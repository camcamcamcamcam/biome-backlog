package com.camcamcamcamcam.biome_backlog.world.gen.feature;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
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

public class ModPlacements {
	public static final ResourceKey<PlacedFeature> PATCH_PEAR_CACTUS = registerKey("patch_pear_cactus");
	public static final ResourceKey<PlacedFeature> PATCH_TUMBLEWEED = registerKey("patch_tumbleweed");

	public static ResourceKey<PlacedFeature> registerKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, BiomeBacklog.prefix(name));
	}

	public static void bootstrapPlacements(BootstapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeature = context.lookup(Registries.CONFIGURED_FEATURE);
		PlacementUtils.register(context, PATCH_PEAR_CACTUS, configuredFeature.getOrThrow(ModConfiguredFeatures.PATCH_PEAR_CACTUS), RarityFilter.onAverageOnceEvery(28), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(context, PATCH_TUMBLEWEED, configuredFeature.getOrThrow(ModConfiguredFeatures.PATCH_TUMBLEWEED), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
	}

}