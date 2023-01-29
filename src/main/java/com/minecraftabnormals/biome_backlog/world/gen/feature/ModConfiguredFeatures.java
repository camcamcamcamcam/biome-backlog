package com.minecraftabnormals.biome_backlog.world.gen.feature;

import com.minecraftabnormals.biome_backlog.BiomeBacklog;
import com.minecraftabnormals.biome_backlog.register.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ModConfiguredFeatures {
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_PEAR_CACTUS = FeatureUtils.register(BiomeBacklog.MODID + ":patch_pear_cactus", Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.PEAR_CACTUS.get()))));
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_TUMBLEWEED = FeatureUtils.register(BiomeBacklog.MODID + ":patch_tumbleweed", Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.TUMBLEWEED.get()))));

	public static void init() {

	}

}