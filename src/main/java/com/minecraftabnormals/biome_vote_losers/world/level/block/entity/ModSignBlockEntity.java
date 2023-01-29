package com.minecraftabnormals.biome_vote_losers.world.level.block.entity;

import com.minecraftabnormals.biome_vote_losers.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModSignBlockEntity extends SignBlockEntity {
	public ModSignBlockEntity(BlockPos p_155700_, BlockState p_155701_) {
		super(p_155700_, p_155701_);
	}

	@Override
	public BlockEntityType<?> getType() {
		return ModBlockEntities.MOD_SIGN.get();
	}
}
