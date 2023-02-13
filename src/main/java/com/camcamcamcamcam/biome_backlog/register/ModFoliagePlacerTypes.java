package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.world.gen.foliage.PalmTreeFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFoliagePlacerTypes {
	public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPE = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, BiomeBacklog.MODID);

	public static final RegistryObject<FoliagePlacerType<PalmTreeFoliagePlacer>> PALM_TREE_FOLIAGE = FOLIAGE_PLACER_TYPE.register("palm_tree_foliage", () -> new FoliagePlacerType<>(PalmTreeFoliagePlacer.CODEC));

}
