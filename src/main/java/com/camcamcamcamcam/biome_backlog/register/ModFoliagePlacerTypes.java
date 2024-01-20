package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.world.gen.foliage.PalmTreeFoliagePlacer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFoliagePlacerTypes {
	public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPE = DeferredRegister.create(BuiltInRegistries.FOLIAGE_PLACER_TYPE, BiomeBacklog.MODID);

	public static final Supplier<FoliagePlacerType<PalmTreeFoliagePlacer>> PALM_TREE_FOLIAGE = FOLIAGE_PLACER_TYPE.register("palm_tree_foliage", () -> new FoliagePlacerType<>(PalmTreeFoliagePlacer.CODEC));

}
