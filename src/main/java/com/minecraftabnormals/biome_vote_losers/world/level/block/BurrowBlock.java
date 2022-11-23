package com.minecraftabnormals.biome_vote_losers.world.level.block;

import com.minecraftabnormals.biome_vote_losers.register.ModBlockEntities;
import com.minecraftabnormals.biome_vote_losers.world.level.block.entity.BurrowBlockEntity;
import com.minecraftabnormals.biome_vote_losers.world.level.entity.Meerkat;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BurrowBlock extends BaseEntityBlock {
	public BurrowBlock(Properties properties) {
		super(properties);
	}

	public void onRemove(BlockState p_48713_, Level p_48714_, BlockPos p_48715_, BlockState p_48716_, boolean p_48717_) {
		if (!p_48713_.is(p_48716_.getBlock())) {
			BlockEntity blockentity = p_48714_.getBlockEntity(p_48715_);
			if (blockentity instanceof BurrowBlockEntity) {
				if (p_48714_ instanceof ServerLevel) {
					((BurrowBlockEntity) blockentity).emptyAllLivingFromBurrow(null, BurrowBlockEntity.BurrowReleaseStatus.EMERGENCY);
					p_48714_.updateNeighbourForOutputSignal(p_48715_, this);
				}
			}

			super.onRemove(p_48713_, p_48714_, p_48715_, p_48716_, p_48717_);
		}
	}

	public void playerDestroy(Level p_49584_, Player p_49585_, BlockPos p_49586_, BlockState p_49587_, @javax.annotation.Nullable BlockEntity p_49588_, ItemStack p_49589_) {
		super.playerDestroy(p_49584_, p_49585_, p_49586_, p_49587_, p_49588_, p_49589_);
		if (!p_49584_.isClientSide && p_49588_ instanceof BurrowBlockEntity burrow) {

			burrow.emptyAllLivingFromBurrow(p_49585_, BurrowBlockEntity.BurrowReleaseStatus.EMERGENCY);
			p_49584_.updateNeighbourForOutputSignal(p_49586_, this);
			this.angerNearbyMeerkats(p_49584_, p_49586_);
		}

	}

	private void angerNearbyMeerkats(Level p_49650_, BlockPos p_49651_) {
		List<Meerkat> list = p_49650_.getEntitiesOfClass(Meerkat.class, (new AABB(p_49651_)).inflate(8.0D, 6.0D, 8.0D));
		if (!list.isEmpty()) {
			List<Player> list1 = p_49650_.getEntitiesOfClass(Player.class, (new AABB(p_49651_)).inflate(8.0D, 6.0D, 8.0D));
			if (list1.isEmpty()) return; //Forge: Prevent Error when no players are around.
			int i = list1.size();

			for (Meerkat meerkat : list) {
				if (meerkat.getTarget() == null) {
					meerkat.setTarget(list1.get(p_49650_.random.nextInt(i)));
				}
			}
		}

	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return new BurrowBlockEntity(p_153215_, p_153216_);
	}

	@javax.annotation.Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
		return p_152180_.isClientSide ? null : createTickerHelper(p_152182_, ModBlockEntities.BURROW.get(), BurrowBlockEntity::serverTick);
	}
}
