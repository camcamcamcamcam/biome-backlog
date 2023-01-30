package com.minecraftabnormals.biome_backlog.world.level.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class CoconutSaplingBlock extends PalmSaplingBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;


    public CoconutSaplingBlock(AbstractTreeGrower p_55978_, Properties p_55979_) {
        super(p_55978_, p_55979_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState rotate(BlockState p_54125_, Rotation p_54126_) {
        return p_54125_.setValue(FACING, p_54126_.rotate(p_54125_.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState p_54122_, Mirror p_54123_) {
        return p_54122_.rotate(p_54123_.getRotation(p_54122_.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49573_) {
        return this.defaultBlockState().setValue(FACING, p_49573_.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49646_) {
        super.createBlockStateDefinition(p_49646_);
        p_49646_.add(FACING);
    }
}
