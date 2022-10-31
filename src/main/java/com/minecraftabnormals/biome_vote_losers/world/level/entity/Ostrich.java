package com.minecraftabnormals.biome_vote_losers.world.level.entity;

import com.minecraftabnormals.biome_vote_losers.register.ModBlocks;
import com.minecraftabnormals.biome_vote_losers.register.ModEntities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.UUID;

public class Ostrich extends Animal implements NeutralMob {

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.BOOLEAN);
    public AnimationState idlingState = new AnimationState();
    public AnimationState walkingState = new AnimationState();
    public AnimationState runningState = new AnimationState();
    public AnimationState dippingState = new AnimationState();
    public AnimationState kickingState = new AnimationState();

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;

    @Nullable
    private BlockPos homeTarget;

    public Ostrich(EntityType<? extends Ostrich> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_DAMAGE, 5.0F).add(Attributes.FOLLOW_RANGE, 20.0F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
        this.entityData.define(HAS_EGG, false);
    }

    @Override
    public void tick() {
        if (this.isMoving() && level.isClientSide()) {
            if (isDashing()) {
                idlingState.stop();
                walkingState.stop();
                runningState.startIfStopped(this.tickCount);
            } else {
                runningState.stop();
                idlingState.stop();
                walkingState.startIfStopped(this.tickCount);
            }
        } else if (level.isClientSide()) {
            runningState.stop();
            walkingState.stop();
            idlingState.startIfStopped(this.tickCount);
        }
        super.tick();
    }

    public void setHomeTarget(@Nullable BlockPos pos) {
        this.homeTarget = pos;
    }

    @Nullable
    private BlockPos getHomeTarget() {
        return this.homeTarget;
    }

    private boolean isDashing() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 0.0075D;
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.5D, true));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.25D, Ingredient.of(Items.WHEAT), false));

        this.goalSelector.addGoal(6, new LayEggGoal(this, 0.85D));
        this.goalSelector.addGoal(7, new OstrichGoHomeGoal(this, 0.85D));
        this.goalSelector.addGoal(8, new BreedGoal(this, 0.85D) {
            @Override
            public boolean canUse() {
                return !hasEgg() && super.canUse();
            }
        });
        this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (this.homeTarget != null) {
            compoundTag.put("HomeTarget", NbtUtils.writeBlockPos(this.homeTarget));
        }
        compoundTag.putBoolean("HasEgg", this.hasEgg());
        this.addPersistentAngerSaveData(compoundTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains("HomeTarget")) {
            this.homeTarget = NbtUtils.readBlockPos(compoundTag.getCompound("HomeTarget"));
        }
        this.setHasEgg(compoundTag.getBoolean("HasEgg"));
        this.readPersistentAngerSaveData(this.level, compoundTag);
    }

    public boolean hasEgg() {
        return this.entityData.get(HAS_EGG);
    }

    public void setHasEgg(boolean hasEgg) {
        this.entityData.set(HAS_EGG, hasEgg);
    }

    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int p_30404_) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, p_30404_);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.homeTarget != null) {
            if (!this.level.getBlockState(this.homeTarget).is(ModBlocks.OSTRICH_EGG.get())) {
                this.homeTarget = null;
            }
        }

        if (!this.level.isClientSide) {
            this.updatePersistentAnger((ServerLevel) this.level, true);
        }
    }

    @javax.annotation.Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@javax.annotation.Nullable UUID p_30400_) {
        this.persistentAngerTarget = p_30400_;
    }

    @Override
    public boolean doHurtTarget(Entity p_21372_) {
        this.level.broadcastEntityEvent(this, (byte) 4);
        return super.doHurtTarget(p_21372_);
    }

    @Override
    public void handleEntityEvent(byte p_219360_) {
        if (p_219360_ == 4) {
            this.kickingState.start(this.tickCount);
        } else {
            super.handleEntityEvent(p_219360_);
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        Ostrich ostrich = ModEntities.OSTRICH.get().create(p_146743_);
        return ostrich;
    }

    public void spawnChildFromBreeding(ServerLevel p_218479_, Animal p_218480_) {
        ServerPlayer serverplayer = this.getLoveCause();
        if (serverplayer == null) {
            serverplayer = p_218480_.getLoveCause();
        }

        if (serverplayer != null) {
            serverplayer.awardStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(serverplayer, this, p_218480_, (AgeableMob) null);
        }

        this.setAge(6000);
        p_218480_.setAge(6000);
        this.resetLove();
        p_218480_.resetLove();
        this.setHasEgg(true);
        p_218479_.broadcastEntityEvent(this, (byte) 18);
        if (p_218479_.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            p_218479_.addFreshEntity(new ExperienceOrb(p_218479_, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
        }
    }

    static class OstrichGoHomeGoal extends Goal {
        private final Ostrich ostrich;
        private final double speedModifier;
        private boolean stuck;
        private int closeToHomeTryTicks;

        OstrichGoHomeGoal(Ostrich p_30253_, double p_30254_) {
            this.ostrich = p_30253_;
            this.speedModifier = p_30254_;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if (this.ostrich.isBaby()) {
                return false;
            } else if (this.ostrich.hasEgg()) {
                return true;
            } else {
                return this.ostrich.getHomeTarget() != null && !this.ostrich.getHomeTarget().closerToCenterThan(this.ostrich.position(), 64.0D);
            }
        }

        public void start() {
            this.stuck = false;
            this.closeToHomeTryTicks = 0;
        }

        public void stop() {
        }

        public boolean canContinueToUse() {
            return this.ostrich.getHomeTarget() != null && !this.ostrich.getHomeTarget().closerToCenterThan(this.ostrich.position(), 7.0D) && !this.stuck && this.closeToHomeTryTicks <= this.adjustedTickDelay(600);
        }

        public void tick() {
            if (this.ostrich.getHomeTarget() != null) {
                BlockPos blockpos = this.ostrich.getHomeTarget();
                boolean flag = blockpos.closerToCenterThan(this.ostrich.position(), 16.0D);
                if (flag) {
                    ++this.closeToHomeTryTicks;
                }

                if (this.ostrich.getNavigation().isDone()) {
                    Vec3 vec3 = Vec3.atBottomCenterOf(blockpos);
                    Vec3 vec31 = DefaultRandomPos.getPosTowards(this.ostrich, 16, 3, vec3, (double) ((float) Math.PI / 10F));
                    if (vec31 == null) {
                        vec31 = DefaultRandomPos.getPosTowards(this.ostrich, 8, 7, vec3, (double) ((float) Math.PI / 2F));
                    }

                    if (vec31 != null && !flag && !this.ostrich.level.getBlockState(new BlockPos(vec31)).is(Blocks.WATER)) {
                        vec31 = DefaultRandomPos.getPosTowards(this.ostrich, 16, 5, vec3, (double) ((float) Math.PI / 2F));
                    }

                    if (vec31 == null) {
                        this.stuck = true;
                        return;
                    }

                    this.ostrich.getNavigation().moveTo(vec31.x, vec31.y, vec31.z, this.speedModifier);
                }

            }
        }
    }

    static class LayEggGoal extends MoveToBlockGoal {
        private final Ostrich ostrich;

        LayEggGoal(Ostrich p_30276_, double p_30277_) {
            super(p_30276_, p_30277_, 16);
            this.ostrich = p_30276_;
        }

        public boolean canUse() {
            return this.ostrich.hasEgg() ? super.canUse() : false;
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.ostrich.hasEgg();
        }

        public void tick() {
            super.tick();
            if (!this.ostrich.isInWater() && this.isReachedTarget()) {
                Level level = this.ostrich.level;
                level.playSound((Player) null, this.blockPos, SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + level.random.nextFloat() * 0.2F);
                level.setBlock(this.blockPos, ModBlocks.OSTRICH_EGG.get().defaultBlockState(), 3);
                this.ostrich.setHasEgg(false);
                this.ostrich.setInLoveTime(600);
                this.ostrich.setHomeTarget(this.blockPos);
            }

        }

        protected boolean isValidTarget(LevelReader p_30280_, BlockPos p_30281_) {
            return !p_30280_.isEmptyBlock(p_30281_.below()) && p_30280_.isEmptyBlock(p_30281_) && p_30280_.canSeeSky(p_30281_);
        }
    }
}
