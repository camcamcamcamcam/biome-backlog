package com.minecraftabnormals.biome_vote_losers.world.level.entity;

import com.minecraftabnormals.biome_vote_losers.register.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Ostrich extends Animal {
    public Ostrich(EntityType<? extends Ostrich> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_DAMAGE, 5.0F).add(Attributes.FOLLOW_RANGE, 20.0F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        Ostrich ostrich = ModEntities.OSTRICH.get().create(p_146743_);
        return ostrich;
    }
}
