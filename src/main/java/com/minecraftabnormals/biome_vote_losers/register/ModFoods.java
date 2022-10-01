package com.minecraftabnormals.biome_vote_losers.register;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
	public static final FoodProperties PRICKLY_PEAR = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).fast().build();

	public static final FoodProperties BAOBAB_FRUIT = new FoodProperties.Builder().nutrition(2).saturationMod(1F).build();
	public static final FoodProperties RAW_OSTRICH = new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).build();
	public static final FoodProperties COOKED_OSTRICH = new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).build();

	public static final FoodProperties DATE = new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).build();
	public static final FoodProperties COCONUT_CHUNK = new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).build();
	public static final FoodProperties DESICCATED_COCONUT = new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).build();

}
