package com.camcamcamcamcam.biome_backlog.recipe.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.camcamcamcamcam.biome_backlog.recipe.BlockPropertyPair;
import com.camcamcamcamcam.biome_backlog.recipe.BlockStateIngredient;
import com.camcamcamcamcam.biome_backlog.recipe.recipes.ColorLoseRecipe;
import com.camcamcamcamcam.biome_backlog.utils.BlockStateRecipeUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ColorLoseRecipeSerializer implements RecipeSerializer<ColorLoseRecipe> {
	public ColorLoseRecipeSerializer() {

	}

	@Nonnull
	@Override
	public ColorLoseRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject serializedRecipe) {

		if (!serializedRecipe.has("result"))
			throw new JsonSyntaxException("Missing result, expected to find a string or object");
		BlockPropertyPair result;
		if (serializedRecipe.get("result").isJsonObject()) {
			JsonObject resultObject = serializedRecipe.getAsJsonObject("result");
			result = BlockStateRecipeUtil.pairFromJson(resultObject);
		} else {
			throw new JsonSyntaxException("Expected result to be object");
		}
		if (!serializedRecipe.has("ingredient"))
			throw new JsonSyntaxException("Missing ingredient, expected to find an object or array");
		JsonElement jsonElement = GsonHelper.isArrayNode(serializedRecipe, "ingredient") ? GsonHelper.getAsJsonArray(serializedRecipe, "ingredient") : GsonHelper.getAsJsonObject(serializedRecipe, "ingredient");
		BlockStateIngredient ingredient = BlockStateIngredient.fromJson(jsonElement);

		return new ColorLoseRecipe(recipeId, result, ingredient);
	}

	@Nullable
	@Override
	public ColorLoseRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buf) {
		BlockPropertyPair result = BlockStateRecipeUtil.readPair(buf);
		BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buf);

		return new ColorLoseRecipe(recipeId, result, ingredient);
	}

	@Override
	public void toNetwork(@Nonnull FriendlyByteBuf buf, ColorLoseRecipe recipe) {
		BlockStateRecipeUtil.writePair(buf, recipe.getResult());
		recipe.getIngredient().toNetwork(buf);

	}
}