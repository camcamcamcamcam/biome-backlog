package com.minecraftabnormals.biome_vote_losers.world.gen.feature;

import com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class ModPlacements {
	public static final Holder<PlacedFeature> PATCH_PEAR_CACTUS = PlacementUtils.register(BiomeVoteLosers.MODID + ":patch_pear_cactus", ModConfiguredFeatures.PATCH_PEAR_CACTUS, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

	public static void init() {

	}

}