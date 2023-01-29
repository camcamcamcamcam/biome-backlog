package com.minecraftabnormals.biome_backlog.world.level.block;

import com.minecraftabnormals.biome_backlog.register.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class MoundBlock extends RotatedPillarBlock {
    public MoundBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource source) {
        for (Direction direction : Direction.values()) {
            BlockPos adjacent = pos.offset(direction.getStepX(), direction.getStepY(), direction.getStepZ());
            BlockState unstripped = level.getBlockState(adjacent);
            if (unstripped.is(BlockTags.LOGS)) {
                BlockState stripped = AxeItem.getAxeStrippingState(unstripped);
                if (stripped != null) {
                    level.setBlock(adjacent, stripped, 3);
                    break;
                }
            } else if (unstripped.getBlock() == ModBlocks.MOUND.get()) {
                level.setBlock(adjacent, ModBlocks.TERMITE_MOUND.get().defaultBlockState().setValue(AXIS, unstripped.getValue(AXIS)), 3);

            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        InteractionResult interactionResult = super.use(state, level, pos, player, hand, result);
        if (player.getItemInHand(hand).getItem() == Items.PAPER && state.getBlock() == ModBlocks.TERMITE_MOUND.get()) {
            for (Direction direction : Direction.values()) {
                BlockPos adjacent = pos.offset(direction.getStepX(), direction.getStepY(), direction.getStepZ());
                BlockState blockState = level.getBlockState(adjacent);
                if (blockState.is(BlockTags.LOGS)) {
                    level.setBlock(adjacent, ModBlocks.MOUND.get().defaultBlockState().setValue(AXIS, blockState.getValue(AXIS)), 3);
                    player.swing(hand);
                    if (!player.isCreative()) {
                        player.getItemInHand(hand).shrink(1);
                    }
                    break;
                }
            }
        }
        return interactionResult;
    }
}
