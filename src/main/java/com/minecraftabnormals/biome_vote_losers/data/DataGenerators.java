package com.minecraftabnormals.biome_vote_losers.data;

import com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BiomeVoteLosers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		event.getGenerator().addProvider(event.includeServer(), new BlockstateGenerator(event.getGenerator(), event.getExistingFileHelper()));
		event.getGenerator().addProvider(event.includeServer(), new ItemModelGenerator(event.getGenerator(), event.getExistingFileHelper()));


		event.getGenerator().addProvider(event.includeServer(), new LootGenerator(event.getGenerator()));
		//event.getGenerator().addProvider(event.includeServer(), new CraftingGenerator(event.getGenerator()));
	}
}