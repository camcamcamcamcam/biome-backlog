package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.world.gen.trunk.PalmTrunkPlacer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModTrunkPlacerTypes {
	public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPE = DeferredRegister.create(BuiltInRegistries.TRUNK_PLACER_TYPE, BiomeBacklog.MODID);

	public static final Supplier<TrunkPlacerType<PalmTrunkPlacer>> PALM_TRUNK_PLACER = TRUNK_PLACER_TYPE.register("palm_trunk_placer", () -> new TrunkPlacerType<>(PalmTrunkPlacer.CODEC));

}
