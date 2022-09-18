package com.minecraftabnormals.biome_vote_losers.world.level.entity;

import com.minecraftabnormals.biome_vote_losers.register.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Vulture extends TamableAnimal {
	public Vulture(EntityType<? extends Vulture> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 22.0D).add(Attributes.MOVEMENT_SPEED, 0.26D).add(Attributes.ATTACK_DAMAGE, 4.0F).add(Attributes.FOLLOW_RANGE, 30.0F);
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
}
