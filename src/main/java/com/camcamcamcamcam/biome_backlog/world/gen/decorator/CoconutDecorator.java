package com.camcamcamcamcam.biome_backlog.world.gen.decorator;

import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import com.camcamcamcamcam.biome_backlog.register.ModTreeDecoratorTypes;
import com.camcamcamcamcam.biome_backlog.world.level.block.CoconutBlock;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class CoconutDecorator extends TreeDecorator {
	public static final Codec<CoconutDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(CoconutDecorator::new, (p_69989_) -> {
		return p_69989_.probability;
	}).codec();
	private final float probability;

	public CoconutDecorator(float p_69976_) {
		this.probability = p_69976_;
	}

	protected TreeDecoratorType<?> type() {
		return ModTreeDecoratorTypes.COCONUT.get();
	}

	public void place(TreeDecorator.Context p_226028_) {
		RandomSource randomsource = p_226028_.random();
		List<BlockPos> list = p_226028_.logs();
		int i = list.get(0).getY();
		list.stream().filter((p_69980_) -> {
			return p_69980_.getY() - i > 2;
		}).forEach((p_226026_) -> {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				if (randomsource.nextFloat() < this.probability) {
					Direction direction1 = direction.getOpposite();
					BlockPos blockpos = p_226026_.offset(direction1.getStepX(), 0, direction1.getStepZ());
					if (p_226028_.level().isStateAtPosition(blockpos, state -> {
						return state.isAir() || state.is(ModBlocks.PALM_LEAVES_HANGING.get());
					})) {
						p_226028_.setBlock(blockpos, ModBlocks.COCONUT.get().defaultBlockState().setValue(CoconutBlock.GREEN, true).setValue(CocoaBlock.FACING, direction));
					}
				}
			}

		});
	}
}