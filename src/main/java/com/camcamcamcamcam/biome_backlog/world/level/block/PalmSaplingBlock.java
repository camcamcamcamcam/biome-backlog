package com.camcamcamcamcam.biome_backlog.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class PalmSaplingBlock extends SaplingBlock {

    public PalmSaplingBlock(AbstractTreeGrower p_55978_, Properties p_55979_) {
        super(p_55978_, p_55979_);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return state.is(BlockTags.DIRT) || state.is(BlockTags.SAND);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
        return state.is(BlockTags.SAND);
    }
}
