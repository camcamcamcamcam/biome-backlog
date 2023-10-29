package com.camcamcamcamcam.biome_backlog.data;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput generator, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(generator, lookupProvider, blockTagsProvider, BiomeBacklog.MODID, existingFileHelper);
    }

	@Override
	protected void addTags(HolderLookup.Provider p_256380_) {
		this.tag(ItemTags.LOGS_THAT_BURN).add(ModBlocks.STRIPPED_PALM_LOG.get().asItem(), ModBlocks.STRIPPED_PALM_WOOD.get().asItem(), ModBlocks.PALM_LOG.get().asItem(), ModBlocks.BAOBAB_TRUNK.get().asItem(), ModBlocks.BAOBAB_BARK.get().asItem());
		this.tag(ItemTags.PLANKS).add(ModBlocks.PALM_PLANKS.get().asItem());
		this.tag(Tags.Items.EGGS).add(ModBlocks.OSTRICH_EGG.get().asItem());
	}
}