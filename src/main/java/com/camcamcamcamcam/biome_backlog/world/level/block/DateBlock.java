package com.camcamcamcamcam.biome_backlog.world.level.block;

import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DateBlock extends Block implements BonemealableBlock {

    private static final VoxelShape HANGING_SHAPE = Block.box(3.0D, 4.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    private static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 12.0D, 13.0D);

    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;


    public DateBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, true));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return context.getClickedFace() != Direction.UP ? this.defaultBlockState() : this.defaultBlockState().setValue(HANGING, false);
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? HANGING_SHAPE : SHAPE;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_57799_) {
        p_57799_.add(HANGING);
    }

	@Override
    public boolean isValidBonemealTarget(LevelReader setter, BlockPos pos, BlockState state) {
		return true;
	}

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource source, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource source, BlockPos pos, BlockState state) {
        level.setBlock(pos, ModBlocks.DATE_SAPLING.get().defaultBlockState(), 3);

        if (!level.getBlockState(pos.below()).is(BlockTags.SAND)) {
            level.destroyBlock(pos, true);
        }
    }
}
