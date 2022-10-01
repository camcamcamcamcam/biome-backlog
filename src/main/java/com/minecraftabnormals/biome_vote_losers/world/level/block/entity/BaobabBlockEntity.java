package com.minecraftabnormals.biome_vote_losers.world.level.block.entity;

import com.minecraftabnormals.biome_vote_losers.register.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BaobabBlockEntity extends BlockEntity {
    public BaobabBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BAOBAB_FLOWER.get(), pos, state);
    }
}
