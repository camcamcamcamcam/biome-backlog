package com.minecraftabnormals.biome_vote_losers.register;

import com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers;
import com.minecraftabnormals.biome_vote_losers.recipe.recipes.ColorLoseRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeTypes {
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, BiomeVoteLosers.MODID);

	public static RegistryObject<RecipeType<ColorLoseRecipe>> COLOR_LOSE = RECIPE_TYPES.register("color_lose", () -> RecipeType.simple(new ResourceLocation(BiomeVoteLosers.MODID, "color_lose")));

}
