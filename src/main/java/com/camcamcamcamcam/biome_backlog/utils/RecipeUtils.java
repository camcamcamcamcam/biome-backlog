package com.camcamcamcamcam.biome_backlog.utils;

import com.camcamcamcamcam.biome_backlog.recipe.recipes.ColorLoseRecipe;
import com.camcamcamcamcam.biome_backlog.register.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class RecipeUtils {

	public static ColorLoseRecipe blockColorLoose(Level level, BlockPos pos, BlockState state) {
		if (!level.isClientSide()) {
			for (RecipeHolder<ColorLoseRecipe> recipe : level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.COLOR_LOSE.get())) {
				if (recipe.value().matches(level, pos, state)) {
					return recipe.value();
				}
			}
		}
		return null;
	}
}
