package com.minecraftabnormals.biome_backlog.register;

import com.minecraftabnormals.biome_backlog.BiomeBacklog;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BiomeBacklog.MODID);

    public static RegistryObject<SimpleParticleType> CALCITE_POWDER_BUBBLE = PARTICLES.register("calcite_powder_bubble", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> CALCITE_POWDER_BUBBLE_POP = PARTICLES.register("calcite_powder_bubble_pop", () -> new SimpleParticleType(false));
}
