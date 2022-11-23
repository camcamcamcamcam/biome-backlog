package com.minecraftabnormals.biome_vote_losers.world.level.entity;

import com.minecraftabnormals.biome_vote_losers.register.ModBlocks;
import com.minecraftabnormals.biome_vote_losers.register.ModEntities;
import com.minecraftabnormals.biome_vote_losers.register.ModTags;
import com.minecraftabnormals.biome_vote_losers.world.level.block.entity.BurrowBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.AirRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

public class Meerkat extends Animal {
	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_0 = SynchedEntityData.defineId(Meerkat.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Boolean> DATA_STANDING = SynchedEntityData.defineId(Meerkat.class, EntityDataSerializers.BOOLEAN);

	public static final EntityDimensions STANDING_DIMENSIONS = EntityDimensions.scalable(0.5F, 1.0F);

	public final AnimationState standingAnimationState = new AnimationState();
	public final AnimationState stopStandingAnimationState = new AnimationState();
	public AnimationState diggingAnimationState = new AnimationState();
	public AnimationState digUpAnimationState = new AnimationState();


	@javax.annotation.Nullable
	BlockPos burrowPos;
	public int stayOutOfBurrowCountdown;

	public Meerkat(EntityType<? extends Meerkat> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new MeerkatEnterBurrowGoal());
		this.goalSelector.addGoal(2, new MeerkatDiggingUpGoal());
		this.goalSelector.addGoal(4, new PanicGoal(this, 1.35D) {
			@Override
			public boolean canUse() {
				return isBaby() && super.canUse();
			}
		});
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.275D, true));
		this.goalSelector.addGoal(5, new BreedGoal(this, 0.85D));
		this.goalSelector.addGoal(7, new MeerkatMakeBurrowOrFindGoal(this));
		this.goalSelector.addGoal(8, new MeerkatGoToBurrowGoal());
		this.goalSelector.addGoal(9, new StandGoal(this));
		this.goalSelector.addGoal(10, new StopStandGoal(this));
		this.goalSelector.addGoal(11, new WaterAvoidingRandomStrollGoal(this, 1.0D) {
			@Override
			public boolean canUse() {
				return !isStanding() && super.canUse();
			}

			@Override
			public boolean canContinueToUse() {
				return !isStanding() && super.canContinueToUse();
			}
		});
		this.goalSelector.addGoal(12, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Meerkat.class, true));

	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.MOVEMENT_SPEED, 0.26D).add(Attributes.ATTACK_DAMAGE, 2.0F).add(Attributes.FOLLOW_RANGE, 24.0F);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_TRUSTED_ID_0, Optional.empty());
		this.entityData.define(DATA_STANDING, false);
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> p_29615_) {
		if (DATA_STANDING.equals(p_29615_)) {
			this.refreshDimensions();
			if (this.isStanding()) {
				this.standingAnimationState.start(this.tickCount);
				this.stopStandingAnimationState.stop();
			} else {
				this.stopStandingAnimationState.start(this.tickCount);
				this.standingAnimationState.stop();
			}
		}

		if (DATA_POSE.equals(p_29615_)) {
			switch (this.getPose()) {
				case DIGGING:
					this.diggingAnimationState.start(this.tickCount);
					break;
				case EMERGING:
					this.digUpAnimationState.start(this.tickCount);
					break;
				default:
					diggingAnimationState.stop();
					digUpAnimationState.stop();
					break;
			}
		}

		super.onSyncedDataUpdated(p_29615_);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level.isClientSide) {
			if (this.stayOutOfBurrowCountdown > 0) {
				--this.stayOutOfBurrowCountdown;
			}
		}
	}

	public void tick() {
		super.tick();
		if (this.level.isClientSide()) {

			switch (this.getPose()) {
				case DIGGING:
					this.clientDiggingParticles(this.diggingAnimationState);
				case EMERGING:
					this.clientDiggingParticles(this.diggingAnimationState);
			}
		}

	}

	private void clientDiggingParticles(AnimationState p_219384_) {
		if ((float) p_219384_.getAccumulatedTime() < 4500.0F) {
			RandomSource randomsource = this.getRandom();
			BlockState blockstate = this.getBlockStateOn();
			if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
				for (int i = 0; i < 5; ++i) {
					double d0 = this.getX() + (double) Mth.randomBetween(randomsource, -0.3F, 0.3F);
					double d1 = this.getY();
					double d2 = this.getZ() + (double) Mth.randomBetween(randomsource, -0.3F, 0.3F);
					this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}

	public boolean isInvulnerableTo(DamageSource p_219427_) {
		return this.isDiggingOrEmerging() && !p_219427_.isBypassInvul() ? true : super.isInvulnerableTo(p_219427_);
	}

	private boolean isDiggingOrEmerging() {
		return this.hasPose(Pose.DIGGING) || this.hasPose(Pose.EMERGING);
	}

	@Override
	public boolean canAttack(LivingEntity p_21171_) {
		return (this.getTrustedLeaderUUID() == null && p_21171_.getType() != ModEntities.MEERKAT.get()) || (this.getTrustedLeaderUUID() != null && this.getTrustedLeaderUUID() != p_21171_.getUUID()) && super.canAttack(p_21171_);
	}

	public int getMaxSpawnClusterSize() {
		return 8;
	}

	public boolean isStanding() {
		return this.entityData.get(DATA_STANDING);
	}

	public void setStanding(boolean standing) {
		this.entityData.set(DATA_STANDING, standing);
	}

	@Nullable
	public UUID getTrustedLeaderUUID() {
		return this.entityData.get(DATA_TRUSTED_ID_0).orElse((UUID) null);
	}

	public void setTrustedLeaderUUID(@javax.annotation.Nullable UUID p_28516_) {
		this.entityData.set(DATA_TRUSTED_ID_0, Optional.ofNullable(p_28516_));
	}

	public void setBurrowPos(@javax.annotation.Nullable BlockPos burrowPos) {
		this.burrowPos = burrowPos;
	}

	@javax.annotation.Nullable
	public BlockPos getBurrowPos() {
		return burrowPos;
	}

	@VisibleForDebug
	public boolean hasBurrow() {
		return this.burrowPos != null;
	}

	//This thing write save datas(called nbt(CompoundTag) I guess)
	@Override
	public void addAdditionalSaveData(CompoundTag p_28518_) {
		super.addAdditionalSaveData(p_28518_);
		ListTag listtag = new ListTag();

		UUID uuid = this.getTrustedLeaderUUID();
		if (uuid != null) {
			listtag.add(NbtUtils.createUUID(uuid));
		}

		if (this.hasBurrow()) {
			p_28518_.put("BurrowPos", NbtUtils.writeBlockPos(this.getBurrowPos()));
		}

		p_28518_.put("TrustedLeader", listtag);

		p_28518_.putInt("CannotEnterBurrowTicks", this.stayOutOfBurrowCountdown);
		p_28518_.putBoolean("Standing", this.isStanding());
	}

	//This thing read save datas(called nbt(CompoundTag) I guess)
	@Override
	public void readAdditionalSaveData(CompoundTag p_28493_) {
		super.readAdditionalSaveData(p_28493_);
		ListTag listtag = p_28493_.getList("Trusted", 11);

		for (int i = 0; i < listtag.size(); ++i) {
			this.setTrustedLeaderUUID(NbtUtils.loadUUID(listtag.get(i)));
		}

		this.burrowPos = null;
		if (p_28493_.contains("BurrowPos")) {
			this.burrowPos = NbtUtils.readBlockPos(p_28493_.getCompound("BurrowPos"));
		}

		this.stayOutOfBurrowCountdown = p_28493_.getInt("CannotEnterBurrowTicks");
		this.setStanding(p_28493_.getBoolean("Standing"));
	}

	@Override
	public EntityDimensions getDimensions(Pose p_149361_) {
		return this.isStanding() ? STANDING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(p_149361_);
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		Meerkat meerkat = ModEntities.MEERKAT.get().create(p_146743_);

		UUID uuid = meerkat.getTrustedLeaderUUID();
		if (uuid != null) {
			meerkat.setTrustedLeaderUUID(uuid);
		}
		return meerkat;
	}

	public static boolean checkMeerkatSpawnRules(EntityType<? extends Animal> p_218105_, LevelAccessor p_218106_, MobSpawnType p_218107_, BlockPos p_218108_, RandomSource p_218109_) {
		return p_218106_.getBlockState(p_218108_.below()).is(Tags.Blocks.SAND) && isBrightEnoughToSpawn(p_218106_, p_218108_);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
		if (p_146748_ != MobSpawnType.STRUCTURE) {
			boolean flag = false;
			UUID uuid = null;

			if (p_146749_ instanceof MeerkatGroupData meerkat$group) {
				uuid = meerkat$group.uuid;
				//when random success and group size is many. child is spawn
				if (meerkat$group.getGroupSize() >= 2 && p_146746_.getRandom().nextFloat() < 0.25F) {
					flag = true;
				}
			} else {
				p_146749_ = new MeerkatGroupData(this.getUUID());
				uuid = this.getUUID();
			}
			//when group found. add leader
			if (uuid != null) {
				this.setTrustedLeaderUUID(uuid);
			}
			if (flag) {
				this.setAge(-24000);
			}
		}

		return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
	}

	boolean closerThan(BlockPos p_27817_, int p_27818_) {
		return p_27817_.closerThan(this.blockPosition(), (double) p_27818_);
	}

	boolean isTooFarAway(BlockPos p_27890_) {
		return !this.closerThan(p_27890_, 128);
	}

	void pathfindRandomlyTowards(BlockPos p_27881_) {
		Vec3 vec3 = Vec3.atBottomCenterOf(p_27881_);
		int i = 0;
		BlockPos blockpos = this.blockPosition();
		int j = (int) vec3.y - blockpos.getY();
		if (j > 2) {
			i = 4;
		} else if (j < -2) {
			i = -4;
		}

		int k = 6;
		int l = 8;
		int i1 = blockpos.distManhattan(p_27881_);
		if (i1 < 15) {
			k = i1 / 2;
			l = i1 / 2;
		}

		Vec3 vec31 = AirRandomPos.getPosTowards(this, k, l, i, vec3, (double) ((float) Math.PI / 10F));
		if (vec31 != null) {
			this.navigation.setMaxVisitedNodesMultiplier(0.5F);
			this.navigation.moveTo(vec31.x, vec31.y, vec31.z, 1.0D);
		}
	}

	public void shareTheBurrow(@javax.annotation.Nullable BlockPos burrowPos) {
		for (Meerkat meerkat : this.level.getEntities(EntityTypeTest.forClass(Meerkat.class), this.getBoundingBox().inflate(16D), (meerkat) -> {
			return meerkat.getTrustedLeaderUUID() == this.getUUID();
		})) {
			meerkat.setBurrowPos(burrowPos);
		}
	}

	public boolean wantsToEnterBurrow() {
		if (this.getTarget() == null && this.getPose() != Pose.EMERGING && this.getPose() != Pose.DIGGING) {
			boolean flag = this.stayOutOfBurrowCountdown <= 0 || this.level.isRaining() || this.level.isNight();
			return flag;
		} else {
			return false;
		}
	}

	public boolean wantsToMakeBurrow() {
		if (this.getTarget() == null && !this.isStanding() && this.getPose() != Pose.EMERGING && this.getPose() != Pose.DIGGING && Meerkat.this.random.nextInt(240) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public class MeerkatDiggingUpGoal extends Goal {
		public int tick;

		public MeerkatDiggingUpGoal() {
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
		}

		public boolean canUse() {
			return Meerkat.this.getPose() == Pose.EMERGING;
		}

		@Override
		public boolean canContinueToUse() {
			return Meerkat.this.getPose() == Pose.EMERGING && this.tick < 60;
		}

		public void start() {
			this.tick = 0;
		}

		@Override
		public void tick() {
			super.tick();
			++this.tick;
		}

		@Override
		public void stop() {
			super.stop();
			Meerkat.this.setPose(Pose.STANDING);
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}
	}

	public class MeerkatMakeBurrowOrFindGoal extends Goal {

		public boolean burrowMade;

		public int tick;

		public final Meerkat meerkat;

		public MeerkatMakeBurrowOrFindGoal(Meerkat meerkat) {
			this.meerkat = meerkat;
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
		}

		public boolean canUse() {
			if (this.meerkat.getTrustedLeaderUUID() == this.meerkat.getUUID()) {
				if (!this.meerkat.hasBurrow() && wantsToMakeBurrow()) {
					if (canFindBurrow()) {
						return false;
					} else {
						return this.meerkat.level.getBlockState(this.meerkat.blockPosition().below()).is(Blocks.SAND);
					}
				}
			}

			return false;
		}

		public boolean canContinueToUse() {
			return this.tick < 120 && !burrowMade;
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		private boolean canFindBurrow() {
			BlockPos blockpos = Meerkat.this.blockPosition();
			BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

			for (int i = 0; i < 16; ++i) {
				for (int j = 0; j < 16; ++j) {
					for (int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
						for (int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
							blockpos$mutable.setWithOffset(blockpos, k, i, l);
							if (level.getBlockState(blockpos$mutable).is(ModBlocks.BURROW.get())) {
								Meerkat.this.setBurrowPos(blockpos$mutable);
								Meerkat.this.shareTheBurrow(blockpos$mutable);
								return true;
							}
						}
					}
				}
			}

			return false;
		}

		public void start() {
			this.tick = 0;
			burrowMade = false;

		}

		@Override
		public void tick() {
			super.tick();
			++this.tick;
			if (this.tick % 5 == 0) {
				if (this.meerkat.level.getBlockState(this.meerkat.blockPosition().below()).is(Blocks.SAND)) {
					this.meerkat.level.levelEvent(2001, this.meerkat.blockPosition().below(), Block.getId(this.meerkat.level.getBlockState(this.meerkat.blockPosition().below())));
				}
			}
		}

		@Override
		public void stop() {
			super.stop();
			if (this.meerkat.level.getBlockState(this.meerkat.blockPosition().below()).is(Blocks.SAND)) {
				this.meerkat.level.setBlock(this.meerkat.blockPosition().below(), ModBlocks.BURROW.get().defaultBlockState(), 2);
				this.meerkat.setBurrowPos(this.meerkat.blockPosition().below());
				Meerkat.this.shareTheBurrow(this.meerkat.blockPosition().below());
			}
		}
	}

	public class MeerkatEnterBurrowGoal extends Goal {
		public int tick;

		public MeerkatEnterBurrowGoal() {
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
		}

		public boolean canUse() {
			if (Meerkat.this.hasBurrow() && wantsToEnterBurrow() && Meerkat.this.burrowPos.closerToCenterThan(Meerkat.this.position(), 2.0D)) {
				BlockEntity blockentity = Meerkat.this.level.getBlockEntity(Meerkat.this.burrowPos);
				if (blockentity instanceof BurrowBlockEntity) {
					BurrowBlockEntity burrow = (BurrowBlockEntity) blockentity;
					if (!burrow.isFull()) {
						return true;
					}
				}
			}

			return false;
		}

		public boolean canContinueToUse() {
			return this.tick < 100;
		}

		public void start() {
			this.tick = 0;
			Meerkat.this.setPose(Pose.DIGGING);
		}

		@Override
		public void tick() {
			super.tick();
			++this.tick;
		}

		@Override
		public void stop() {
			super.stop();
			BlockEntity blockentity = Meerkat.this.level.getBlockEntity(Meerkat.this.burrowPos);
			if (blockentity instanceof BurrowBlockEntity burrow) {
				burrow.addOccupant(Meerkat.this);
			} else {
				Meerkat.this.setPose(Pose.EMERGING);
				Meerkat.this.setBurrowPos(null);
				Meerkat.this.shareTheBurrow(null);
			}
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}
	}


	@VisibleForDebug
	public class MeerkatGoToBurrowGoal extends Goal {
		int travellingTicks = Meerkat.this.level.random.nextInt(10);
		public MeerkatGoToBurrowGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			return Meerkat.this.burrowPos != null && Meerkat.this.wantsToEnterBurrow() && !Meerkat.this.hasRestriction() && !this.hasReachedTarget(Meerkat.this.burrowPos) && Meerkat.this.level.getBlockState(Meerkat.this.burrowPos).is(ModTags.BURROW);
		}

		public boolean canContinueToUse() {
			return this.canUse();
		}

		public void start() {
			this.travellingTicks = 0;
			super.start();
		}

		public void stop() {
			this.travellingTicks = 0;
			Meerkat.this.navigation.stop();
			Meerkat.this.navigation.resetMaxVisitedNodesMultiplier();
		}

		public void tick() {
			if (Meerkat.this.burrowPos != null) {
				++this.travellingTicks;
				if (!Meerkat.this.navigation.isInProgress()) {
					if (!Meerkat.this.closerThan(Meerkat.this.burrowPos, 16)) {
						Meerkat.this.pathfindRandomlyTowards(Meerkat.this.burrowPos);
					} else {
						boolean flag = this.pathfindDirectlyTowards(Meerkat.this.burrowPos);
						if (!flag) {
							this.dropHive();
						}

					}
				}
			}
		}

		private boolean pathfindDirectlyTowards(BlockPos p_27991_) {
			Meerkat.this.navigation.setMaxVisitedNodesMultiplier(10.0F);
			Meerkat.this.navigation.moveTo((double) p_27991_.getX(), (double) p_27991_.getY(), (double) p_27991_.getZ(), 1.0D);
			return Meerkat.this.navigation.getPath() != null && Meerkat.this.navigation.getPath().canReach();
		}

		private void dropHive() {
			Meerkat.this.burrowPos = null;
		}

		private boolean hasReachedTarget(BlockPos p_28002_) {
			if (Meerkat.this.closerThan(p_28002_, 2)) {
				return true;
			} else {
				Path path = Meerkat.this.navigation.getPath();
				return path != null && path.getTarget().equals(p_28002_) && path.canReach() && path.isDone();
			}
		}
	}

	/*
	 *  group data class
	 */
	public static class MeerkatGroupData extends AgeableMob.AgeableMobGroupData {
		public final UUID uuid;

		public MeerkatGroupData(UUID p_28703_) {
			super(false);
			this.uuid = p_28703_;
		}
	}

	private class StandGoal extends Goal {
		public final Meerkat meerkat;

		private static final UniformInt TIME_BETWEEN_STANDING = UniformInt.of(300, 1200);


		private int cooldown = -1;

		public StandGoal(Meerkat meerkat) {
			this.meerkat = meerkat;
		}

		@Override
		public boolean canUse() {
			if (this.cooldown <= -1) {
				this.cooldown = TIME_BETWEEN_STANDING.sample(this.meerkat.random);
			}

			if (!this.meerkat.isStanding()) {
				if (this.cooldown <= 0) {
					this.cooldown = TIME_BETWEEN_STANDING.sample(this.meerkat.random);
					if (this.meerkat.getTarget() == null && this.meerkat.isOnGround() && !this.meerkat.isInWater()) {
						return true;
					}
				} else {
					--this.cooldown;
					return false;
				}
			} else {
				if (this.cooldown <= 0) {
					this.cooldown = TIME_BETWEEN_STANDING.sample(this.meerkat.random);
				}
			}

			return false;
		}

		@Override
		public boolean canContinueToUse() {
			return false;
		}

		@Override
		public void start() {
			super.start();
			this.meerkat.setStanding(true);
		}
	}

	private class StopStandGoal extends Goal {
		public final Meerkat meerkat;

		private static final UniformInt TIME_STANDING = UniformInt.of(100, 400);


		private int cooldown;

		public StopStandGoal(Meerkat meerkat) {
			this.meerkat = meerkat;
		}

		@Override
		public boolean canUse() {
			if (this.meerkat.isStanding()) {

				if (this.meerkat.getTarget() != null || !this.meerkat.isOnGround() || this.meerkat.isInWater()) {
					return true;
				}
				if (this.cooldown <= 0) {
					this.cooldown = TIME_STANDING.sample(this.meerkat.random);
					return true;
				} else {
					--this.cooldown;
					return false;
				}
			} else {
				if (this.cooldown <= 0) {
					this.cooldown = TIME_STANDING.sample(this.meerkat.random);
				}
			}

			return false;
		}

		@Override
		public boolean canContinueToUse() {
			return false;
		}

		@Override
		public void start() {
			super.start();
			this.meerkat.setStanding(false);
			this.meerkat.getNavigation().stop();
		}
	}
}
