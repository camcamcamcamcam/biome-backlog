package com.camcamcamcamcam.biome_backlog.world.gen.decorator;

import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import com.camcamcamcamcam.biome_backlog.register.ModTreeDecoratorTypes;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class DateDecorator extends TreeDecorator {
	public static final Codec<DateDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(DateDecorator::new, (p_69989_) -> {
		return p_69989_.probability;
	}).codec();
	private final float probability;

	public DateDecorator(float p_69976_) {
		this.probability = p_69976_;
	}

	protected TreeDecoratorType<?> type() {
		return ModTreeDecoratorTypes.DATE.get();
	}

	public void place(Context p_226028_) {
		RandomSource randomsource = p_226028_.random();
		List<BlockPos> list = p_226028_.logs();
		int i = list.get(0).getY();
		list.forEach((p_226026_) -> {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				if (randomsource.nextFloat() < this.probability) {
					Direction direction1 = direction.getOpposite();
					BlockPos blockpos = p_226026_.offset(direction1.getStepX(), 0, direction1.getStepZ());
					if (p_226028_.level().isStateAtPosition(blockpos, state -> {
						return state.isAir() || state.is(ModBlocks.PALM_LEAVES_HANGING.get());
					}) && p_226028_.level().isStateAtPosition(blockpos.above(), state -> {
						return state.is(BlockTags.LEAVES);
					})) {
						p_226028_.setBlock(blockpos, ModBlocks.DATE_BUNCH.get().defaultBlockState());
					}
				}
			}

		});
	}
}