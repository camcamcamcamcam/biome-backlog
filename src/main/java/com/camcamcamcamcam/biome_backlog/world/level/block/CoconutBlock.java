package com.camcamcamcamcam.biome_backlog.world.level.block;

import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CoconutBlock extends HorizontalDirectionalBlock implements Fallable, BonemealableBlock {

    public static final BooleanProperty GREEN = BooleanProperty.create("green");

    private static final VoxelShape SHAPE_GREEN = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);

    private static final VoxelShape SHAPE_BROWN = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);


    public CoconutBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(GREEN, false));

    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (!state.getValue(GREEN)) {
            for (Direction direction : context.getNearestLookingDirections()) {
                if (direction.getAxis().isHorizontal()) {
                    state = state.setValue(FACING, direction.getOpposite());
                    break;
                }
            }
        } else {
            if (context.getClickedFace().getOpposite().getAxis().isHorizontal()) {
                state = state.setValue(FACING, context.getClickedFace().getOpposite());
            } else {
                state = state.setValue(FACING, Direction.NORTH);
            }
        }
        return state;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        InteractionResult use = super.use(state, level, pos, player, hand, result);
        Item item = player.getItemInHand(hand).getItem();
        if (item instanceof AxeItem && state.getValue(GREEN)) {
            player.swing(hand);
            item.damageItem(player.getItemInHand(hand), 1, player, p -> p.broadcastBreakEvent(hand));
            level.setBlock(pos, state.setValue(GREEN, false), 3);
            level.scheduleTick(pos, this, this.getDelayAfterPlace());
        } else if (item instanceof BucketItem && !state.getValue(GREEN)) {
            // todo make this work with multiple buckets
            player.setItemInHand(hand, Items.MILK_BUCKET.getDefaultInstance());
            level.destroyBlock(pos, true, player);
        }
        return use;
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource source) {

        if (isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight()) {
            FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(level, pos, state);
            this.falling(fallingblockentity);
        }
    }

    protected void falling(FallingBlockEntity entity) {
    }

    public static boolean isFree(BlockState p_53242_) {
        return p_53242_.isAir() || p_53242_.is(BlockTags.FIRE) || p_53242_.liquid() || p_53242_.canBeReplaced();
    }

    protected int getDelayAfterPlace() {
        return 2;
    }

    public VoxelShape getShape(BlockState p_57809_, BlockGetter p_57810_, BlockPos p_57811_, CollisionContext p_57812_) {
        return p_57809_.getValue(GREEN) ? SHAPE_GREEN : SHAPE_BROWN;
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_57799_) {
        p_57799_.add(FACING, GREEN);
    }

	@Override
	public boolean isValidBonemealTarget(LevelReader blockGetter, BlockPos pos, BlockState state, boolean isClientSide) {
		return !state.getValue(GREEN);
	}

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos pos, BlockState state) {
        if (!level.getBlockState(pos.below()).is(BlockTags.SAND)) {

            for (Direction direction : Direction.values()) {
            BlockPos offset = pos.offset(direction.getStepX(), direction.getStepY(), direction.getStepZ());
            if (direction.getAxis().isHorizontal() && level.getBlockState(offset).is(BlockTags.LOGS)) {
                level.setBlock(pos, state.setValue(GREEN, true).setValue(FACING, direction), 3);
                return;
            }
        }

            level.destroyBlock(pos, true);
        } else {
            level.setBlock(pos, ModBlocks.COCONUT_SAPLING.get().defaultBlockState().setValue(FACING, state.getValue(FACING)), 3);
        }
    }
}