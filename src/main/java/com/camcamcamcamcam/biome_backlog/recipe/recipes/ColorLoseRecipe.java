package com.camcamcamcamcam.biome_backlog.recipe.recipes;

import com.camcamcamcamcam.biome_backlog.recipe.BlockStateIngredient;
import com.camcamcamcamcam.biome_backlog.register.ModRecipeSerializers;
import com.camcamcamcamcam.biome_backlog.register.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class ColorLoseRecipe implements Recipe<Container> {

	protected final Block result;
	protected final BlockStateIngredient ingredient;

	public ColorLoseRecipe(Block result, BlockStateIngredient ingredient) {
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
			BlockState resultState = this.getResult().withPropertiesOf(originalState);
			return resultState;
		}
		return Blocks.AIR.defaultBlockState();
	}

	public Block getResult() {
		return this.result;
	}

	@Nonnull
	@Override
	public RecipeType<?> getType() {
		return ModRecipeTypes.COLOR_LOSE.get();
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
	public ItemStack assemble(@Nonnull Container container, RegistryAccess p_267165_) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return false;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess p_267052_) {
		return ItemStack.EMPTY;
	}

	public ItemStack getToastSymbol() {
		return new ItemStack(Items.WHITE_DYE);
	}

	@Override
	public boolean isSpecial() {
		return true;
	}
}