package com.camcamcamcamcam.biome_backlog.data;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.world.gen.feature.ModConfiguredFeatures;
import com.camcamcamcamcam.biome_backlog.world.gen.feature.ModPlacements;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WorldGenerator extends DatapackBuiltinEntriesProvider {

	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrapConfiguredFeature)
			.add(Registries.PLACED_FEATURE, ModPlacements::bootstrapPlacements);
	;


	public WorldGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, BUILDER, Set.of("minecraft", BiomeBacklog.MODID));
	}
}