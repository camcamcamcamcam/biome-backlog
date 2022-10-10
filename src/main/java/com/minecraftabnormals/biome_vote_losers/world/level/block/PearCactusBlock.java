package com.minecraftabnormals.biome_vote_losers.world.level.block;

import com.minecraftabnormals.biome_vote_losers.register.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PearCactusBlock extends BushBlock implements BonemealableBlock {
	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 2);

	private static final VoxelShape MID_GROWTH_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public PearCactusBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
	}

	public VoxelShape getShape(BlockState p_57291_, BlockGetter p_57292_, BlockPos p_57293_, CollisionContext p_57294_) {
		return MID_GROWTH_SHAPE;
	}

	public boolean isRandomlyTicking(BlockState p_57284_) {
		return p_57284_.getValue(AGE) < 2;
	}

	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		int i = state.getValue(AGE);
		if (i < 2 && level.getRawBrightness(pos.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(5) == 0)) {
			BlockState blockstate = state.setValue(AGE, Integer.valueOf(i + 1));
			level.setBlock(pos, blockstate, 2);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockstate));
			net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, state);
		}
	}

	public void entityInside(BlockState p_51148_, Level p_51149_, BlockPos p_51150_, Entity p_51151_) {
		if (p_51151_.isPickable() & p_51148_.getBlock() == ModBlocks.PEAR_CACTUS.get()) {
			p_51151_.hurt(DamageSource.CACTUS, 1.0F);
		}
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51164_) {
		p_51164_.add(AGE);
	}

	public boolean isPathfindable(BlockState p_51143_, BlockGetter p_51144_, BlockPos p_51145_, PathComputationType p_51146_) {
		return false;
	}

	@Override
	public net.minecraftforge.common.PlantType getPlantType(BlockGetter world, BlockPos pos) {
		return net.minecraftforge.common.PlantType.DESERT;
	}


	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return state.is(BlockTags.DIRT) || state.is(BlockTags.SAND);
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter p_50897_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
		return p_50899_.getValue(AGE) < 2;
	}

	@Override
	public boolean isBonemealSuccess(Level p_220878_, RandomSource p_220879_, BlockPos p_220880_, BlockState p_220881_) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos pos, BlockState state) {
		int i = state.getValue(AGE);
		BlockState blockstate = state.setValue(AGE, Integer.valueOf(i + 1));
		level.setBlock(pos, blockstate, 2);
		level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockstate));
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if (player.getItemInHand(hand).getItem() instanceof AxeItem item && state.getBlock() == ModBlocks.PEAR_CACTUS.get()) {
			item.damageItem(player.getItemInHand(hand), 1, player, p -> p.broadcastBreakEvent(hand));
			player.swing(hand);
			int age = state.getValue(AGE);
			level.setBlock(pos, ModBlocks.STRIPPED_PEAR_CACTUS.get().defaultBlockState().setValue(AGE, age), 3);
			return InteractionResult.PASS;
		}
		return InteractionResult.FAIL;
	}
}
