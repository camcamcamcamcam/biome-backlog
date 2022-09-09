package com.minecraftabnormals.biome_vote_losers.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SaltLampBlock extends SaltBlock {

    private static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 13.0D, 13.0D);

    public SaltLampBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState p_57809_, BlockGetter p_57810_, BlockPos p_57811_, CollisionContext p_57812_) {
        return SHAPE;
    }

}
