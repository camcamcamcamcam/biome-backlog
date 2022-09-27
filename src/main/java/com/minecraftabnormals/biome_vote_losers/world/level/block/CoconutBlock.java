package com.minecraftabnormals.biome_vote_losers.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CoconutBlock extends HorizontalDirectionalBlock {

    public static final BooleanProperty GREEN = BooleanProperty.create("green");

    private static final VoxelShape SHAPE_GREEN_SOUTH = Block.box(4.0D, 0.0D, 6.0D, 12.0D, 10.0D, 14.0D);
    private static final VoxelShape SHAPE_GREEN_NORTH = Block.box(4.0D, 0.0D, 2.0D, 12.0D, 10.0D, 10.0D);
    private static final VoxelShape SHAPE_GREEN_EAST = Block.box(6.0D, 0.0D, 4.0D, 14.0D, 10.0D, 12.0D);
    private static final VoxelShape SHAPE_GREEN_WEST = Block.box(2.0D, 0.0D, 4.0D, 10.0D, 10.0D, 12.0D);

    private static final VoxelShape SHAPE_BROWN_SOUTH = Block.box(4.0D, 0.0D, 6.0D, 12.0D, 8.0D, 14.0D);
    private static final VoxelShape SHAPE_BROWN_NORTH = Block.box(4.0D, 0.0D, 2.0D, 12.0D, 8.0D, 10.0D);
    private static final VoxelShape SHAPE_BROWN_EAST = Block.box(6.0D, 0.0D, 4.0D, 14.0D, 8.0D, 12.0D);
    private static final VoxelShape SHAPE_BROWN_WEST = Block.box(2.0D, 0.0D, 4.0D, 10.0D, 8.0D, 12.0D);


    public CoconutBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(GREEN, false));

    }

    public VoxelShape getShape(BlockState p_57809_, BlockGetter p_57810_, BlockPos p_57811_, CollisionContext p_57812_) {
        if (p_57809_.getValue(GREEN)) {
            switch ((Direction) p_57809_.getValue(FACING)) {
                case SOUTH:
                    return SHAPE_GREEN_SOUTH;
                case NORTH:
                default:
                    return SHAPE_GREEN_NORTH;
                case WEST:
                    return SHAPE_GREEN_WEST;
                case EAST:
                    return SHAPE_GREEN_EAST;
            }
        } else {
            switch ((Direction) p_57809_.getValue(FACING)) {
                case SOUTH:
                    return SHAPE_BROWN_SOUTH;
                case NORTH:
                default:
                    return SHAPE_BROWN_NORTH;
                case WEST:
                    return SHAPE_BROWN_WEST;
                case EAST:
                    return SHAPE_BROWN_EAST;
            }
        }
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_57799_) {
        p_57799_.add(FACING, GREEN);
    }
}