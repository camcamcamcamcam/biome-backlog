package com.camcamcamcamcam.biome_backlog.world.gen.grower;

import com.camcamcamcamcam.biome_backlog.world.gen.feature.ModConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class PalmTreeDateGrower extends AbstractTreeGrower {
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource p_256119_, boolean p_256536_) {
		return ModConfiguredFeatures.PALM_DATE;
	}
}