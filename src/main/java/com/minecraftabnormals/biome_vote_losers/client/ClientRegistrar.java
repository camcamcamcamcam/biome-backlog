package com.minecraftabnormals.biome_vote_losers.client;

import com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers;
import com.minecraftabnormals.biome_vote_losers.register.ModBlocks;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = BiomeVoteLosers.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {
	public static void setup(FMLCommonSetupEvent event) {

	}

	@SubscribeEvent
	public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
		event.register((state, reader, pos, color) -> {
			return reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColor.getDefaultColor();
		}, ModBlocks.BAOBAB_LEAVES.get());
	}

	@SubscribeEvent
	public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
		event.register((stack, i) -> {
			return FoliageColor.getDefaultColor();
		}, ModBlocks.BAOBAB_LEAVES.get().asItem());
	}
}
