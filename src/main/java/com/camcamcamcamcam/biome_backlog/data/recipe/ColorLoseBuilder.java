package com.camcamcamcamcam.biome_backlog.data.recipe;

import com.camcamcamcamcam.biome_backlog.recipe.BlockStateIngredient;
import com.camcamcamcamcam.biome_backlog.recipe.recipes.ColorLoseRecipe;
import com.camcamcamcamcam.biome_backlog.recipe.serializer.ColorLoseRecipeSerializer;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public class ColorLoseBuilder implements RecipeBuilder {
	private final Map<String, Criterion<?>> criteria = new LinkedHashMap();
	private final BlockStateIngredient ingredient;

	private final Block result;
	private final RecipeSerializer<?> serializer;


	public ColorLoseBuilder(BlockStateIngredient ingredient, @Nullable Block result, RecipeSerializer<?> serializer) {
		this.ingredient = ingredient;
		this.result = result;
		this.serializer = serializer;
	}


	public static ColorLoseBuilder recipe(BlockStateIngredient ingredient, Block result, ColorLoseRecipeSerializer serializer) {
		return new ColorLoseBuilder(ingredient, result, serializer);
	}

	public BlockStateIngredient getIngredient() {
		return this.ingredient;
	}


	@Override
	public ColorLoseBuilder unlockedBy(String p_176781_, Criterion<?> p_300897_) {
		this.criteria.put(p_176781_, p_300897_);
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

	public Map<String, Criterion<?>> getCriteria() {
		return criteria;
	}

	@Override
	public void save(RecipeOutput p_301137_, ResourceLocation p_126328_) {
		ColorLoseRecipe singleitemrecipe = new ColorLoseRecipe(this.result, this.ingredient);
		p_301137_.accept(p_126328_, singleitemrecipe, null);
	}

	static ResourceLocation getDefaultBlockRecipeId(Block p_176494_) {
		return BuiltInRegistries.BLOCK.getKey(p_176494_);
	}
}