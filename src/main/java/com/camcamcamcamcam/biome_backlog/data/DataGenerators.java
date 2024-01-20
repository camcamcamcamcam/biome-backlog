package com.camcamcamcamcam.biome_backlog.data;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = BiomeBacklog.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = event.getGenerator().getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		generator.addProvider(event.includeClient(), new BlockstateGenerator(packOutput, existingFileHelper));
		generator.addProvider(event.includeClient(), new ItemModelGenerator(packOutput, existingFileHelper));
		ModBlockTagProvider blockTags = new ModBlockTagProvider(packOutput, lookupProvider, existingFileHelper);
		generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new ModItemTagProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), LootGenerator.create(packOutput));
		generator.addProvider(event.includeServer(), new RecipeGenerator(packOutput, lookupProvider));
		generator.addProvider(event.includeServer(), new LanguageGenerator(packOutput));
		generator.addProvider(event.includeServer(), new WorldGenerator(packOutput, lookupProvider));
	}
}