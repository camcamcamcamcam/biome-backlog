package com.minecraftabnormals.biome_backlog.utils;

import com.minecraftabnormals.biome_backlog.recipe.recipes.ColorLoseRecipe;
import com.minecraftabnormals.biome_backlog.register.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class RecipeUtils {

	public static ColorLoseRecipe blockColorLoose(Level level, BlockPos pos, BlockState state) {
		if (!level.isClientSide()) {
			for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.COLOR_LOSE.get())) {
				if (recipe instanceof ColorLoseRecipe recipe2) {
					if (recipe2.matches(level, pos, state)) {
						return recipe2;
					}
				}
			}
		}
		return null;
	}
}
