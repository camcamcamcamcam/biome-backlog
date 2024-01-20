package com.camcamcamcamcam.biome_backlog.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class SaltBlock extends StainedGlassBlock {

    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public SaltBlock(Properties properties) {
        super(DyeColor.WHITE, properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(LIT, false));
    }

    @Override
    public void onPlace(BlockState p_60566_, Level level, BlockPos pos, BlockState p_60569_, boolean p_60570_) {
        level.scheduleTick(pos, this, 1);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource source) {
        if (!level.isClientSide) {
            int light = level.getLightEngine().getLayerListener(LightLayer.BLOCK).getLightValue(pos);
            level.setBlock(pos, state.setValue(LIT, light != 0), Block.UPDATE_ALL);
            level.scheduleTick(pos, this, 1);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_57799_) {
        p_57799_.add(LIT);
    }

}
