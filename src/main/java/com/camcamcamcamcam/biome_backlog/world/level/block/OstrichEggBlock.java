package com.camcamcamcamcam.biome_backlog.world.level.block;

import com.camcamcamcamcam.biome_backlog.world.level.entity.Ostrich;
import com.camcamcamcamcam.biome_backlog.register.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class OstrichEggBlock extends Block {

	public static final int MAX_HATCH_LEVEL = 2;
	private static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 12.0D, 13.0D);
	public static final IntegerProperty HATCH = BlockStateProperties.HATCH;

	public OstrichEggBlock(Properties p_57759_) {
		super(p_57759_);
		this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, 0));
	}

	@Override
	public void playerWillDestroy(Level p_49852_, BlockPos p_49853_, BlockState p_49854_, Player p_49855_) {
		super.playerWillDestroy(p_49852_, p_49853_, p_49854_, p_49855_);
		angerNearbyOstrichs(p_49855_);
	}

	public static void angerNearbyOstrichs(Player p_34874_) {
		List<Ostrich> list = p_34874_.level.getEntitiesOfClass(Ostrich.class, p_34874_.getBoundingBox().inflate(16.0D));
		list.stream().filter((p_34881_) -> {
			return p_34881_.hasLineOfSight(p_34874_) && !p_34874_.isCreative();
		}).forEach((p_34872_) -> {
			p_34872_.setPersistentAngerTarget(p_34874_.getUUID());
			p_34872_.startPersistentAngerTimer();
		});
	}

	public VoxelShape getShape(BlockState p_57809_, BlockGetter p_57810_, BlockPos p_57811_, CollisionContext p_57812_) {
		return SHAPE;
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_57799_) {
		p_57799_.add(HATCH);
	}

	public void randomTick(BlockState p_222644_, ServerLevel p_222645_, BlockPos p_222646_, RandomSource p_222647_) {
		if (p_222647_.nextFloat() < 0.45F) {
			int i = p_222644_.getValue(HATCH);
			if (i < 2) {
				p_222645_.playSound((Player) null, p_222646_, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + p_222647_.nextFloat() * 0.2F);
				p_222645_.setBlock(p_222646_, p_222644_.setValue(HATCH, Integer.valueOf(i + 1)), 2);
			} else {
				p_222645_.playSound((Player) null, p_222646_, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + p_222647_.nextFloat() * 0.2F);
				p_222645_.removeBlock(p_222646_, false);

				p_222645_.levelEvent(2001, p_222646_, Block.getId(p_222644_));
				Ostrich ostrich = ModEntities.OSTRICH.get().create(p_222645_);
				ostrich.setAge(-24000);
				ostrich.moveTo((double) p_222646_.getX() + 0.5D, (double) p_222646_.getY(), (double) p_222646_.getZ() + 0.5D, 0.0F, 0.0F);
				p_222645_.addFreshEntity(ostrich);
			}
		}

	}
}
