package com.minecraftabnormals.biome_vote_losers.register;

import com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers;
import com.minecraftabnormals.biome_vote_losers.world.level.block.PricklyCactusBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BiomeVoteLosers.MODID);

	public static final RegistryObject<PricklyCactusBlock> PRICKLY_CACTUS = register("prickly_cactus", () -> new PricklyCactusBlock(BlockBehaviour.Properties.of(Material.CACTUS).noOcclusion().randomTicks().instabreak().sound(SoundType.WOOL)));

	public static final RegistryObject<Block> MOUND = register("mound", () -> new Block(BlockBehaviour.Properties.of(Material.CLAY)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> TERMITE_MOUND = register("termite_mound", () -> new Block(BlockBehaviour.Properties.of(Material.CLAY)), CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> BAOBAB_LEAVES = register("baobab_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).noOcclusion()), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<RotatedPillarBlock> BAOBAB_TRUNK = register("baobab_trunk", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);

	public static final RegistryObject<RotatedPillarBlock> PALM_LOG = register("palm_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD)));
	public static final RegistryObject<LeavesBlock> PALM_LEAVES = register("palm_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).noOcclusion()));
	// todo public static final RegistryObject<Block> PALM_LEAVES_HANGING = register("hanging_palm_leaves", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD)));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_PALM_LOG = register("stripped_palm_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD)));
	public static final RegistryObject<RotatedPillarBlock> PALM_WOOD = register("palm_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD)));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_PALM_WOOD = register("stripped_palm_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD)));
	public static final RegistryObject<Block> PALM_PLANKS = register("palm_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD)));
	public static final RegistryObject<Block> PALM_SAPLING = register("palm_sapling", () -> new SaplingBlock(new OakTreeGrower(), BlockBehaviour.Properties.of(Material.WOOD)));
	public static final RegistryObject<DoorBlock> PALM_DOOR = register("palm_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD)));
	public static final RegistryObject<TrapDoorBlock> PALM_TRAPDOOR = register("palm_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD)));
	// todo public static final RegistryObject<StandingSignBlock> PALM_SIGN = register("palm_sign", () -> new StandingSignBlock(BlockBehaviour.Properties.of(Material.WOOD), WoodType.OAK));
	// todo public static final RegistryObject<WallSignBlock> PALM_WALL_SIGN = register("palm_wall_sign", () -> new WallSignBlock(BlockBehaviour.Properties.of(Material.WOOD), WoodType.OAK));
	public static final RegistryObject<FenceBlock> PALM_FENCE = register("palm_fence", () -> new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD)));
	public static final RegistryObject<FenceGateBlock> PALM_FENCE_GATE = register("palm_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD)));
	public static final RegistryObject<StairBlock> PALM_STAIRS = register("palm_stairs", () -> new StairBlock(PALM_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD)));
	public static final RegistryObject<SlabBlock> PALM_SLAB = register("palm_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD)));
	public static final RegistryObject<WoodButtonBlock> PALM_BUTTON = register("palm_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.of(Material.WOOD)));
	public static final RegistryObject<PressurePlateBlock> PALM_PRESSURE_PLATE = register("palm_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD)));

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
		return (RegistryObject<B>) baseRegister(name, block, (object) -> ModBlocks.registerBlockItem(object, CreativeModeTab.TAB_BUILDING_BLOCKS));
	}

	private static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends Block> block, CreativeModeTab tab) {
		return (RegistryObject<B>) baseRegister(name, block, (object) -> ModBlocks.registerBlockItem(object, tab));
	}

	private static <T extends Block> Supplier<BlockItem> registerBlockItem(final RegistryObject<T> block, CreativeModeTab tab) {
		return () -> {
			return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties().tab(tab));
		};
	}
}
