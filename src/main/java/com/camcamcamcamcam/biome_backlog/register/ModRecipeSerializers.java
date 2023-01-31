package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.recipe.serializer.ColorLoseRecipeSerializer;
import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BiomeBacklog.MODID);

	public static final RegistryObject<ColorLoseRecipeSerializer> COLOR_LOSE = RECIPE_SERIALIZERS.register("color_lose", ColorLoseRecipeSerializer::new);

}
