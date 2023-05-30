package com.camcamcamcamcam.biome_backlog.data;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = BiomeBacklog.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		generator.addProvider(event.includeClient(), new BlockstateGenerator(generator, existingFileHelper));
		generator.addProvider(event.includeClient(), new ItemModelGenerator(generator, existingFileHelper));
		ModBlockTagProvider blockTags = new ModBlockTagProvider(generator, existingFileHelper);
		generator.addProvider(event.includeServer(), blockTags);
		generator.addProvider(event.includeServer(), new ModItemTagProvider(generator, blockTags, existingFileHelper));
		generator.addProvider(event.includeServer(), new LootGenerator(generator));
		generator.addProvider(event.includeServer(), new RecipeGenerator(generator));
		generator.addProvider(event.includeServer(), new LanguageGenerator(generator));
	}
}