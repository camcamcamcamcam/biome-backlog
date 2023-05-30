package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.world.gen.trunk.PalmTrunkPlacer;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class ModTrunkPlacerTypes {
	public static final TrunkPlacerType<PalmTrunkPlacer> PALM_TRUNK_PLACER = register(BiomeBacklog.prefixOnString("palm_trunk_placer"), PalmTrunkPlacer.CODEC);

	private static <P extends TrunkPlacer> TrunkPlacerType<P> register(String p_70327_, Codec<P> p_70328_) {
		return Registry.register(Registry.TRUNK_PLACER_TYPES, p_70327_, new TrunkPlacerType<>(p_70328_));
	}

	public static void init() {

	}
}
