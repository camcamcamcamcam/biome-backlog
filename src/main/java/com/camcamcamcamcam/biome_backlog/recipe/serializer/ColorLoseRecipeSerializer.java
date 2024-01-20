package com.camcamcamcamcam.biome_backlog.recipe.serializer;

import com.camcamcamcamcam.biome_backlog.recipe.BlockStateIngredient;
import com.camcamcamcamcam.biome_backlog.recipe.recipes.ColorLoseRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nonnull;

public class ColorLoseRecipeSerializer implements RecipeSerializer<ColorLoseRecipe> {
	private static final Codec<ColorLoseRecipe> codec = RecordCodecBuilder.create((p_296927_) -> {
		return p_296927_.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("result").orElse(Blocks.AIR).forGetter(ColorLoseRecipe::getResult), BlockStateIngredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(ColorLoseRecipe::getIngredient)).apply(p_296927_, ColorLoseRecipe::new);
	});

	@Override
	public Codec<ColorLoseRecipe> codec() {
		return codec;
	}

	@Override
	public ColorLoseRecipe fromNetwork(FriendlyByteBuf friendlyByteBuf) {
		return new ColorLoseRecipe(BuiltInRegistries.BLOCK.byId(friendlyByteBuf.readVarInt()), BlockStateIngredient.fromNetwork(friendlyByteBuf));
	}

	@Override
	public void toNetwork(@Nonnull FriendlyByteBuf buf, ColorLoseRecipe recipe) {
		buf.writeVarInt(BuiltInRegistries.BLOCK.getId(recipe.getResult()));
		recipe.getIngredient().toNetwork(buf);

	}
}