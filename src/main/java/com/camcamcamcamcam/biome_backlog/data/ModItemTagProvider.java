package com.camcamcamcamcam.biome_backlog.data;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
	public ModItemTagProvider(PackOutput generator, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
		super(generator, lookupProvider, blockTagsProvider, BiomeBacklog.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider p_256380_) {
		this.tag(ItemTags.PLANKS).add(ModBlocks.PALM_PLANKS.get().asItem());
		this.tag(Tags.Items.EGGS).add(ModBlocks.OSTRICH_EGG.get().asItem());
	}
}