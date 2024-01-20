package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, BiomeBacklog.MODID);

    public static Supplier<SimpleParticleType> CALCITE_POWDER_BUBBLE = PARTICLES.register("calcite_powder_bubble", () -> new SimpleParticleType(false));
    public static Supplier<SimpleParticleType> CALCITE_POWDER_BUBBLE_POP = PARTICLES.register("calcite_powder_bubble_pop", () -> new SimpleParticleType(false));
}
