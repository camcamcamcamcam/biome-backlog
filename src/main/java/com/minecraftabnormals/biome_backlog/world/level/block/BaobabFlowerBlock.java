package com.minecraftabnormals.biome_backlog.world.level.block;

import com.minecraftabnormals.biome_backlog.register.ModBlockEntities;
import com.minecraftabnormals.biome_backlog.world.level.block.entity.BaobabBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BaobabFlowerBlock extends HangingLeavesBlock implements EntityBlock {
    private static final BooleanProperty OPEN = BooleanProperty.create("open");

    private static final VoxelShape SHAPE = Block.box(6.0, 7.0, 6.0, 10.0, 16.0, 10.0);

    public BaobabFlowerBlock(BlockBehaviour.Properties p_153337_) {
        super(p_153337_);

        this.registerDefaultState(this.stateDefinition.any().setValue(OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);

        builder.add(OPEN);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    private static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T entity) {
        /* Only check once per second. This is what daylight detectors do. */
        if (level.getGameTime() % 20L != 0L) {
            return;
        }

        /* NOTE: We don't do a game event here because I don't think a flower opening would make vibrations. */

        if (state.getValue(OPEN)) {
            if (level.isDay()) {
                level.setBlock(pos, state.setValue(OPEN, false), Block.UPDATE_CLIENTS);
            }
        } else {
            if (!level.isDay()) {
                level.setBlock(pos, state.setValue(OPEN, true), Block.UPDATE_CLIENTS);
            }
        }
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (type != ModBlockEntities.BAOBAB_FLOWER.get()) {
            return null;
        }

        if (level.isClientSide) {
            return null;
        }

        if (!level.dimensionType().hasSkyLight()) {
            return null;
        }

        return BaobabFlowerBlock::tick;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BaobabBlockEntity(pos, state);
    }
}
