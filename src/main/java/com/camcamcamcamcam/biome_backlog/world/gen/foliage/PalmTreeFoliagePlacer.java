package com.camcamcamcamcam.biome_backlog.world.gen.foliage;

import com.camcamcamcamcam.biome_backlog.register.ModFoliagePlacerTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.function.BiConsumer;

public class PalmTreeFoliagePlacer extends FoliagePlacer {
	public static final Codec<PalmTreeFoliagePlacer> CODEC = RecordCodecBuilder.create((p_68630_) -> {
		return foliagePlacerParts(p_68630_).and(Codec.intRange(0, 16).fieldOf("leaf_length").forGetter((p_161468_) -> {
			return p_161468_.leefLength;
		})).apply(p_68630_, PalmTreeFoliagePlacer::new);
	});
	private final int leefLength;

	public PalmTreeFoliagePlacer(IntProvider range, IntProvider rangeOffset, int leafLength) {
		super(range, rangeOffset);
		this.leefLength = leafLength;
	}

	protected FoliagePlacerType<?> type() {
		return ModFoliagePlacerTypes.PALM_TREE_FOLIAGE.get();
	}

	@Override
	protected void createFoliage(LevelSimulatedReader p_225723_, BiConsumer<BlockPos, BlockState> p_225614_, RandomSource p_225725_, TreeConfiguration p_225726_, int p_225727_, FoliagePlacer.FoliageAttachment p_225728_, int p_225729_, int p_225730_, int p_225731_) {
		BlockPos blockpos = p_225728_.pos();
		int attempts = p_225725_.nextInt(this.leefLength) + 3;
		tryPlaceLeaf(p_225723_, p_225614_, p_225725_, p_225726_, blockpos);
		BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos.mutable();
		for (int i = 0; i > -1; --i) {
			int j = 1 + i;
			this.placeLeavesRow(p_225723_, p_225614_, p_225725_, p_225726_, blockpos, j, i, false);
		}
		for (int i = 0; i < 10; ++i) {
			blockpos$mutableblockpos.setWithOffset(blockpos, p_225725_.nextInt(p_225730_) - p_225725_.nextInt(p_225730_), p_225725_.nextInt(p_225730_) - p_225730_ + 2, p_225725_.nextInt(p_225730_) - p_225725_.nextInt(p_225730_));
			tryPlaceLeaf(p_225723_, p_225614_, p_225725_, p_225726_, blockpos$mutableblockpos);
		}

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			BlockPos.MutableBlockPos blockpos$mutableblockpos2 = new BlockPos.MutableBlockPos(blockpos.getX(), blockpos.getY(), blockpos.getZ());
			int leavesGravity = 0;
			int maxLimit = attempts / 3;
			for (int i = 0; i < attempts; ++i) {
				blockpos$mutableblockpos2.move(direction);
				//when limit is come. down the pos
				if (leavesGravity >= maxLimit) {
					leavesGravity = 0;
					maxLimit /= 1.5F;
					tryPlaceLeaf(p_225723_, p_225614_, p_225725_, p_225726_, blockpos$mutableblockpos2);
					blockpos$mutableblockpos2.move(Direction.DOWN);
				} else {
					++leavesGravity;
				}
				tryPlaceLeaf(p_225723_, p_225614_, p_225725_, p_225726_, blockpos$mutableblockpos2);
			}
		}

	}

	public int foliageHeight(RandomSource p_225719_, int p_225720_, TreeConfiguration p_225721_) {
		return 5;
	}

	protected boolean shouldSkipLocation(RandomSource p_225712_, int p_225713_, int p_225714_, int p_225715_, int p_225716_, boolean p_225717_) {
		return false;
	}
}