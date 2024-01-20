package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.recipe.recipes.ColorLoseRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipeTypes {
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, BiomeBacklog.MODID);

	public static Supplier<RecipeType<ColorLoseRecipe>> COLOR_LOSE = RECIPE_TYPES.register("color_lose", () -> RecipeType.simple(new ResourceLocation(BiomeBacklog.MODID, "color_lose")));

}
