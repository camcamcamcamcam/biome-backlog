package com.camcamcamcamcam.biome_backlog.world.gen.trunk;

import com.camcamcamcamcam.biome_backlog.register.ModTrunkPlacerTypes;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiConsumer;

public class PalmTrunkPlacer extends TrunkPlacer {
	public static final Codec<PalmTrunkPlacer> CODEC = RecordCodecBuilder.create((p_70161_) -> {
		return trunkPlacerParts(p_70161_).apply(p_70161_, PalmTrunkPlacer::new);
	});

	public PalmTrunkPlacer(int p_70148_, int p_70149_, int p_70150_) {
		super(p_70148_, p_70149_, p_70150_);
	}

	protected TrunkPlacerType<?> type() {
		return ModTrunkPlacerTypes.PALM_TRUNK_PLACER;
	}

	public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader p_226116_, BiConsumer<BlockPos, BlockState> p_226117_, RandomSource p_226118_, int p_226119_, BlockPos p_226120_, TreeConfiguration p_226121_) {
		setDirtAt(p_226116_, p_226117_, p_226118_, p_226120_.below(), p_226121_);
		List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
		Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(p_226118_);
		int i = p_226119_ - p_226118_.nextInt(4) - 1;
		int j = 3 - p_226118_.nextInt(3);
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
		int k = p_226120_.getX();
		int l = p_226120_.getZ();
		OptionalInt optionalint = OptionalInt.empty();

		for (int i1 = 0; i1 < p_226119_; ++i1) {
			int j1 = p_226120_.getY() + i1;
			if (i1 >= i && j > 0) {
				k += direction.getStepX();
				l += direction.getStepZ();
				--j;
			}

			if (this.placeLog(p_226116_, p_226117_, p_226118_, blockpos$mutableblockpos.set(k, j1, l), p_226121_)) {
				optionalint = OptionalInt.of(j1 + 1);
			}
		}

		if (optionalint.isPresent()) {
			list.add(new FoliagePlacer.FoliageAttachment(new BlockPos(k, optionalint.getAsInt(), l), 1, false));
		}
		return list;
	}
}