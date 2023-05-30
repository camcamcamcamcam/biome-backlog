package com.camcamcamcamcam.biome_backlog.world.gen.feature;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import com.camcamcamcamcam.biome_backlog.world.gen.decorator.CoconutDecorator;
import com.camcamcamcamcam.biome_backlog.world.gen.decorator.DateDecorator;
import com.camcamcamcamcam.biome_backlog.world.gen.foliage.PalmTreeFoliagePlacer;
import com.camcamcamcamcam.biome_backlog.world.gen.trunk.PalmTrunkPlacer;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModConfiguredFeatures {
	public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, BiomeBacklog.MODID);

	public static final RegistryObject<ConfiguredFeature<?, ?>> PATCH_PEAR_CACTUS = CONFIGURED_FEATURES.register("patch_pear_cactus", ()-> new ConfiguredFeature<>(Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(ModBlocks.PEAR_CACTUS.get()), 32)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> PATCH_TUMBLEWEED = CONFIGURED_FEATURES.register("patch_tumbleweed", ()-> new ConfiguredFeature<>(Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(ModBlocks.TUMBLEWEED.get()), 32)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> PALM_DATE = CONFIGURED_FEATURES.register("palm_date", ()-> new ConfiguredFeature<>(Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.PALM_LOG.get()), new PalmTrunkPlacer(5, 2, 2), BlockStateProvider.simple(ModBlocks.PALM_LEAVES.get()), new PalmTreeFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2), new TwoLayersFeatureSize(1, 0, 2))).decorators(List.of(new AttachedToLeavesDecorator(0.75F, 1, 0, BlockStateProvider.simple(ModBlocks.PALM_LEAVES_HANGING.get()), 2, List.of(Direction.DOWN)), new DateDecorator(0.9F))).dirt(BlockStateProvider.simple(Blocks.SAND)).ignoreVines().build()));
	public static final RegistryObject<ConfiguredFeature<?, ?>> PALM_COCONUTS = CONFIGURED_FEATURES.register("palm_coconuts", ()-> new ConfiguredFeature<>(Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.PALM_LOG.get()), new PalmTrunkPlacer(5, 2, 2), BlockStateProvider.simple(ModBlocks.PALM_LEAVES.get()), new PalmTreeFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2), new TwoLayersFeatureSize(1, 0, 2))).decorators(List.of(new AttachedToLeavesDecorator(0.75F, 1, 0, BlockStateProvider.simple(ModBlocks.PALM_LEAVES_HANGING.get()), 2, List.of(Direction.DOWN)), new CoconutDecorator(0.9F))).dirt(BlockStateProvider.simple(Blocks.SAND)).ignoreVines().build()));

	private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
		return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(p_195203_)));
	}

}