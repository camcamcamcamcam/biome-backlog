package com.minecraftabnormals.biome_vote_losers.data;

import com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers;
import com.minecraftabnormals.biome_vote_losers.register.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagProvider extends ItemTagsProvider {
	public ModItemTagProvider(DataGenerator generator, BlockTagsProvider p_126531_, ExistingFileHelper existingFileHelper) {
		super(generator, p_126531_, BiomeVoteLosers.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		this.tag(ItemTags.PLANKS).add(ModBlocks.PALM_PLANKS.get().asItem());
		this.tag(Tags.Items.EGGS).add(ModBlocks.OSTRICH_EGG.get().asItem());
	}
}