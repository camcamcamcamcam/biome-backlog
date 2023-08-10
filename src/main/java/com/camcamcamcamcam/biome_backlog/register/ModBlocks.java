package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.world.gen.grower.PalmTreeCoconutGrower;
import com.camcamcamcamcam.biome_backlog.world.gen.grower.PalmTreeDateGrower;
import com.camcamcamcamcam.biome_backlog.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BiomeBacklog.MODID);

	public static final WoodType PALM_TYPE = WoodType.register(new WoodType(BiomeBacklog.prefix("palm").toString(), BlockSetType.OAK));

	// BADLANDS

	public static final RegistryObject<PearCactusBlock> PEAR_CACTUS = register("pear_cactus", () -> new PearCactusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).noOcclusion().strength(0.3F).randomTicks().instabreak().sound(SoundType.WOOL)));
	public static final RegistryObject<PearCactusBlock> STRIPPED_PEAR_CACTUS = register("stripped_pear_cactus", () -> new PearCactusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).noOcclusion().strength(0.3F).randomTicks().instabreak().sound(SoundType.WOOL)));

	public static final RegistryObject<TumbleWeedBlock> TUMBLEWEED = noItemRegister("tumbleweed", () -> new TumbleWeedBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().randomTicks().instabreak().sound(SoundType.SWEET_BERRY_BUSH)));

	// SAVANNA

	public static final RegistryObject<MoundBlock> MOUND = register("mound", () -> new MoundBlock(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).strength(1.0F).sound(SoundType.GRAVEL)));
	public static final RegistryObject<MoundBlock> TERMITE_MOUND = register("termite_mound", () -> new MoundBlock(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).strength(1.0F).randomTicks().sound(SoundType.GRAVEL)));

	public static final RegistryObject<OstrichEggBlock> OSTRICH_EGG = register("ostrich_egg", () -> new OstrichEggBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(1.0F).sound(SoundType.METAL)));

	public static final RegistryObject<LeavesBlock> BAOBAB_LEAVES = register("baobab_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).strength(0.2F).isValidSpawn(ModBlocks::ocelotOrParrot).sound(SoundType.GRASS).noOcclusion()));
	public static final RegistryObject<RotatedPillarBlock> BAOBAB_TRUNK = register("baobab_trunk", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

	public static final RegistryObject<RotatedPillarBlock> BAOBAB_BARK = register("baobab_bark", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

	public static final RegistryObject<HangingLeavesBlock> BAOBAB_FLOWER = register("baobab_flower", () -> new BaobabFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).noCollission().instabreak().sound(SoundType.GRASS)));

	public static final RegistryObject<HangingLeavesBlock> BAOBAB_FRUIT = noItemRegister("baobab_fruit", () -> new HangingLeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).sound(SoundType.SWEET_BERRY_BUSH)));

	// DESERT

	public static final RegistryObject<Block> BURROW = register("burrow", () -> new BurrowBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.5F).sound(SoundType.SAND)));

	public static final BlockBehaviour.Properties PALM = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0F, 9.0F).sound(SoundType.WOOD);

	public static final RegistryObject<DateBlock> DATE_BUNCH = register("date_bunch", () -> new DateBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)));
	public static final RegistryObject<CoconutBlock> COCONUT = register("coconut", () -> new CoconutBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F, 1.0F)));

	public static final RegistryObject<RotatedPillarBlock> PALM_LOG = register("palm_log", () -> new RotatedPillarBlock(PALM));
	public static final RegistryObject<LeavesBlock> PALM_LEAVES = register("palm_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.2F).isValidSpawn(ModBlocks::ocelotOrParrot).sound(SoundType.GRASS).noOcclusion()));
	public static final RegistryObject<HangingLeavesBlock> PALM_LEAVES_HANGING = register("palm_leaves_hanging", () -> new HangingLeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).noCollission().strength(0.2F).sound(SoundType.GRASS)));
	public static final RegistryObject<Block> THATCH_BLOCK = register("thatch_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.25F, 0.5F).sound(SoundType.GRASS)));
	public static final RegistryObject<StairBlock> THATCH_STAIRS = register("thatch_stairs", () -> new StairBlock(() -> THATCH_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.25F, 0.5F).sound(SoundType.GRASS)));

	public static final RegistryObject<RotatedPillarBlock> STRIPPED_PALM_LOG = register("stripped_palm_log", () -> new RotatedPillarBlock(PALM));
	public static final RegistryObject<RotatedPillarBlock> PALM_WOOD = register("palm_wood", () -> new RotatedPillarBlock(PALM));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_PALM_WOOD = register("stripped_palm_wood", () -> new RotatedPillarBlock(PALM));
	public static final RegistryObject<Block> PALM_PLANKS = register("palm_planks", () -> new Block(PALM));
	public static final RegistryObject<Block> COCONUT_SAPLING = register("coconut_sapling", () -> new CoconutSaplingBlock(new PalmTreeCoconutGrower(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).sound(SoundType.GRASS).noCollission()));
	public static final RegistryObject<Block> DATE_SAPLING = register("date_sapling", () -> new PalmSaplingBlock(new PalmTreeDateGrower(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).sound(SoundType.GRASS).noCollission()));
	public static final RegistryObject<DoorBlock> PALM_DOOR = register("palm_door", () -> new DoorBlock(PALM.noOcclusion(), BlockSetType.OAK));
	public static final RegistryObject<TrapDoorBlock> PALM_TRAPDOOR = register("palm_trapdoor", () -> new TrapDoorBlock(PALM.noOcclusion(), BlockSetType.OAK));
	public static final RegistryObject<ModStandingSignBlock> PALM_SIGN = register("palm_sign", () -> new ModStandingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().sound(SoundType.WOOD), WoodType.OAK));
	public static final RegistryObject<ModWallSignBlock> PALM_WALL_SIGN = noItemRegister("palm_wall_sign", () -> new ModWallSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().sound(SoundType.WOOD), WoodType.OAK));
	public static final RegistryObject<FenceBlock> PALM_FENCE = register("palm_fence", () -> new FenceBlock(PALM));
	public static final RegistryObject<FenceGateBlock> PALM_FENCE_GATE = register("palm_fence_gate", () -> new FenceGateBlock(PALM, WoodType.OAK));
	public static final RegistryObject<StairBlock> PALM_STAIRS = register("palm_stairs", () -> new StairBlock(() -> PALM_PLANKS.get().defaultBlockState(), PALM));
	public static final RegistryObject<SlabBlock> PALM_SLAB = register("palm_slab", () -> new SlabBlock(PALM));
	public static final RegistryObject<ButtonBlock> PALM_BUTTON = register("palm_button", () -> new ButtonBlock(PALM, BlockSetType.OAK, 30, true));
	public static final RegistryObject<PressurePlateBlock> PALM_PRESSURE_PLATE = register("palm_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, PALM, BlockSetType.OAK));

	public static final BlockBehaviour.Properties SALT = BlockBehaviour.Properties.of().mapColor(DyeColor.ORANGE).requiresCorrectToolForDrops().strength(1.0F, 4.0F).sound(SoundType.GLASS).noOcclusion();

	public static final RegistryObject<SaltBlock> SALT_BLOCK = register("salt_block", () -> new SaltBlock(SALT));
	public static final RegistryObject<SaltBlock> SALT_BRICKS = register("salt_bricks", () -> new SaltBlock(SALT));
	public static final RegistryObject<SaltBlock> SALT_TILES = register("salt_tiles", () -> new SaltBlock(SALT));
	public static final RegistryObject<SaltBlock> CHISELED_SALT_BLOCK = register("chiseled_salt_block", () -> new SaltBlock(SALT));
	public static final RegistryObject<SaltSlabBlock> SALT_SLAB = register("salt_slab", () -> new SaltSlabBlock(SALT));
	public static final RegistryObject<SaltSlabBlock> SALT_BRICK_SLAB = register("salt_brick_slab", () -> new SaltSlabBlock(SALT));
	public static final RegistryObject<SaltSlabBlock> SALT_TILE_SLAB = register("salt_tile_slab", () -> new SaltSlabBlock(SALT));
	public static final RegistryObject<SaltStairBlock> SALT_STAIRS = register("salt_stairs", () -> new SaltStairBlock(SALT));
	public static final RegistryObject<SaltStairBlock> SALT_BRICK_STAIRS = register("salt_brick_stairs", () -> new SaltStairBlock(SALT));
	public static final RegistryObject<SaltStairBlock> SALT_TILE_STAIRS = register("salt_tile_stairs", () -> new SaltStairBlock(SALT));

	public static final RegistryObject<SaltLampBlock> SALT_LAMP = register("salt_lamp", () -> new SaltLampBlock(SALT.lightLevel((state) -> 8)));
	public static final RegistryObject<SaltTrailBlock> SALT_TRAIL = register("salt", () -> new SaltTrailBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).noCollission().instabreak()));

	public static final RegistryObject<SucculentBlock> SUCCULENT = register("succulent", () -> new SucculentBlock(BlockBehaviour.Properties.of().sound(SoundType.GRASS).instabreak().noOcclusion().noCollission()));
	public static final RegistryObject<FlowerPotBlock> POTTED_SUCCULENT = noItemRegister("potted_succulent", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SUCCULENT, BlockBehaviour.Properties.of().instabreak().noOcclusion()));

	// public static final RegistryObject<Block> WORMHOLE = register("wormhole", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));

	private static Boolean never(BlockState p_50779_, BlockGetter p_50780_, BlockPos p_50781_, EntityType<?> p_50782_) {
		return (boolean) false;
	}

	private static Boolean always(BlockState p_50810_, BlockGetter p_50811_, BlockPos p_50812_, EntityType<?> p_50813_) {
		return (boolean) true;
	}

	private static Boolean ocelotOrParrot(BlockState p_50822_, BlockGetter p_50823_, BlockPos p_50824_, EntityType<?> p_50825_) {
		return (boolean) (p_50825_ == EntityType.OCELOT || p_50825_ == EntityType.PARROT);
	}

	private static <T extends Block> RegistryObject<T> baseRegister(String name, Supplier<? extends T> block, Function<RegistryObject<T>, Supplier<? extends Item>> item) {
		RegistryObject<T> register = BLOCKS.register(name, block);
		ModItems.ITEMS.register(name, item.apply(register));
		return register;
	}

	private static <T extends Block> RegistryObject<T> noItemRegister(String name, Supplier<? extends T> block) {
		RegistryObject<T> register = BLOCKS.register(name, block);
		return register;
	}

	private static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends Block> block) {
		return (RegistryObject<B>) baseRegister(name, block, (object) -> ModBlocks.registerBlockItem(object));
	}

	private static <T extends Block> Supplier<BlockItem> registerBlockItem(final RegistryObject<T> block) {
		return () -> {
			if (Objects.requireNonNull(block.get()) == PALM_SIGN.get()) {

				return new SignItem(new Item.Properties(), block.get(), PALM_WALL_SIGN.get());

			} else {
				return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties());
			}
		};
	}

	public static void flamableInit() {
		FireBlock fireblock = (FireBlock) Blocks.FIRE;
		fireblock.setFlammable(PALM_PLANKS.get(), 5, 20);
		fireblock.setFlammable(PALM_LOG.get(), 5, 20);
		fireblock.setFlammable(PALM_WOOD.get(), 5, 20);
		fireblock.setFlammable(PALM_SLAB.get(), 5, 20);
		fireblock.setFlammable(PALM_STAIRS.get(), 5, 20);
		fireblock.setFlammable(PALM_FENCE.get(), 5, 20);
		fireblock.setFlammable(PALM_FENCE_GATE.get(), 5, 20);
		fireblock.setFlammable(PALM_BUTTON.get(), 5, 20);
		fireblock.setFlammable(PALM_PRESSURE_PLATE.get(), 5, 20);
		fireblock.setFlammable(PALM_DOOR.get(), 5, 20);
		fireblock.setFlammable(PALM_TRAPDOOR.get(), 5, 20);

		fireblock.setFlammable(BAOBAB_BARK.get(), 5, 20);
		fireblock.setFlammable(BAOBAB_TRUNK.get(), 5, 20);

		fireblock.setFlammable(BAOBAB_LEAVES.get(), 30, 60);
		fireblock.setFlammable(PALM_LEAVES.get(), 30, 60);
		fireblock.setFlammable(PALM_LEAVES_HANGING.get(), 40, 80);
	}
}
