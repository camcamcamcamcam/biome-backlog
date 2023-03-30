package com.camcamcamcamcam.biome_backlog.world.gen.feature;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import static net.minecraft.data.worldgen.placement.VegetationPlacements.treePlacement;

public class ModPlacements {
	public static final ResourceKey<PlacedFeature> PATCH_PEAR_CACTUS = registerKey("patch_pear_cactus");
	public static final ResourceKey<PlacedFeature> PATCH_TUMBLEWEED = registerKey("patch_tumbleweed");
	public static final ResourceKey<PlacedFeature> PALM_TREE_COCONUT = registerKey("palm_tree_coconut");
	public static final ResourceKey<PlacedFeature> PALM_TREE_DATE = registerKey("palm_tree_date");

	public static ResourceKey<PlacedFeature> registerKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, BiomeBacklog.prefix(name));
	}

	public static void bootstrapPlacements(BootstapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeature = context.lookup(Registries.CONFIGURED_FEATURE);
		PlacementUtils.register(context, PATCH_PEAR_CACTUS, configuredFeature.getOrThrow(ModConfiguredFeatures.PATCH_PEAR_CACTUS), RarityFilter.onAverageOnceEvery(25), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, PATCH_TUMBLEWEED, configuredFeature.getOrThrow(ModConfiguredFeatures.PATCH_TUMBLEWEED), RarityFilter.onAverageOnceEvery(22), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, PALM_TREE_COCONUT, configuredFeature.getOrThrow(ModConfiguredFeatures.PALM_COCONUTS), treePlacement(PlacementUtils.countExtra(1, 0.05F, 1), ModBlocks.COCONUT_SAPLING.get()));
		PlacementUtils.register(context, PALM_TREE_DATE, configuredFeature.getOrThrow(ModConfiguredFeatures.PALM_DATE), treePlacement(RarityFilter.onAverageOnceEvery(26), ModBlocks.DATE_SAPLING.get()));
	}

}