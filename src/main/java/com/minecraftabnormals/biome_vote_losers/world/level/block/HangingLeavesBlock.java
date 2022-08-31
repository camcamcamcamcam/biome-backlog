package com.minecraftabnormals.biome_vote_losers.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.HangingRootsBlock;
import net.minecraft.world.level.block.state.BlockState;

public class HangingLeavesBlock extends HangingRootsBlock {
    public HangingLeavesBlock(Properties p_153337_) {
        super(p_153337_);
    }

    @Override
    public boolean canSurvive(BlockState p_153347_, LevelReader p_153348_, BlockPos p_153349_) {
        BlockPos blockpos = p_153349_.above();
        BlockState blockstate = p_153348_.getBlockState(blockpos);
        return blockstate.isCollisionShapeFullBlock(p_153348_, blockpos);
    }
}
