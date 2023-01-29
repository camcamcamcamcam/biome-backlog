package com.minecraftabnormals.biome_backlog.register;

import com.minecraftabnormals.biome_backlog.BiomeBacklog;
import com.minecraftabnormals.biome_backlog.recipe.serializer.ColorLoseRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BiomeBacklog.MODID);

	public static final RegistryObject<ColorLoseRecipeSerializer> COLOR_LOSE = RECIPE_SERIALIZERS.register("color_lose", ColorLoseRecipeSerializer::new);

}
