package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = BiomeBacklog.MODID)
public class ModCreativeTabEvents {
	@SubscribeEvent
	public static void registerCreativeTab(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			event.accept(ModBlocks.PALM_LOG);
			event.accept(ModBlocks.PALM_WOOD);
			event.accept(ModBlocks.STRIPPED_PALM_LOG);
			event.accept(ModBlocks.STRIPPED_PALM_WOOD);

			event.accept(ModBlocks.PALM_PLANKS);
			event.accept(ModBlocks.PALM_SLAB);
			event.accept(ModBlocks.PALM_STAIRS);
			event.accept(ModBlocks.PALM_FENCE);
			event.accept(ModBlocks.PALM_FENCE_GATE);
			event.accept(ModBlocks.PALM_DOOR);
			event.accept(ModBlocks.PALM_TRAPDOOR);
			event.accept(ModBlocks.PALM_SIGN);
			event.accept(ModBlocks.PALM_BUTTON);
			event.accept(ModBlocks.PALM_PRESSURE_PLATE);

			event.accept(ModBlocks.BAOBAB_TRUNK);
			event.accept(ModBlocks.BAOBAB_BARK);

			event.accept(ModBlocks.SALT_BLOCK);
			event.accept(ModBlocks.SALT_SLAB);
			event.accept(ModBlocks.SALT_STAIRS);
			event.accept(ModBlocks.CHISELED_SALT_BLOCK);
			event.accept(ModBlocks.SALT_BRICKS);
			event.accept(ModBlocks.SALT_BRICK_SLAB);
			event.accept(ModBlocks.SALT_BRICK_STAIRS);
			event.accept(ModBlocks.SALT_TILES);
			event.accept(ModBlocks.SALT_TILE_SLAB);
			event.accept(ModBlocks.SALT_TILE_STAIRS);
		}
		if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
			event.accept(ModBlocks.PALM_BUTTON);
			event.accept(ModBlocks.PALM_PRESSURE_PLATE);
		}
		if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
			event.accept(ModBlocks.PALM_LEAVES);
			event.accept(ModBlocks.COCONUT_SAPLING);
			event.accept(ModBlocks.COCONUT);
			event.accept(ModBlocks.DATE_SAPLING);
			event.accept(ModBlocks.DATE_BUNCH);
			event.accept(ModBlocks.BAOBAB_LEAVES);
			event.accept(ModBlocks.BAOBAB_FLOWER);
			event.accept(ModBlocks.SUCCULENT);
			event.accept(ModItems.TUMBLEWEED_SEED);
		}
		if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			event.accept(ModItems.OSTRICH_SPAWN_EGG);
			event.accept(ModItems.VULTURE_SPAWN_EGG);
			event.accept(ModItems.MEERKAT_SPAWN_EGG);
		}

		if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
			event.accept(ModItems.PRICKLY_PEAR);
			event.accept(ModItems.PRICKLESS_PEAR);
			event.accept(ModItems.CACTUS_SALAD);
			event.accept(ModItems.RAW_OSTRICH);
			event.accept(ModItems.COOKED_OSTRICH);
			event.accept(ModItems.DATE);
			event.accept(ModItems.COCONUT_CHUNK);
			event.accept(ModItems.DESICCATED_COCONUT);
		}
		if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			event.accept(ModItems.CACTUS_PAD);
			event.accept(ModItems.COCONUT_HALF);
			event.accept(ModItems.COCONUT_SHELL);
			event.accept(ModItems.VINEGAR_BOTTLE);
			event.accept(ModItems.CALCITE_POWDER_BOTTLE);
			event.accept(ModItems.SUCCULENT_CUTTING);
		}
	}
}