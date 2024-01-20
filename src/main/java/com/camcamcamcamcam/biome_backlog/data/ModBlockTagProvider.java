package com.camcamcamcamcam.biome_backlog.data;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import com.camcamcamcamcam.biome_backlog.register.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
	public ModBlockTagProvider(PackOutput generator, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(generator, lookupProvider, BiomeBacklog.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider p_256380_) {
		this.tag(ModTags.COLORED_BEDS).add(Blocks.RED_BED, Blocks.BLACK_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.CYAN_BED, Blocks.GRAY_BED, Blocks.GREEN_BED, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_GRAY_BED, Blocks.LIME_BED, Blocks.MAGENTA_BED, Blocks.ORANGE_BED, Blocks.PINK_BED, Blocks.PURPLE_BED, Blocks.YELLOW_BED);
		this.tag(ModTags.COLORED_SHULKER_BOXS).add(Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX);


		this.tag(BlockTags.MINEABLE_WITH_AXE)
				.add(ModBlocks.STRIPPED_PALM_LOG.get(), ModBlocks.STRIPPED_PALM_WOOD.get(), ModBlocks.PALM_LOG.get(), ModBlocks.PALM_PLANKS.get(), ModBlocks.PALM_WOOD.get())
				.add(ModBlocks.PALM_BUTTON.get(), ModBlocks.PALM_DOOR.get(), ModBlocks.PALM_TRAPDOOR.get(), ModBlocks.PALM_FENCE.get(), ModBlocks.PALM_FENCE_GATE.get(), ModBlocks.PALM_PRESSURE_PLATE.get(), ModBlocks.PALM_SIGN.get(), ModBlocks.PALM_WALL_SIGN.get(), ModBlocks.PALM_STAIRS.get(), ModBlocks.PALM_SLAB.get())
				.add(ModBlocks.BAOBAB_TRUNK.get(), ModBlocks.BAOBAB_BARK.get()).add(ModBlocks.COCONUT.get())
				.add(ModBlocks.PEAR_CACTUS.get(), ModBlocks.STRIPPED_PEAR_CACTUS.get())
				.add(ModBlocks.TERMITE_MOUND.get(), ModBlocks.MOUND.get());
		this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.BURROW.get());
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.SALT_BLOCK.get(), ModBlocks.SALT_BRICKS.get(), ModBlocks.SALT_SLAB.get(), ModBlocks.SALT_STAIRS.get(), ModBlocks.SALT_TILES.get(), ModBlocks.SALT_TILE_SLAB.get(), ModBlocks.SALT_TILE_STAIRS.get(), ModBlocks.SALT_BRICKS.get(), ModBlocks.SALT_BRICK_SLAB.get(), ModBlocks.SALT_BRICK_STAIRS.get(), ModBlocks.CHISELED_SALT_BLOCK.get())
				.add(ModBlocks.SALT_LAMP.get()).add(ModBlocks.OSTRICH_EGG.get());
		this.tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.THATCH_BLOCK.get(), ModBlocks.THATCH_STAIRS.get()).add(ModBlocks.PALM_LEAVES.get(), ModBlocks.PALM_LEAVES_HANGING.get(), ModBlocks.BAOBAB_LEAVES.get());
		this.tag(BlockTags.LEAVES).add(ModBlocks.PALM_LEAVES.get(), ModBlocks.BAOBAB_LEAVES.get());
		this.tag(BlockTags.WOODEN_SLABS).add(ModBlocks.PALM_SLAB.get());
		this.tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.PALM_STAIRS.get());


		this.tag(ModTags.PALM_WOOD)
				.add(ModBlocks.STRIPPED_PALM_LOG.get(), ModBlocks.STRIPPED_PALM_WOOD.get(), ModBlocks.PALM_LOG.get(), ModBlocks.PALM_PLANKS.get(), ModBlocks.PALM_WOOD.get())
				.add(ModBlocks.PALM_BUTTON.get(), ModBlocks.PALM_DOOR.get(), ModBlocks.PALM_TRAPDOOR.get(), ModBlocks.PALM_FENCE.get(), ModBlocks.PALM_FENCE_GATE.get(), ModBlocks.PALM_PRESSURE_PLATE.get(), ModBlocks.PALM_SIGN.get(), ModBlocks.PALM_WALL_SIGN.get(), ModBlocks.PALM_STAIRS.get(), ModBlocks.PALM_SLAB.get());

		this.tag(BlockTags.LOGS_THAT_BURN).add(ModBlocks.STRIPPED_PALM_LOG.get(), ModBlocks.STRIPPED_PALM_WOOD.get(), ModBlocks.PALM_LOG.get(), ModBlocks.BAOBAB_TRUNK.get(), ModBlocks.BAOBAB_BARK.get());
		this.tag(ModTags.COLORED_CONCRETE).add(
						//Blocks.WHITE_CONCRETE,
						Blocks.ORANGE_CONCRETE, Blocks.MAGENTA_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE,
						Blocks.YELLOW_CONCRETE, Blocks.LIME_CONCRETE, Blocks.PINK_CONCRETE, Blocks.GRAY_CONCRETE,
						Blocks.LIGHT_GRAY_CONCRETE, Blocks.CYAN_CONCRETE, Blocks.PURPLE_CONCRETE, Blocks.BLUE_CONCRETE,
						Blocks.BROWN_CONCRETE, Blocks.GREEN_CONCRETE, Blocks.RED_CONCRETE, Blocks.BLACK_CONCRETE)
				.addOptionalTag(new ResourceLocation("tinted:concrete"));
		this.tag(ModTags.COLORED_CONCRETE_POWDER).add(
						//Blocks.WHITE_CONCRETE_POWDER,
						Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER,
						Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER,
						Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER,
						Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER)
				.addOptionalTag(new ResourceLocation("tinted:concrete_powder"));
		this.tag(ModTags.BURROW).add(ModBlocks.BURROW.get());
	}
}