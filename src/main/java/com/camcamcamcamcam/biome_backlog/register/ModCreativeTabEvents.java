package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = BiomeBacklog.MODID)
public class ModCreativeTabEvents {
	@SubscribeEvent
	public static void registerCreativeTab(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			event.accept(ModBlocks.PALM_LOG.get());
			event.accept(ModBlocks.PALM_WOOD.get());
			event.accept(ModBlocks.STRIPPED_PALM_LOG.get());
			event.accept(ModBlocks.STRIPPED_PALM_WOOD.get());

			event.accept(ModBlocks.PALM_PLANKS.get());
			event.accept(ModBlocks.PALM_SLAB.get());
			event.accept(ModBlocks.PALM_STAIRS.get());
			event.accept(ModBlocks.PALM_FENCE.get());
			event.accept(ModBlocks.PALM_FENCE_GATE.get());
			event.accept(ModBlocks.PALM_DOOR.get());
			event.accept(ModBlocks.PALM_TRAPDOOR.get());
			event.accept(ModBlocks.PALM_SIGN.get());
			event.accept(ModBlocks.PALM_BUTTON.get());
			event.accept(ModBlocks.PALM_PRESSURE_PLATE.get());

			event.accept(ModBlocks.BAOBAB_TRUNK.get());
			event.accept(ModBlocks.BAOBAB_BARK.get());

			event.accept(ModBlocks.SALT_BLOCK.get());
			event.accept(ModBlocks.SALT_SLAB.get());
			event.accept(ModBlocks.SALT_STAIRS.get());
			event.accept(ModBlocks.CHISELED_SALT_BLOCK.get());
			event.accept(ModBlocks.SALT_BRICKS.get());
			event.accept(ModBlocks.SALT_BRICK_SLAB.get());
			event.accept(ModBlocks.SALT_BRICK_STAIRS.get());
			event.accept(ModBlocks.SALT_TILES.get());
			event.accept(ModBlocks.SALT_TILE_SLAB.get());
			event.accept(ModBlocks.SALT_TILE_STAIRS.get());
		}
		if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
			event.accept(ModBlocks.PALM_BUTTON.get());
			event.accept(ModBlocks.PALM_PRESSURE_PLATE.get());
		}
		if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
			event.accept(ModBlocks.PALM_LEAVES.get());
			event.accept(ModBlocks.PALM_LEAVES_HANGING.get());
			event.accept(ModBlocks.COCONUT_SAPLING.get());
			event.accept(ModBlocks.COCONUT.get());
			event.accept(ModBlocks.DATE_SAPLING.get());
			event.accept(ModBlocks.DATE_BUNCH.get());
			event.accept(ModBlocks.BAOBAB_LEAVES.get());
			event.accept(ModBlocks.BAOBAB_FLOWER.get());
			event.accept(ModBlocks.SUCCULENT.get());
			event.accept(ModItems.TUMBLEWEED_SEED.get());
		}
		if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			event.accept(ModItems.OSTRICH_SPAWN_EGG.get());
			event.accept(ModItems.VULTURE_SPAWN_EGG.get());
			event.accept(ModItems.MEERKAT_SPAWN_EGG.get());
		}

		if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
			event.accept(ModItems.PRICKLY_PEAR.get());
			event.accept(ModItems.PRICKLESS_PEAR.get());
			event.accept(ModItems.CACTUS_SALAD.get());
			event.accept(ModItems.RAW_OSTRICH.get());
			event.accept(ModItems.COOKED_OSTRICH.get());
			event.accept(ModItems.DATE.get());
			event.accept(ModItems.COCONUT_CHUNK.get());
			event.accept(ModItems.DESICCATED_COCONUT.get());
		}
		if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			event.accept(ModItems.CACTUS_PAD.get());
			event.accept(ModItems.COCONUT_HALF.get());
			event.accept(ModItems.COCONUT_SHELL.get());
			event.accept(ModItems.VINEGAR_BOTTLE.get());
			event.accept(ModItems.CALCITE_POWDER_BOTTLE.get());
			event.accept(ModItems.SUCCULENT_CUTTING.get());
		}
		if (event.getTabKey() == CreativeModeTabs.COMBAT) {
			event.accept(ModItems.PALM_SHIELD.get());
		}
	}
}