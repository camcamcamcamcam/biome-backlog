package com.camcamcamcamcam.biome_backlog.world.level.entity;

import com.camcamcamcamcam.biome_backlog.register.ModEntities;
import com.camcamcamcamcam.biome_backlog.world.level.entity.goal.LongTemptGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class Vulture extends TamableAnimal {
	private static final EntityDataAccessor<Boolean> DATA_CIRCLE = SynchedEntityData.defineId(Vulture.class, EntityDataSerializers.BOOLEAN);


	public static final float FLAP_DEGREES_PER_TICK = 7.448451F;
	public static final int TICKS_PER_FLAP = Mth.ceil(24.166098F);
	public Vec3 moveTargetPoint = Vec3.ZERO;
	BlockPos anchorPoint = BlockPos.ZERO;
	Vulture.AttackPhase attackPhase = Vulture.AttackPhase.CIRCLE;

	public Vulture(EntityType<? extends Vulture> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
		this.moveControl = new VultureMoveControl(this);
		this.lookControl = new VultureLookControl(this);
	}

	public boolean isFlapping() {
		return (this.getUniqueFlapTickOffset() + this.tickCount) % TICKS_PER_FLAP == 0;
	}

	protected BodyRotationControl createBodyControl() {
		return new VultureBodyRotationControl(this);
	}


	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_CIRCLE, false);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new Vulture.VultureCircleAroundDeathPointGoal());
		this.goalSelector.addGoal(1, new LongTemptGoal(this, Ingredient.of(Items.ROTTEN_FLESH), false));
		this.goalSelector.addGoal(2, new Vulture.VultureAttackStrategyGoal());
		this.goalSelector.addGoal(3, new Vulture.VultureSweepAttackGoal());
		this.goalSelector.addGoal(4, new Vulture.VultureCircleAroundAnchorGoal());
		this.targetSelector.addGoal(1, new VultureAttackZombieTargetGoal());
	}


	public boolean hasCircle() {
		return this.entityData.get(DATA_CIRCLE);
	}

	public void setCircle(boolean p_28516_) {
		this.entityData.set(DATA_CIRCLE, p_28516_);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 22.0D).add(Attributes.MOVEMENT_SPEED, 0.28D).add(Attributes.FLYING_SPEED, 0.28D).add(Attributes.ATTACK_DAMAGE, 4.0F).add(Attributes.FOLLOW_RANGE, 35.0F);
	}

	public int getUniqueFlapTickOffset() {
		return this.getId() * 3;
	}

	protected boolean shouldDespawnInPeaceful() {
		return false;
	}
	public void tick() {
		super.tick();
		if (this.level.isClientSide) {
			float f = Mth.cos((float) (this.getUniqueFlapTickOffset() + this.tickCount) * 7.448451F * ((float) Math.PI / 180F) + (float) Math.PI);
			float f1 = Mth.cos((float) (this.getUniqueFlapTickOffset() + this.tickCount + 1) * 7.448451F * ((float) Math.PI / 180F) + (float) Math.PI);
			if (f > 0.0F && f1 <= 0.0F) {
				this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.PHANTOM_FLAP, this.getSoundSource(), 0.95F + this.random.nextFloat() * 0.05F, 0.95F + this.random.nextFloat() * 0.05F, false);
			}
		}

	}

	public void travel(Vec3 p_20818_) {
		if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
			if (this.isInWater()) {
				this.moveRelative(0.02F, p_20818_);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.8F));
			} else if (this.isInLava()) {
				this.moveRelative(0.02F, p_20818_);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
			} else {
				BlockPos ground = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
				float f = 0.91F;
				if (this.onGround) {
					f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
				}

				float f1 = 0.16277137F / (f * f * f);
				f = 0.91F;
				if (this.onGround) {
					f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
				}

				this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, p_20818_);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setDeltaMovement(this.getDeltaMovement().scale((double) f));
			}
		}

		this.calculateEntityAnimation(this, false);
	}

	public boolean onClimbable() {
		return false;
	}

	public boolean causeFallDamage(float p_148989_, float p_148990_, DamageSource p_148991_) {
		return false;
	}

	protected void checkFallDamage(double p_29370_, boolean p_29371_, BlockState p_29372_, BlockPos p_29373_) {
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
		this.anchorPoint = this.blockPosition().above(5);
		return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
	}

	public void readAdditionalSaveData(CompoundTag p_33132_) {
		super.readAdditionalSaveData(p_33132_);
		if (p_33132_.contains("AX")) {
			this.anchorPoint = new BlockPos(p_33132_.getInt("AX"), p_33132_.getInt("AY"), p_33132_.getInt("AZ"));
		}
		this.setCircle(p_33132_.getBoolean("Circle"));
	}

	public void addAdditionalSaveData(CompoundTag p_33141_) {
		super.addAdditionalSaveData(p_33141_);
		p_33141_.putInt("AX", this.anchorPoint.getX());
		p_33141_.putInt("AY", this.anchorPoint.getY());
		p_33141_.putInt("AZ", this.anchorPoint.getZ());
		p_33141_.putBoolean("Circle", this.hasCircle());
	}

	@Nullable
	@Override
	public Vulture getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		Vulture vulture = ModEntities.VULTURE.get().create(p_146743_);
		UUID uuid = this.getOwnerUUID();
		if (uuid != null) {
			vulture.setOwnerUUID(uuid);
			vulture.setTame(true);
		}

		return vulture;
	}

	@Override
	public boolean isFood(ItemStack p_27600_) {
		return false;
	}

	public InteractionResult mobInteract(Player p_30412_, InteractionHand p_30413_) {
		ItemStack itemstack = p_30412_.getItemInHand(p_30413_);
		Item item = itemstack.getItem();
		boolean flag = this.isTame() && this.isOwnedBy(p_30412_) && !this.hasCircle() || itemstack.is(Items.ROTTEN_FLESH) && !this.isTame() && !this.isOwnedBy(p_30412_);

		if (this.level.isClientSide) {
			return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
		} else {
			if (flag) {
				if (!p_30412_.getAbilities().instabuild) {
					itemstack.shrink(1);
				}
				this.setCircle(true);
				this.tame(p_30412_);
				this.navigation.stop();
				this.setTarget((LivingEntity) null);
				this.level.broadcastEntityEvent(this, (byte) 7);

				return InteractionResult.SUCCESS;
			}

			return super.mobInteract(p_30412_, p_30413_);
		}
	}

	public boolean wantsToAttack(LivingEntity p_30389_, LivingEntity p_30390_) {
		if (!(p_30389_ instanceof Creeper) && !(p_30389_ instanceof Ghast)) {
			if (p_30389_ instanceof Vulture) {
				Vulture vulture = (Vulture) p_30389_;
				return !vulture.isTame() || vulture.getOwner() != p_30390_;
			} else if (p_30389_ instanceof Player && p_30390_ instanceof Player && !((Player) p_30390_).canHarmPlayer((Player) p_30389_)) {
				return false;
			} else {
				return !(p_30389_ instanceof TamableAnimal) || !((TamableAnimal) p_30389_).isTame();
			}
		} else {
			return false;
		}
	}

	static enum AttackPhase {
		CIRCLE,
		SWOOP;
	}

	class VultureAttackZombieTargetGoal extends Goal {
		private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0D);
		private int nextScanTick = reducedTickDelay(20);

		public boolean canUse() {
			if (this.nextScanTick > 0) {
				--this.nextScanTick;
				return false;
			} else {
				this.nextScanTick = reducedTickDelay(60);
				List<Zombie> list = Vulture.this.level.getNearbyEntities(Zombie.class, this.attackTargeting, Vulture.this, Vulture.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
				if (!list.isEmpty()) {
					list.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());

					for (Zombie zombie : list) {
						if (Vulture.this.canAttack(zombie, TargetingConditions.DEFAULT)) {
							Vulture.this.setTarget(zombie);
							return true;
						}
					}
				}

				return false;
			}
		}

		public boolean canContinueToUse() {
			LivingEntity livingentity = Vulture.this.getTarget();
			return livingentity != null ? Vulture.this.canAttack(livingentity, TargetingConditions.DEFAULT) : false;
		}
	}

	class VultureTemptGoal extends Goal {
		private final TargetingConditions attackTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().range(64.0D);
		private int nextScanTick = reducedTickDelay(20);

		public boolean canUse() {
			if (this.nextScanTick > 0) {
				--this.nextScanTick;
				return false;
			} else {
				this.nextScanTick = reducedTickDelay(60);
				List<Zombie> list = Vulture.this.level.getNearbyEntities(Zombie.class, this.attackTargeting, Vulture.this, Vulture.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
				if (!list.isEmpty()) {
					list.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());

					for (Zombie zombie : list) {
						if (Vulture.this.canAttack(zombie, TargetingConditions.DEFAULT)) {
							Vulture.this.setTarget(zombie);
							return true;
						}
					}
				}

				return false;
			}
		}

		public boolean canContinueToUse() {
			LivingEntity livingentity = Vulture.this.getTarget();
			return livingentity != null ? Vulture.this.canAttack(livingentity, TargetingConditions.DEFAULT) : false;
		}
	}

	class VultureAttackStrategyGoal extends Goal {
		private int nextSweepTick;

		public boolean canUse() {
			LivingEntity livingentity = Vulture.this.getTarget();
			return livingentity != null ? Vulture.this.canAttack(livingentity, TargetingConditions.DEFAULT) : false;
		}

		public void start() {
			this.nextSweepTick = this.adjustedTickDelay(10);
			Vulture.this.attackPhase = Vulture.AttackPhase.CIRCLE;
			this.setAnchorAboveTarget();
		}

		public void stop() {
			Vulture.this.anchorPoint = Vulture.this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, Vulture.this.anchorPoint).above(10 + Vulture.this.random.nextInt(20));
		}

		public void tick() {
			if (Vulture.this.attackPhase == Vulture.AttackPhase.CIRCLE) {
				--this.nextSweepTick;
				if (this.nextSweepTick <= 0) {
					Vulture.this.attackPhase = Vulture.AttackPhase.SWOOP;
					this.setAnchorAboveTarget();
					this.nextSweepTick = this.adjustedTickDelay((8 + Vulture.this.random.nextInt(4)) * 20);
					//Vulture.this.playSound(SoundEvents.Vulture_SWOOP, 10.0F, 0.95F + Vulture.this.random.nextFloat() * 0.1F);
				}
			}

		}

		private void setAnchorAboveTarget() {
			Vulture.this.anchorPoint = Vulture.this.getTarget().blockPosition().above(20 + Vulture.this.random.nextInt(20));
			if (Vulture.this.anchorPoint.getY() < Vulture.this.level.getSeaLevel()) {
				Vulture.this.anchorPoint = new BlockPos(Vulture.this.anchorPoint.getX(), Vulture.this.level.getSeaLevel() + 1, Vulture.this.anchorPoint.getZ());
			}

		}
	}

	class VultureBodyRotationControl extends BodyRotationControl {
		public VultureBodyRotationControl(Mob p_33216_) {
			super(p_33216_);
		}

		public void clientTick() {
			Vulture.this.yHeadRot = Vulture.this.yBodyRot;
			Vulture.this.yBodyRot = Vulture.this.getYRot();
		}
	}

	class VultureCircleAroundDeathPointGoal extends Vulture.VultureMoveTargetGoal {
		private float angle;
		private float distance;
		private float height;
		private float clockwise;

		public boolean canUse() {
			return Vulture.this.getTarget() == null && Vulture.this.getOwner() != null && Vulture.this.getOwner() instanceof Player && ((Player) Vulture.this.getOwner()).getLastDeathLocation().isPresent() && Vulture.this.distanceToSqr(Vulture.this.getOwner()) < 120F && Vulture.this.attackPhase == Vulture.AttackPhase.CIRCLE;
		}

		public void start() {
			this.distance = 5.0F + Vulture.this.random.nextFloat() * 10.0F;
			this.height = -4.0F + Vulture.this.random.nextFloat() * 9.0F;
			this.clockwise = Vulture.this.random.nextBoolean() ? 1.0F : -1.0F;
			this.selectNext();
		}

		public void tick() {
			if (Vulture.this.random.nextInt(this.adjustedTickDelay(350)) == 0) {
				this.height = -4.0F + Vulture.this.random.nextFloat() * 9.0F;
			}

			/*if (Vulture.this.random.nextInt(this.adjustedTickDelay(250)) == 0) {
				++this.distance;
				if (this.distance > 15.0F) {
					this.distance = 5.0F;
					this.clockwise = -this.clockwise;
				}
			}*/

			/*if (Vulture.this.random.nextInt(this.adjustedTickDelay(450)) == 0) {
				this.angle = Vulture.this.random.nextFloat() * 2.0F * (float)Math.PI;
				this.selectNext();
			}*/

			if (this.touchingTarget()) {
				this.selectNext();
			}

			if (Vulture.this.moveTargetPoint.y < Vulture.this.getY() && !Vulture.this.level.isEmptyBlock(Vulture.this.blockPosition().below(1))) {
				this.height = Math.max(1.0F, this.height);
				this.selectNext();
			}

			if (Vulture.this.moveTargetPoint.y > Vulture.this.getY() && !Vulture.this.level.isEmptyBlock(Vulture.this.blockPosition().above(1))) {
				this.height = Math.min(-1.0F, this.height);
				this.selectNext();
			}

		}

		private void selectNext() {
			if (Vulture.this.getOwner() instanceof Player) {
				if (((Player) Vulture.this.getOwner()).getLastDeathLocation().isPresent() && ((Player) Vulture.this.getOwner()).getLastDeathLocation().get().dimension() == Vulture.this.getLevel().dimension()) {
					Vulture.this.anchorPoint = ((Player) Vulture.this.getOwner()).getLastDeathLocation().get().pos().above(10);
				}
			}

			this.angle += this.clockwise * 15.0F * ((float) Math.PI / 180F);
			Vulture.this.moveTargetPoint = Vec3.atLowerCornerOf(Vulture.this.anchorPoint).add((double) (this.distance * Mth.cos(this.angle)), (double) (-4.0F + this.height), (double) (this.distance * Mth.sin(this.angle)));
		}
	}

	class VultureCircleAroundAnchorGoal extends Vulture.VultureMoveTargetGoal {
		private float angle;
		private float distance;
		private float height;
		private float clockwise;

		public boolean canUse() {
			return Vulture.this.getTarget() == null || Vulture.this.attackPhase == Vulture.AttackPhase.CIRCLE;
		}

		public void start() {
			this.distance = 5.0F + Vulture.this.random.nextFloat() * 10.0F;
			this.height = -4.0F + Vulture.this.random.nextFloat() * 9.0F;
			this.clockwise = Vulture.this.random.nextBoolean() ? 1.0F : -1.0F;
			this.selectNext();
		}

		public void tick() {
			if (Vulture.this.random.nextInt(this.adjustedTickDelay(350)) == 0) {
				this.height = -4.0F + Vulture.this.random.nextFloat() * 9.0F;
			}

			if (Vulture.this.random.nextInt(this.adjustedTickDelay(250)) == 0) {
				++this.distance;
				if (this.distance > 15.0F) {
					this.distance = 5.0F;
					this.clockwise = -this.clockwise;
				}
			}

			if (Vulture.this.random.nextInt(this.adjustedTickDelay(450)) == 0) {
				this.angle = Vulture.this.random.nextFloat() * 2.0F * (float) Math.PI;
				this.selectNext();
			}

			if (this.touchingTarget()) {
				this.selectNext();
			}

			if (Vulture.this.moveTargetPoint.y < Vulture.this.getY() && !Vulture.this.level.isEmptyBlock(Vulture.this.blockPosition().below(1))) {
				this.height = Math.max(1.0F, this.height);
				this.selectNext();
			}

			if (Vulture.this.moveTargetPoint.y > Vulture.this.getY() && !Vulture.this.level.isEmptyBlock(Vulture.this.blockPosition().above(1))) {
				this.height = Math.min(-1.0F, this.height);
				this.selectNext();
			}

		}

		private void selectNext() {
			if (BlockPos.ZERO.equals(Vulture.this.anchorPoint)) {
				Vulture.this.anchorPoint = Vulture.this.blockPosition();
			}

			this.angle += this.clockwise * 15.0F * ((float) Math.PI / 180F);
			Vulture.this.moveTargetPoint = Vec3.atLowerCornerOf(Vulture.this.anchorPoint).add((double) (this.distance * Mth.cos(this.angle)), (double) (-4.0F + this.height), (double) (this.distance * Mth.sin(this.angle)));
		}
	}

	class VultureLookControl extends LookControl {
		public VultureLookControl(Mob p_33235_) {
			super(p_33235_);
		}

		public void tick() {
		}
	}

	class VultureMoveControl extends MoveControl {
		private float speed = 0.1F;

		public VultureMoveControl(Mob p_33241_) {
			super(p_33241_);
		}

		public void tick() {
			if (Vulture.this.horizontalCollision) {
				Vulture.this.setYRot(Vulture.this.getYRot() + 180.0F);
				this.speed = 0.1F;
			}

			double d0 = Vulture.this.moveTargetPoint.x - Vulture.this.getX();
			double d1 = Vulture.this.moveTargetPoint.y - Vulture.this.getY();
			double d2 = Vulture.this.moveTargetPoint.z - Vulture.this.getZ();
			double d3 = Math.sqrt(d0 * d0 + d2 * d2);
			if (Math.abs(d3) > (double) 1.0E-5F) {
				double d4 = 1.0D - Math.abs(d1 * (double) 0.7F) / d3;
				d0 *= d4;
				d2 *= d4;
				d3 = Math.sqrt(d0 * d0 + d2 * d2);
				double d5 = Math.sqrt(d0 * d0 + d2 * d2 + d1 * d1);
				float f = Vulture.this.getYRot();
				float f1 = (float) Mth.atan2(d2, d0);
				float f2 = Mth.wrapDegrees(Vulture.this.getYRot() + 90.0F);
				float f3 = Mth.wrapDegrees(f1 * (180F / (float) Math.PI));
				Vulture.this.setYRot(Mth.approachDegrees(f2, f3, 4.0F) - 90.0F);
				Vulture.this.yBodyRot = Vulture.this.getYRot();
				if (Mth.degreesDifferenceAbs(f, Vulture.this.getYRot()) < 3.0F) {
					this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
				} else {
					this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
				}

				float f4 = (float) (-(Mth.atan2(-d1, d3) * (double) (180F / (float) Math.PI)));
				Vulture.this.setXRot(f4);
				float f5 = Vulture.this.getYRot() + 90.0F;
				double d6 = (double) (this.speed * Mth.cos(f5 * ((float) Math.PI / 180F))) * Math.abs(d0 / d5);
				double d7 = (double) (this.speed * Mth.sin(f5 * ((float) Math.PI / 180F))) * Math.abs(d2 / d5);
				double d8 = (double) (this.speed * Mth.sin(f4 * ((float) Math.PI / 180F))) * Math.abs(d1 / d5);
				Vec3 vec3 = Vulture.this.getDeltaMovement();
				Vulture.this.setDeltaMovement(vec3.add((new Vec3(d6, d8, d7)).subtract(vec3).scale(0.2D)));
			}

		}
	}

	abstract class VultureMoveTargetGoal extends Goal {
		public VultureMoveTargetGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		protected boolean touchingTarget() {
			return Vulture.this.moveTargetPoint.distanceToSqr(Vulture.this.getX(), Vulture.this.getY(), Vulture.this.getZ()) < 4.0D;
		}
	}

	class VultureSweepAttackGoal extends Vulture.VultureMoveTargetGoal {
		private static final int CAT_SEARCH_TICK_DELAY = 20;
		private boolean isScaredOfCat;
		private int catSearchTick;

		public boolean canUse() {
			return Vulture.this.getTarget() != null && Vulture.this.attackPhase == Vulture.AttackPhase.SWOOP;
		}

		public boolean canContinueToUse() {
			LivingEntity livingentity = Vulture.this.getTarget();
			if (livingentity == null) {
				return false;
			} else if (!livingentity.isAlive()) {
				return false;
			} else {
				if (livingentity instanceof Player) {
					Player player = (Player) livingentity;
					if (livingentity.isSpectator() || player.isCreative()) {
						return false;
					}
				}

				if (!this.canUse()) {
					return false;
				} else {
					if (Vulture.this.tickCount > this.catSearchTick) {
						this.catSearchTick = Vulture.this.tickCount + 20;
						List<Cat> list = Vulture.this.level.getEntitiesOfClass(Cat.class, Vulture.this.getBoundingBox().inflate(16.0D), EntitySelector.ENTITY_STILL_ALIVE);

						for (Cat cat : list) {
							cat.hiss();
						}

						this.isScaredOfCat = !list.isEmpty();
					}

					return !this.isScaredOfCat;
				}
			}
		}

		public void start() {
		}

		public void stop() {
			Vulture.this.setTarget((LivingEntity) null);
			Vulture.this.attackPhase = Vulture.AttackPhase.CIRCLE;
		}

		public void tick() {
			LivingEntity livingentity = Vulture.this.getTarget();
			if (livingentity != null) {
				Vulture.this.moveTargetPoint = new Vec3(livingentity.getX(), livingentity.getY(0.5D), livingentity.getZ());
				if (Vulture.this.getBoundingBox().inflate((double) 0.2F).intersects(livingentity.getBoundingBox())) {
					Vulture.this.doHurtTarget(livingentity);
					Vulture.this.attackPhase = Vulture.AttackPhase.CIRCLE;
					if (!Vulture.this.isSilent()) {
						Vulture.this.level.levelEvent(1039, Vulture.this.blockPosition(), 0);
					}
				} else if (Vulture.this.horizontalCollision || Vulture.this.hurtTime > 0) {
					Vulture.this.attackPhase = Vulture.AttackPhase.CIRCLE;
				}

			}
		}
	}
}