package com.camcamcamcamcam.biome_backlog.world.gen.feature;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

import static net.minecraft.data.worldgen.placement.VegetationPlacements.treePlacement;

public class ModPlacements {
	public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, BiomeBacklog.MODID);

	public static final RegistryObject<PlacedFeature> PATCH_PEAR_CACTUS = PLACED_FEATURES.register("patch_pear_cactus", () -> new PlacedFeature(ModConfiguredFeatures.PATCH_PEAR_CACTUS.getHolder().orElseThrow(), List.of(RarityFilter.onAverageOnceEvery(14), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome())));
	public static final RegistryObject<PlacedFeature> PATCH_TUMBLEWEED = PLACED_FEATURES.register("patch_tumbleweed", () -> new PlacedFeature(ModConfiguredFeatures.PATCH_TUMBLEWEED.getHolder().orElseThrow(), List.of(RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome())));
	public static final RegistryObject<PlacedFeature> PALM_TREE_COCONUT = PLACED_FEATURES.register("palm_tree_coconut", () -> new PlacedFeature(ModConfiguredFeatures.PALM_COCONUTS.getHolder().orElseThrow(), treePlacement(PlacementUtils.countExtra(1, 0.05F, 1), ModBlocks.COCONUT_SAPLING.get())));
	public static final RegistryObject<PlacedFeature> PALM_TREE_DATE = PLACED_FEATURES.register("palm_tree_date", () -> new PlacedFeature(ModConfiguredFeatures.PALM_COCONUTS.getHolder().orElseThrow(), treePlacement(RarityFilter.onAverageOnceEvery(26), ModBlocks.DATE_SAPLING.get())));

}