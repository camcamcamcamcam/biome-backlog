package com.minecraftabnormals.biome_vote_losers.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class OstrichEggBlock extends Block {

    public static final int MAX_HATCH_LEVEL = 2;
    private static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 12.0D, 13.0D);
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;

    public OstrichEggBlock(Properties p_57759_) {
        super(p_57759_);
        this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, 0));
    }

    public VoxelShape getShape(BlockState p_57809_, BlockGetter p_57810_, BlockPos p_57811_, CollisionContext p_57812_) {
        return SHAPE;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_57799_) {
        p_57799_.add(HATCH);
    }
}
