package com.minecraftabnormals.biome_vote_losers.world.level.entity;

import com.minecraftabnormals.biome_vote_losers.register.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Ostrich extends Animal {
    public AnimationState idlingState = new AnimationState();
    public AnimationState walkingState = new AnimationState();
    public AnimationState runningState = new AnimationState();
    public AnimationState dippingState = new AnimationState();
    public AnimationState kickingState = new AnimationState();

    public Ostrich(EntityType<? extends Ostrich> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_DAMAGE, 5.0F).add(Attributes.FOLLOW_RANGE, 20.0F);
    }

    @Override
    public void tick() {
        if (this.isMoving() && level.isClientSide()) {
            idlingState.stop();
            walkingState.startIfStopped(this.tickCount);
        } else if (level.isClientSide()) {
            walkingState.stop();
            idlingState.startIfStopped(this.tickCount);
        }
        super.tick();
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(6, new BreedGoal(this, 0.85D));
        this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));

    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        Ostrich ostrich = ModEntities.OSTRICH.get().create(p_146743_);
        return ostrich;
    }
}
