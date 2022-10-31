package com.minecraftabnormals.biome_vote_losers.world.level.entity;

import com.minecraftabnormals.biome_vote_losers.register.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
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
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class Meerkat extends Animal {
	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_0 = SynchedEntityData.defineId(Meerkat.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Boolean> DATA_STANDING = SynchedEntityData.defineId(Meerkat.class, EntityDataSerializers.BOOLEAN);

	public static final EntityDimensions STANDING_DIMENSIONS = EntityDimensions.scalable(0.5F, 1.0F);

	public final AnimationState standingAnimationState = new AnimationState();
	public final AnimationState stopStandingAnimationState = new AnimationState();

	public Meerkat(EntityType<? extends Meerkat> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
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

		super.onSyncedDataUpdated(p_29615_);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_TRUSTED_ID_0, Optional.empty());
		this.entityData.define(DATA_STANDING, false);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.275D, true));
		this.goalSelector.addGoal(6, new BreedGoal(this, 0.85D));
		this.goalSelector.addGoal(7, new StandGoal(this));
		this.goalSelector.addGoal(8, new StopStandGoal(this));
		this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 1.0D) {
			@Override
			public boolean canUse() {
				return !isStanding() && super.canUse();
			}

			@Override
			public boolean canContinueToUse() {
				return !isStanding() && super.canContinueToUse();
			}
		});
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Meerkat.class, true));

	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.MOVEMENT_SPEED, 0.26D).add(Attributes.ATTACK_DAMAGE, 2.0F).add(Attributes.FOLLOW_RANGE, 24.0F);
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

	//This thing write save datas(called nbt(CompoundTag) I guess)
	@Override
	public void addAdditionalSaveData(CompoundTag p_28518_) {
		super.addAdditionalSaveData(p_28518_);
		ListTag listtag = new ListTag();

		UUID uuid = this.getTrustedLeaderUUID();
		if (uuid != null) {
			listtag.add(NbtUtils.createUUID(uuid));
		}

		p_28518_.put("TrustedLeader", listtag);

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
		}
		//when group found. add leader
		if (uuid != null) {
			this.setTrustedLeaderUUID(uuid);
		}
		if (flag) {
			this.setAge(-24000);
		}

		return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
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
