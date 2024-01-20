package com.camcamcamcamcam.biome_backlog.world.level.block;

import com.camcamcamcamcam.biome_backlog.world.level.block.entity.ModSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModStandingSignBlock extends StandingSignBlock {
	public ModStandingSignBlock(Properties p_56990_, WoodType p_56991_) {
		super(p_56991_, p_56990_);
	}

	public BlockEntity newBlockEntity(BlockPos p_154556_, BlockState p_154557_) {
		return new ModSignBlockEntity(p_154556_, p_154557_);
	}
}
