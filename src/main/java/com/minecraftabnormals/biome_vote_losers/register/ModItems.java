package com.minecraftabnormals.biome_vote_losers.register;

import com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BiomeVoteLosers.MODID);

	public static final RegistryObject<Item> CACTUS_PAD = ITEMS.register("cactus_pad", () -> new Item((new Item.Properties()).tab(CreativeModeTab.TAB_MATERIALS)));
	public static final RegistryObject<Item> PRICKLY_PEAR = ITEMS.register("prickly_pear", () -> new Item((new Item.Properties()).food(ModFoods.PRICKLY_PEAR).tab(CreativeModeTab.TAB_FOOD)));

	public static final RegistryObject<Item> BAOBAB_FRUIT = ITEMS.register("baobab_fruit", () -> new Item(new Item.Properties().food(ModFoods.BAOBAB_FRUIT).tab(CreativeModeTab.TAB_FOOD)));

	public static final RegistryObject<Item> DATE = ITEMS.register("date", () -> new Item(new Item.Properties().food(ModFoods.DATE).tab(CreativeModeTab.TAB_FOOD)));
	public static final RegistryObject<Item> COCONUT = ITEMS.register("coconut", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
	public static final RegistryObject<Item> COCONUT_HALF = ITEMS.register("coconut_half", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
	public static final RegistryObject<Item> COCONUT_CHUNK = ITEMS.register("coconut_chunk", () -> new Item(new Item.Properties().food(ModFoods.COCONUT_CHUNK).tab(CreativeModeTab.TAB_FOOD)));
	public static final RegistryObject<Item> DESICCATED_COCONUT = ITEMS.register("desiccated_coconut", () -> new Item(new Item.Properties().food(ModFoods.DESICCATED_COCONUT).tab(CreativeModeTab.TAB_FOOD)));
	public static final RegistryObject<Item> COCONUT_SHELL = ITEMS.register("coconut_shell", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

	// todo palm boat and chest boat

	public static final RegistryObject<Item> SUCCULENT_CUTTING = ITEMS.register("succulent_cutting", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

}
