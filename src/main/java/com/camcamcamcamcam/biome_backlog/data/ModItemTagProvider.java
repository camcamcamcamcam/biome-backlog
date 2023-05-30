package com.camcamcamcamcam.biome_backlog.data;

import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagProvider extends ItemTagsProvider {
	public ModItemTagProvider(DataGenerator generator, BlockTagsProvider p_126531_, ExistingFileHelper existingFileHelper) {
		super(generator, p_126531_, BiomeBacklog.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		this.tag(ItemTags.PLANKS).add(ModBlocks.PALM_PLANKS.get().asItem());
		this.tag(Tags.Items.EGGS).add(ModBlocks.OSTRICH_EGG.get().asItem());
	}
}