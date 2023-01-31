package com.minecraftabnormals.biome_backlog.data;

import com.minecraftabnormals.biome_backlog.BiomeBacklog;
import com.minecraftabnormals.biome_backlog.register.ModBlocks;
import com.minecraftabnormals.biome_backlog.world.level.block.SaltBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class BlockstateGenerator extends BlockStateProvider {
	public BlockstateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, BiomeBacklog.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		this.logBlock(ModBlocks.MOUND.get());
		this.axisBlock(ModBlocks.TERMITE_MOUND.get(), texture(name(ModBlocks.TERMITE_MOUND.get())), texture(name(ModBlocks.MOUND.get())+"_top"));

		this.leavesTintBlock(ModBlocks.BAOBAB_LEAVES.get());
		this.logBlock(ModBlocks.BAOBAB_TRUNK.get());
		this.axisBlock(ModBlocks.BAOBAB_BARK.get(), texture(name(ModBlocks.BAOBAB_TRUNK.get())), texture(name(ModBlocks.BAOBAB_TRUNK.get())));
		this.crossBlock(ModBlocks.BAOBAB_FRUIT.get());

		this.simpleBlock(ModBlocks.BURROW.get());

		this.logBlock(ModBlocks.PALM_LOG.get());
		this.leavesBlock(ModBlocks.PALM_LEAVES.get(), translucentCubeAll(ModBlocks.PALM_LEAVES.get()));
		this.crossBlock(ModBlocks.PALM_LEAVES_HANGING.get());
		this.logBlock(ModBlocks.STRIPPED_PALM_LOG.get());
		this.simpleBlock(ModBlocks.PALM_WOOD.get(), texture(name(ModBlocks.PALM_LOG.get())));
		this.simpleBlock(ModBlocks.STRIPPED_PALM_WOOD.get(), texture(name(ModBlocks.STRIPPED_PALM_LOG.get())));
		this.simpleBlock(ModBlocks.PALM_PLANKS.get());
		this.stairs(ModBlocks.PALM_STAIRS.get(), ModBlocks.PALM_PLANKS.get());
		this.slab(ModBlocks.PALM_SLAB.get(), ModBlocks.PALM_PLANKS.get());

		this.doorBlockWithRenderType(ModBlocks.PALM_DOOR.get(), texture(name(ModBlocks.PALM_DOOR.get()) + "_bottom"), texture(name(ModBlocks.PALM_DOOR.get()) + "_top"), "cutout");
		this.trapdoorBlockWithRenderType(ModBlocks.PALM_TRAPDOOR.get(), texture(name(ModBlocks.PALM_TRAPDOOR.get())), true, "cutout");

		this.fenceBlock(ModBlocks.PALM_FENCE.get(), texture(name(ModBlocks.PALM_PLANKS.get())));
		this.fenceGateBlock(ModBlocks.PALM_FENCE_GATE.get(), texture(name(ModBlocks.PALM_PLANKS.get())));
		this.buttonBlock(ModBlocks.PALM_BUTTON.get(), texture(name(ModBlocks.PALM_PLANKS.get())));
		this.pressurePlateBlock(ModBlocks.PALM_PRESSURE_PLATE.get(), texture(name(ModBlocks.PALM_PLANKS.get())));

		this.saltBlock(ModBlocks.SALT_BLOCK.get());
		this.saltBlock(ModBlocks.SALT_BRICKS.get());
		this.saltBlock(ModBlocks.SALT_TILES.get());
		this.saltBlock(ModBlocks.CHISELED_SALT_BLOCK.get());
		this.litStairsSaltBlock(ModBlocks.SALT_STAIRS.get(), ModBlocks.SALT_BLOCK.get());
		this.litStairsSaltBlock(ModBlocks.SALT_BRICK_STAIRS.get(), ModBlocks.SALT_BRICKS.get());
		this.litStairsSaltBlock(ModBlocks.SALT_TILE_STAIRS.get(), ModBlocks.SALT_TILES.get());
		this.litSlabSaltBlock(ModBlocks.SALT_SLAB.get(), ModBlocks.SALT_BLOCK.get());
		this.litSlabSaltBlock(ModBlocks.SALT_BRICK_SLAB.get(), ModBlocks.SALT_BRICKS.get());
		this.litSlabSaltBlock(ModBlocks.SALT_TILE_SLAB.get(), ModBlocks.SALT_TILES.get());
	}

	public ModelFile cubeAll(Block block, ResourceLocation resourceLocation) {
		return models().cubeAll(name(block), resourceLocation);
	}

	public void simpleBlock(Block block, ResourceLocation resourceLocation) {
		simpleBlock(block, cubeAll(block, resourceLocation));
	}

	public void saltBlock(Block block) {
		ModelFile unlit = models().cubeAll(name(block), texture(name(block)));
		ModelFile lit = models().cubeAll("lit_" + name(block), texture("lit_" + name(block)));
		getVariantBuilder(block).forAllStates(state -> {
			boolean isLit = state.getValue(SaltBlock.LIT);
			return ConfiguredModel.builder().modelFile(isLit ? lit : unlit).build();
		});
	}

	public void leavesTintBlock(Block block) {
		ModelFile tint = models().singleTexture(name(block), mcLoc("minecraft:block/leaves"), "all", texture(name(block))).renderType("minecraft:cutout_mipped");
		leavesBlock(block, tint);
	}

	private void leavesBlock(Block block, ModelFile model) {
		getVariantBuilder(block).forAllStates(state ->
				ConfiguredModel.builder()
						.modelFile(model)
						.build());
	}

	public void translucentBlock(Block block) {
		simpleBlock(block, translucentCubeAll(block));
	}

	private ModelFile translucentCubeAll(Block block) {
		return models().cubeAll(name(block), blockTexture(block)).renderType("minecraft:translucent");
	}

	public void stairs(StairBlock block, Block fullBlock) {
		stairsBlock(block, texture(name(fullBlock)));
	}

	public void slab(SlabBlock block, Block fullBlock) {
		slabBlock(block, texture(name(fullBlock)), texture(name(fullBlock)));
	}

	public void litSlabSaltBlock(SlabBlock block, Block fullBlock) {
		ModelFile unlit_slab = models().slab(name(block), texture(name(fullBlock)), texture(name(fullBlock)), texture(name(fullBlock)));
		ModelFile lit_slab = models().slab("lit_" + name(block), texture("lit_" + name(fullBlock)), texture("lit_" + name(fullBlock)), texture("lit_" + name(fullBlock)));
		ModelFile unlit_top = models().slabTop(name(block) + "_top", texture(name(fullBlock)), texture(name(fullBlock)), texture(name(fullBlock)));
		ModelFile lit_top = models().slabTop("lit_" + name(block) + "_top", texture("lit_" + name(fullBlock)), texture("lit_" + name(fullBlock)), texture("lit_" + name(fullBlock)));
		ModelFile unlit = models().cubeAll(name(block), texture(name(fullBlock)));
		ModelFile lit = models().cubeAll("lit_" + name(block), texture("lit_" + name(fullBlock)));


		getVariantBuilder(block).forAllStates(state -> {
			boolean isLit = state.getValue(SaltBlock.LIT);
			SlabType type = state.getValue(SlabBlock.TYPE);
			if (type == SlabType.BOTTOM) {
				return ConfiguredModel.builder().modelFile(isLit ? lit_slab : unlit_slab).build();
			} else if (type == SlabType.TOP) {
				return ConfiguredModel.builder().modelFile(isLit ? lit_top : unlit_top).build();
			} else {
				return ConfiguredModel.builder().modelFile(isLit ? lit : unlit).build();
			}
		});
	}

	public void litStairsSaltBlock(StairBlock block, Block fullBlock) {
		ModelFile stairs = models().stairs(name(block), texture(name(fullBlock)), texture(name(fullBlock)), texture(name(fullBlock)));
		ModelFile stairsLit = models().stairs("lit_" + name(block), texture("lit_" + name(fullBlock)), texture("lit_" + name(fullBlock)), texture("lit_" + name(fullBlock)));
		ModelFile stairsInner = models().stairsInner(name(block) + "_inner", texture(name(fullBlock)), texture(name(fullBlock)), texture(name(fullBlock)));
		ModelFile stairsInnerLit = models().stairsInner("lit_" + name(block) + "_inner", texture("lit_" + name(fullBlock)), texture("lit_" + name(fullBlock)), texture("lit_" + name(fullBlock)));
		ModelFile stairsOuter = models().stairsOuter(name(block) + "_outer", texture(name(fullBlock)), texture(name(fullBlock)), texture(name(fullBlock)));
		ModelFile stairsOuterLit = models().stairsOuter("lit_" + name(block) + "_outer", texture("lit_" + name(fullBlock)), texture("lit_" + name(fullBlock)), texture("lit_" + name(fullBlock)));

		getVariantBuilder(block)
				.forAllStatesExcept(state -> {
					Direction facing = state.getValue(StairBlock.FACING);
					Half half = state.getValue(StairBlock.HALF);
					StairsShape shape = state.getValue(StairBlock.SHAPE);
					int yRot = (int) facing.getClockWise().toYRot(); // Stairs model is rotated 90 degrees clockwise for some reason
					if (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT) {
						yRot += 270; // Left facing stairs are rotated 90 degrees clockwise
					}
					if (shape != StairsShape.STRAIGHT && half == Half.TOP) {
						yRot += 90; // Top stairs are rotated 90 degrees clockwise
					}
					yRot %= 360;
					boolean uvlock = yRot != 0 || half == Half.TOP; // Don't set uvlock for states that have no rotation
					boolean isLit = state.getValue(SaltBlock.LIT);
					return ConfiguredModel.builder()
							.modelFile(shape == StairsShape.STRAIGHT ? isLit ? stairsLit : stairs : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? isLit ? stairsInnerLit : stairsInner : isLit ? stairsOuterLit : stairsOuter)
							.rotationX(half == Half.BOTTOM ? 0 : 180)
							.rotationY(yRot)
							.uvLock(uvlock)
							.build();
				}, StairBlock.WATERLOGGED);
	}

	public void crossBlock(Block block) {

		crossBlock(block, models().cross(name(block), texture(name(block))).renderType("minecraft:cutout"));
	}

	public void crossTintBlock(Block block) {
		ModelFile tint = models().singleTexture(name(block), mcLoc("block/tinted_cross"), "cross", texture(name(block))).renderType("minecraft:cutout");


		crossBlock(block, tint);
	}

	private void crossBlock(Block block, ModelFile model) {
		getVariantBuilder(block).forAllStates(state ->
				ConfiguredModel.builder()
						.modelFile(model)
						.build());
	}


	private ResourceLocation key(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block);
	}

	protected ResourceLocation texture(String name) {
		return modLoc("block/" + name);
	}

	protected String name(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block).getPath();
	}

	@Nonnull
	@Override
	public String getName() {
		return "BiomeVoteLosers blockstates and block models";
	}
}