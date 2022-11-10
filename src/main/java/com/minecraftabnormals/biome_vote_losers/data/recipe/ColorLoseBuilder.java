package com.minecraftabnormals.biome_vote_losers.data.recipe;

import com.google.gson.JsonObject;
import com.minecraftabnormals.biome_vote_losers.recipe.BlockPropertyPair;
import com.minecraftabnormals.biome_vote_losers.recipe.BlockStateIngredient;
import com.minecraftabnormals.biome_vote_losers.recipe.serializer.ColorLoseRecipeSerializer;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ColorLoseBuilder implements RecipeBuilder {
	private final BlockStateIngredient ingredient;

	private final BlockPropertyPair result;
	private final RecipeSerializer<?> serializer;

	public ColorLoseBuilder(BlockStateIngredient ingredient, @Nullable BlockPropertyPair result, RecipeSerializer<?> serializer) {
		this.ingredient = ingredient;
		this.result = result;
		this.serializer = serializer;
	}

	public static ColorLoseBuilder recipe(BlockStateIngredient ingredient, BlockPropertyPair result, ColorLoseRecipeSerializer serializer) {
		return new ColorLoseBuilder(ingredient, result, serializer);
	}

	public BlockStateIngredient getIngredient() {
		return this.ingredient;
	}

	public BlockPropertyPair getResultBlock() {
		return this.result;
	}

	public RecipeSerializer<?> getSerializer() {
		return this.serializer;
	}

	@Nonnull
	@Override
	public RecipeBuilder unlockedBy(@Nonnull String criterionName, @Nonnull CriterionTriggerInstance criterionTriggerInstance) {
		return this;
	}

	@Nonnull
	@Override
	public RecipeBuilder group(@Nullable String groupName) {
		return this;
	}

	@Nonnull
	@Override
	public Item getResult() {
		return Items.AIR;
	}

	public void save(Consumer<FinishedRecipe> p_176499_) {
		this.save(p_176499_, getDefaultBlockRecipeId(this.getResultBlock().block()));
	}

	static ResourceLocation getDefaultBlockRecipeId(Block p_176494_) {
		return Registry.BLOCK.getKey(p_176494_);
	}

	@Override
	public void save(@Nonnull Consumer<FinishedRecipe> finishedRecipeConsumer, @Nonnull ResourceLocation recipeId) {
		finishedRecipeConsumer.accept(new Result(recipeId, this.result, this.ingredient, this.serializer));
	}

	public static class Result implements FinishedRecipe {
		private final ResourceLocation id;

		private final BlockPropertyPair result;
		private final BlockStateIngredient ingredient;
		private final RecipeSerializer<?> serializer;

		public Result(ResourceLocation id, BlockPropertyPair result, BlockStateIngredient ingredient, RecipeSerializer<?> serializer) {
			this.id = id;
			this.result = result;
			this.ingredient = ingredient;
			this.serializer = serializer;
		}

		@Override
		public void serializeRecipeData(@Nonnull JsonObject json) {
			json.add("result", BlockStateIngredient.of(this.result.block()).toJson());
			json.add("ingredient", this.ingredient.toJson());

		}

		@Nonnull
		@Override
		public RecipeSerializer<?> getType() {
			return this.serializer;
		}

		@Nonnull
		@Override
		public ResourceLocation getId() {
			return this.id;
		}

		@Nullable
		@Override
		public JsonObject serializeAdvancement() {
			return null;
		}

		@Nullable
		@Override
		public ResourceLocation getAdvancementId() {
			return null;
		}
	}
}