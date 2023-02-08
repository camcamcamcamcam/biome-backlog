package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
	public static final TagKey<Block> PALM_WOOD = TagKey.create(Registries.BLOCK, new ResourceLocation(BiomeBacklog.MODID, "palm_wood"));
	public static final TagKey<Block> BURROW = TagKey.create(Registries.BLOCK, new ResourceLocation(BiomeBacklog.MODID, "burrow"));

	public static final TagKey<Block> COLORED_BEDS = TagKey.create(Registries.BLOCK, new ResourceLocation(BiomeBacklog.MODID, "colored_beds"));
	public static final TagKey<Block> COLORED_SHULKER_BOXS = TagKey.create(Registries.BLOCK, new ResourceLocation(BiomeBacklog.MODID, "colored_shulker_boxs"));
	public static final TagKey<Block> COLORED_CONCRETE = TagKey.create(Registries.BLOCK, new ResourceLocation(BiomeBacklog.MODID, "colored_concrete"));
	public static final TagKey<Block> COLORED_CONCRETE_POWDER = TagKey.create(Registries.BLOCK, new ResourceLocation(BiomeBacklog.MODID, "colored_concrete_powder"));

}
