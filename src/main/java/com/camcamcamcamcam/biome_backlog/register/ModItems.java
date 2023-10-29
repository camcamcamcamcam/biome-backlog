package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.world.level.entity.ModBoat;
import com.camcamcamcamcam.biome_backlog.world.level.item.ModBoatItem;
import com.camcamcamcamcam.biome_backlog.world.level.item.PalmShieldItem;
import com.camcamcamcamcam.biome_backlog.world.level.item.VinegarBottleItem;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BiomeBacklog.MODID);

	public static final RegistryObject<Item> CACTUS_PAD = ITEMS.register("cactus_pad", () -> new Item((new Item.Properties())));
	public static final RegistryObject<Item> CACTUS_SALAD = ITEMS.register("cactus_salad", () -> new BowlFoodItem((new Item.Properties()).stacksTo(1).food(ModFoods.CACTUS_SALAD)));

	public static final RegistryObject<Item> PRICKLY_PEAR = ITEMS.register("prickly_pear", () -> new Item((new Item.Properties()).food(ModFoods.PRICKLY_PEAR)));
	public static final RegistryObject<Item> PRICKLESS_PEAR = ITEMS.register("prickless_pear", () -> new Item((new Item.Properties()).food(ModFoods.PRICKLESS_PEAR)));
	public static final RegistryObject<Item> TUMBLEWEED_SEED = ITEMS.register("tumbleweed_seed", () -> new ItemNameBlockItem(ModBlocks.TUMBLEWEED.get(), new Item.Properties()));

	public static final RegistryObject<ItemNameBlockItem> BAOBAB_FRUIT = ITEMS.register("baobab_fruit", () -> new ItemNameBlockItem(ModBlocks.BAOBAB_FRUIT.get(), new Item.Properties().food(ModFoods.BAOBAB_FRUIT)));
	public static final RegistryObject<Item> RAW_OSTRICH = ITEMS.register("raw_ostrich", () -> new Item(new Item.Properties().food(ModFoods.RAW_OSTRICH)));
	public static final RegistryObject<Item> COOKED_OSTRICH = ITEMS.register("cooked_ostrich", () -> new Item(new Item.Properties().food(ModFoods.COOKED_OSTRICH)));

	public static final RegistryObject<Item> DATE = ITEMS.register("date", () -> new Item(new Item.Properties().food(ModFoods.DATE)));
	// public static final RegistryObject<CoconutItem> COCONUT = ITEMS.register("coconut", () -> new CoconutItem(new Item.Properties()));
	public static final RegistryObject<Item> COCONUT_SHELL = ITEMS.register("coconut_shell", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> COCONUT_HALF = ITEMS.register("coconut_half", () -> new Item(new Item.Properties().craftRemainder(ModItems.COCONUT_SHELL.get())));
	public static final RegistryObject<Item> COCONUT_CHUNK = ITEMS.register("coconut_chunk", () -> new Item(new Item.Properties().food(ModFoods.COCONUT_CHUNK)));
	public static final RegistryObject<Item> DESICCATED_COCONUT = ITEMS.register("desiccated_coconut", () -> new Item(new Item.Properties().food(ModFoods.DESICCATED_COCONUT)));
	public static final RegistryObject<VinegarBottleItem> VINEGAR_BOTTLE = ITEMS.register("vinegar_bottle", () -> new VinegarBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)));
	public static final RegistryObject<Item> CALCITE_POWDER_BOTTLE = ITEMS.register("calcite_powder_bottle", () -> new Item((new Item.Properties()).craftRemainder(Items.GLASS_BOTTLE)));

	public static final RegistryObject<Item> PALM_BOAT = ITEMS.register("palm_boat", () -> new ModBoatItem(false, ModBoat.BoatType.PALM, (new Item.Properties()).stacksTo(1)));
	public static final RegistryObject<Item> PALM_BOAT_CHEST = ITEMS.register("palm_boat_chest", () -> new ModBoatItem(true, ModBoat.BoatType.PALM, (new Item.Properties()).stacksTo(1)));
    public static final RegistryObject<Item> PALM_SHIELD = ITEMS.register("palm_shield", () -> new PalmShieldItem((new Item.Properties()).stacksTo(1).durability(336)));

	public static final RegistryObject<Item> SUCCULENT_CUTTING = ITEMS.register("succulent_cutting", () -> new Item(new Item.Properties()));


	public static final RegistryObject<Item> MEERKAT_SPAWN_EGG = ITEMS.register("meerkat_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.MEERKAT, 0xBC895C, 0x302B31, new Item.Properties()));
	public static final RegistryObject<Item> OSTRICH_SPAWN_EGG = ITEMS.register("ostrich_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.OSTRICH, 0xD5AA9A, 0x362A2A, new Item.Properties()));
	public static final RegistryObject<Item> VULTURE_SPAWN_EGG = ITEMS.register("vulture_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.VULTURE, 0x654225, 0xD49076, new Item.Properties()));

	public static void registerCompostableItem() {
		ComposterBlock.COMPOSTABLES.put(COCONUT_CHUNK.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(COCONUT_HALF.get().asItem(), 0.2F);
		ComposterBlock.COMPOSTABLES.put(PRICKLY_PEAR.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(PRICKLESS_PEAR.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(CACTUS_PAD.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(ModBlocks.DATE_BUNCH.get().asItem(), 0.6F);
		ComposterBlock.COMPOSTABLES.put(ModBlocks.COCONUT.get().asItem(), 0.6F);
		ComposterBlock.COMPOSTABLES.put(ModBlocks.BAOBAB_LEAVES.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(ModBlocks.PALM_LEAVES.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(ModBlocks.PALM_LEAVES_HANGING.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(ModBlocks.COCONUT_SAPLING.get().asItem(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(ModBlocks.DATE_SAPLING.get().asItem(), 0.3F);
	}
}
