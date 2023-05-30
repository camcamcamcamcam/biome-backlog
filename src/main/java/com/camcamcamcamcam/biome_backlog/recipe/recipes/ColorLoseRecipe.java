package com.camcamcamcamcam.biome_backlog.recipe.recipes;

import com.camcamcamcamcam.biome_backlog.recipe.BlockPropertyPair;
import com.camcamcamcamcam.biome_backlog.recipe.BlockStateIngredient;
import com.camcamcamcamcam.biome_backlog.register.ModRecipeSerializers;
import com.camcamcamcamcam.biome_backlog.register.ModRecipeTypes;
import com.camcamcamcamcam.biome_backlog.utils.BlockStateRecipeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nonnull;
import java.util.Map;

public class ColorLoseRecipe implements Recipe<Container> {
	protected final ResourceLocation id;

	protected final BlockPropertyPair result;
	protected final BlockStateIngredient ingredient;

	public ColorLoseRecipe(ResourceLocation id, BlockPropertyPair result, BlockStateIngredient ingredient) {
		this.id = id;
		this.result = result;
		this.ingredient = ingredient;
	}

	public boolean matches(Level level, BlockPos pos, BlockState object) {
		return this.getIngredient().test(object);
	}


	public BlockStateIngredient getIngredient() {
		return this.ingredient;
	}

	public BlockState getResultState(BlockState originalState) {
		if (this.getResult() != null) {
			BlockState resultState = this.getResult().block().withPropertiesOf(originalState);
			for (Map.Entry<Property<?>, Comparable<?>> propertyEntry : this.getResult().properties().entrySet()) {
				resultState = BlockStateRecipeUtil.setHelper(propertyEntry, resultState);
			}
			return resultState;
		}
		return Blocks.AIR.defaultBlockState();
	}

	public BlockPropertyPair getResult() {
		return this.result;
	}

	@Nonnull
	@Override
	public RecipeType<?> getType() {
		return ModRecipeTypes.COLOR_LOSE.get();
	}

	@Nonnull
	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipeSerializers.COLOR_LOSE.get();
	}

	@Override
	public boolean matches(@Nonnull Container container, @Nonnull Level level) {
		return false;
	}

	@Nonnull
	@Override
	public ItemStack assemble(@Nonnull Container container) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return false;
	}

	public ItemStack getToastSymbol() {
		return new ItemStack(Items.WHITE_DYE);
	}


	@Nonnull
	@Override
	public ItemStack getResultItem() {
		return ItemStack.EMPTY;
	}
}