package com.minecraftabnormals.biome_vote_losers.data;

import com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers;
import com.minecraftabnormals.biome_vote_losers.register.ModBlocks;
import com.minecraftabnormals.biome_vote_losers.register.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers.prefix;

public class ItemModelGenerator extends ItemModelProvider {
	public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, BiomeVoteLosers.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		this.toBlockModel(ModBlocks.PEAR_CACTUS.get(), "pear_cactus_0");
		this.toBlockModel(ModBlocks.STRIPPED_PEAR_CACTUS.get(), "stripped_pear_cactus_0");
		this.singleTex(ModItems.CACTUS_PAD);
		this.singleTex(ModItems.PRICKLY_PEAR);
		this.singleTex(ModItems.TUMBLEWEED_SEED);

		this.singleTex(ModItems.BAOBAB_FRUIT);
		this.basicItem(ModBlocks.OSTRICH_EGG.get().asItem());

		this.singleTex(ModItems.DATE);
		this.singleTex(ModItems.COCONUT);
		this.singleTex(ModItems.COCONUT_HALF);
		this.singleTex(ModItems.COCONUT_CHUNK);
		this.singleTex(ModItems.DESICCATED_COCONUT);
		this.singleTex(ModItems.COCONUT_SHELL);
		this.singleTex(ModItems.VINEGAR_BOTTLE);
		this.singleTex(ModItems.CALCITE_POWDER_BOTTLE);

		this.singleTex(ModItems.SUCCULENT_CUTTING);

		this.egg(ModItems.MEERKAT_SPAWN_EGG);
		this.egg(ModItems.OSTRICH_SPAWN_EGG);

		this.toBlock(ModBlocks.MOUND);
		this.toBlock(ModBlocks.TERMITE_MOUND);

		this.toBlock(ModBlocks.BAOBAB_TRUNK);
		this.toBlock(ModBlocks.BAOBAB_LEAVES);

		this.toBlock(ModBlocks.BURROW);

		this.toBlock(ModBlocks.PALM_LOG);
		this.toBlock(ModBlocks.PALM_LEAVES);
		this.itemBlockFlat(ModBlocks.PALM_LEAVES_HANGING);
		this.toBlock(ModBlocks.STRIPPED_PALM_LOG);
		this.toBlock(ModBlocks.PALM_WOOD);
		this.toBlock(ModBlocks.STRIPPED_PALM_WOOD);
		this.toBlock(ModBlocks.PALM_PLANKS);
		this.itemBlockFlat(ModBlocks.PALM_SAPLING);
		this.basicItem(ModBlocks.PALM_DOOR.get().asItem());
		this.toBlockModel(ModBlocks.PALM_TRAPDOOR.get(), "palm_trapdoor_bottom");
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
		this.basicItem(ModBlocks.SALT.get().asItem());

		this.itemBlockFlat(ModBlocks.SUCCULENT);
	}

	private ItemModelBuilder generated(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(name, "item/generated");
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}

	private ItemModelBuilder singleTexTool(RegistryObject<Item> item) {
		return tool(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), prefix("item/" + ForgeRegistries.ITEMS.getKey(item.get()).getPath()));
	}

	private ItemModelBuilder tool(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(name, "item/handheld");
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}

	private ItemModelBuilder singleTex(RegistryObject<? extends Item> item) {
		return generated(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), prefix("item/" + ForgeRegistries.ITEMS.getKey(item.get()).getPath()));
	}

	private ItemModelBuilder bowItem(String name, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(name, "item/bow");
		for (int i = 0; i < layers.length; i++) {
			builder = builder.texture("layer" + i, layers[i]);
		}
		return builder;
	}

	private ItemModelBuilder bowTex(RegistryObject<Item> item, ModelFile pull0, ModelFile pull1, ModelFile pull2) {
		return bowItem(item.getId().getPath(), prefix("item/" + item.getId().getPath()))
				.override().predicate(new ResourceLocation("pulling"), 1).model(pull0).end()
				.override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), (float) 0.65).model(pull1).end()
				.override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), (float) 0.9).model(pull2).end();
	}

	private void woodenButton(RegistryObject<? extends Block> button, RegistryObject<Block> wood) {
		getBuilder(ForgeRegistries.BLOCKS.getKey(button.get()).getPath())
				.parent(getExistingFile(mcLoc("block/button_inventory")))
				.texture("texture", "block/" + ForgeRegistries.BLOCKS.getKey(wood.get()).getPath());
	}

	private void woodenFence(RegistryObject<? extends Block> fence, RegistryObject<? extends Block> block) {
		getBuilder(ForgeRegistries.BLOCKS.getKey(fence.get()).getPath())
				.parent(getExistingFile(mcLoc("block/fence_inventory")))
				.texture("texture", "block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath());
	}

	private void woodenFence(Block fence, String texture) {
		getBuilder(ForgeRegistries.BLOCKS.getKey(fence).getPath())
				.parent(getExistingFile(mcLoc("block/fence_inventory")))
				.texture("texture", "block/" + texture);
	}

	private void toBlock(RegistryObject<? extends Block> b) {
		toBlockModel(b.get(), ForgeRegistries.BLOCKS.getKey(b.get()).getPath());
	}
	private void toBlockModel(Block b, String model) {
		toBlockModel(b, prefix("block/" + model));
	}

	private void toBlockModel(Block b, ResourceLocation model) {
		withExistingParent(ForgeRegistries.BLOCKS.getKey(b).getPath(), model);
	}

	public ItemModelBuilder itemBlockFlat(RegistryObject<? extends Block> block) {
		return itemBlockFlat(block.get(), blockName(block.get()));
	}

	public ItemModelBuilder itemBlockFlat(Block block, String name) {
		return withExistingParent(blockName(block), mcLoc("item/generated"))
				.texture("layer0", modLoc("block/" + name));
	}

	public ItemModelBuilder egg(RegistryObject<Item> item) {
		return withExistingParent(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), mcLoc("item/template_spawn_egg"));
	}

	public String blockName(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block).getPath();
	}

	@Override
	public String getName() {
		return "BiomeVoteLosers item and itemblock models";
	}
}