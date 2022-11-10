package com.minecraftabnormals.biome_vote_losers.register;

import com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
	public static final TagKey<Block> PALM_WOOD = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(BiomeVoteLosers.MODID, "palm_wood"));

	public static final TagKey<Block> COLORED_BEDS = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(BiomeVoteLosers.MODID, "colored_beds"));
	public static final TagKey<Block> COLORED_SHULKER_BOXS = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(BiomeVoteLosers.MODID, "colored_shulker_boxs"));
	public static final TagKey<Block> COLORED_CONCRETE = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(BiomeVoteLosers.MODID, "colored_concrete"));
	public static final TagKey<Block> COLORED_CONCRETE_POWDER = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(BiomeVoteLosers.MODID, "colored_concrete_powder"));

}
