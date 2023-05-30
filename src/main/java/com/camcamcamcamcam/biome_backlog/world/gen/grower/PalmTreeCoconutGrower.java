package com.camcamcamcamcam.biome_backlog.world.gen.grower;

import com.camcamcamcamcam.biome_backlog.world.gen.feature.ModConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

public class PalmTreeCoconutGrower extends AbstractTreeGrower {
	@Nullable
	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource p_256119_, boolean p_256536_) {
		return ModConfiguredFeatures.PALM_COCONUTS.getHolder().get();
	}
}