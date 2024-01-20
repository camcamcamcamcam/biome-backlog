package com.camcamcamcamcam.biome_backlog.data;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import com.camcamcamcamcam.biome_backlog.register.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

import static com.camcamcamcamcam.biome_backlog.BiomeBacklog.prefix;

public class ItemModelGenerator extends ItemModelProvider {
	public ItemModelGenerator(PackOutput generator, ExistingFileHelper existingFileHelper) {
		super(generator, BiomeBacklog.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		this.toBlockModel(ModBlocks.PEAR_CACTUS.get(), "pear_cactus_0");
		this.toBlockModel(ModBlocks.STRIPPED_PEAR_CACTUS.get(), "stripped_pear_cactus_0");
		this.singleTex(ModItems.CACTUS_PAD);
		this.singleTex(ModItems.CACTUS_SALAD);
		this.singleTex(ModItems.PRICKLY_PEAR);
		this.singleTex(ModItems.PRICKLESS_PEAR);
		this.singleTex(ModItems.TUMBLEWEED_SEED);

		this.singleTex(ModItems.BAOBAB_FRUIT);
		this.basicItem(ModBlocks.OSTRICH_EGG.get().asItem());
		this.singleTex(ModItems.RAW_OSTRICH);
		this.singleTex(ModItems.COOKED_OSTRICH);

		this.toBlock(ModBlocks.DATE_BUNCH);
		this.basicItem(ModBlocks.COCONUT.get().asItem());

		this.singleTex(ModItems.DATE);
		// this.singleTex(ModItems.COCONUT);
		this.singleTex(ModItems.COCONUT_HALF);
		this.singleTex(ModItems.COCONUT_CHUNK);
		this.singleTex(ModItems.DESICCATED_COCONUT);
		this.singleTex(ModItems.COCONUT_SHELL);
		this.singleTex(ModItems.VINEGAR_BOTTLE);
		this.singleTex(ModItems.CALCITE_POWDER_BOTTLE);
		this.singleTex(ModItems.PALM_BOAT);
		this.singleTex(ModItems.PALM_BOAT_CHEST);

		this.singleTex(ModItems.SUCCULENT_CUTTING);

		this.egg(ModItems.MEERKAT_SPAWN_EGG);
		this.egg(ModItems.OSTRICH_SPAWN_EGG);
		this.egg(ModItems.VULTURE_SPAWN_EGG);

		this.toBlock(ModBlocks.MOUND);
		this.toBlock(ModBlocks.TERMITE_MOUND);

		this.toBlock(ModBlocks.BAOBAB_TRUNK);
		this.toBlock(ModBlocks.BAOBAB_BARK);
		this.toBlock(ModBlocks.BAOBAB_LEAVES);

		this.toBlock(ModBlocks.BURROW);

		this.toBlock(ModBlocks.PALM_LOG);
		this.toBlock(ModBlocks.PALM_LEAVES);
		this.itemBlockFlat(ModBlocks.PALM_LEAVES_HANGING);
		this.toBlock(ModBlocks.THATCH_BLOCK);
		this.toBlock(ModBlocks.THATCH_STAIRS);

		this.toBlock(ModBlocks.STRIPPED_PALM_LOG);
		this.toBlock(ModBlocks.PALM_WOOD);
		this.toBlock(ModBlocks.STRIPPED_PALM_WOOD);
		this.toBlock(ModBlocks.PALM_PLANKS);
		this.itemBlockFlat(ModBlocks.COCONUT_SAPLING);
		this.itemBlockFlat(ModBlocks.DATE_SAPLING);

		this.basicItem(ModBlocks.PALM_DOOR.get().asItem());
		this.toBlockModel(ModBlocks.PALM_TRAPDOOR.get(), "palm_trapdoor_bottom");
		this.basicItem(ModBlocks.PALM_SIGN.get().asItem());
		this.woodenFence(ModBlocks.PALM_FENCE, ModBlocks.PALM_PLANKS);
		this.toBlock(ModBlocks.PALM_FENCE_GATE);
		this.toBlock(ModBlocks.PALM_STAIRS);
		this.toBlock(ModBlocks.PALM_SLAB);
		this.woodenButton(ModBlocks.PALM_BUTTON, ModBlocks.PALM_PLANKS);
		this.toBlock(ModBlocks.PALM_PRESSURE_PLATE);

		this.toBlock(ModBlocks.SALT_BLOCK);
		this.toBlock(ModBlocks.SALT_BRICKS);
		this.toBlock(ModBlocks.SALT_TILES);
		this.toBlock(ModBlocks.CHISELED_SALT_BLOCK);
		this.toBlock(ModBlocks.SALT_LAMP);
		this.basicItem(ModBlocks.SALT_TRAIL.get().asItem());

		this.toBlock(ModBlocks.SALT_STAIRS);
		this.toBlock(ModBlocks.SALT_BRICK_STAIRS);
		this.toBlock(ModBlocks.SALT_TILE_STAIRS);

		this.toBlockModel(ModBlocks.SALT_SLAB.get(), "salt_slab_bottom");
		this.toBlockModel(ModBlocks.SALT_BRICK_SLAB.get(), "salt_brick_slab_bottom");
		this.toBlockModel(ModBlocks.SALT_TILE_SLAB.get(), "salt_tile_slab_bottom");

		this.itemBlockFlat(ModBlocks.SUCCULENT.get(), "succulent1");

		// this.toBlock(ModBlocks.WORMHOLE);
	}

	private ItemModelBuilder generated(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(name, "item/generated");
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}

	private ItemModelBuilder singleTexTool(Supplier<Item> item) {
		return tool(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), prefix("item/" + BuiltInRegistries.ITEM.getKey(item.get()).getPath()));
	}

	private ItemModelBuilder tool(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(name, "item/handheld");
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}

	private ItemModelBuilder singleTex(Supplier<? extends Item> item) {
		return generated(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), prefix("item/" + BuiltInRegistries.ITEM.getKey(item.get()).getPath()));
	}

	private ItemModelBuilder bowItem(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(name, "item/bow");
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}

	private ItemModelBuilder bowTex(Supplier<Item> item, ModelFile pull0, ModelFile pull1, ModelFile pull2) {
		return bowItem(itemName(item.get()), prefix("item/" + itemName(item.get())))
				.override().predicate(new ResourceLocation("pulling"), 1).model(pull0).end()
				.override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), (float) 0.65).model(pull1).end()
				.override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), (float) 0.9).model(pull2).end();
	}

	private void woodenButton(Supplier<? extends Block> button, Supplier<Block> wood) {
		getBuilder(BuiltInRegistries.BLOCK.getKey(button.get()).getPath())
				.parent(getExistingFile(mcLoc("block/button_inventory")))
				.texture("texture", "block/" + BuiltInRegistries.BLOCK.getKey(wood.get()).getPath());
	}

	private void woodenFence(Supplier<? extends Block> fence, Supplier<? extends Block> block) {
		getBuilder(BuiltInRegistries.BLOCK.getKey(fence.get()).getPath())
				.parent(getExistingFile(mcLoc("block/fence_inventory")))
				.texture("texture", "block/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath());
	}

	private void woodenFence(Block fence, String texture) {
		getBuilder(BuiltInRegistries.BLOCK.getKey(fence).getPath())
				.parent(getExistingFile(mcLoc("block/fence_inventory")))
				.texture("texture", "block/" + texture);
	}

	private void toBlock(Supplier<? extends Block> b) {
		toBlockModel(b.get(), BuiltInRegistries.BLOCK.getKey(b.get()).getPath());
	}
	private void toBlockModel(Block b, String model) {
		toBlockModel(b, prefix("block/" + model));
	}

	private void toBlockModel(Block b, ResourceLocation model) {
		withExistingParent(BuiltInRegistries.BLOCK.getKey(b).getPath(), model);
	}

	public ItemModelBuilder itemBlockFlat(Supplier<? extends Block> block) {
		return itemBlockFlat(block.get(), blockName(block.get()));
	}

	public ItemModelBuilder itemBlockFlat(Block block, String name) {
		return withExistingParent(blockName(block), mcLoc("item/generated"))
				.texture("layer0", modLoc("block/" + name));
	}

	public ItemModelBuilder egg(Supplier<Item> item) {
		return withExistingParent(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), mcLoc("item/template_spawn_egg"));
	}

	public String blockName(Block block) {
		return BuiltInRegistries.BLOCK.getKey(block).getPath();
	}

	public String itemName(Item item) {
		return BuiltInRegistries.ITEM.getKey(item).getPath();
	}

	@Override
	public String getName() {
		return "BiomeVoteLosers item and itemblock models";
	}
}