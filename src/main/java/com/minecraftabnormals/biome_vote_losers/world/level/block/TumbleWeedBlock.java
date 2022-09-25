package com.minecraftabnormals.biome_vote_losers.world.level.block;

import com.minecraftabnormals.biome_vote_losers.register.ModEntities;
import com.minecraftabnormals.biome_vote_losers.world.level.entity.Tumbleweed;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TumbleWeedBlock extends BushBlock implements BonemealableBlock {
    private static final int MAX_AGE = 2;

    public static final IntegerProperty AGE     = BlockStateProperties.AGE_2;
    public static final BooleanProperty STUNTED = BooleanProperty.create("stunted");

    private static final VoxelShape SEEDLING_SHAPE    = Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0);
    private static final VoxelShape MID_GROWTH_SHAPE  = Block.box(3.0, 0.0, 3.0, 13.0, 10.0, 13.0);
    private static final VoxelShape FULLY_GROWN_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 13.0, 13.0);

    public TumbleWeedBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(STUNTED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        switch (state.getValue(AGE)) {
            case 0: return SEEDLING_SHAPE;
            case 1: return MID_GROWTH_SHAPE;
            case 2: return FULLY_GROWN_SHAPE;
        }

        return FULLY_GROWN_SHAPE;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(STUNTED);
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);
        if (age < MAX_AGE && level.getRawBrightness(pos.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(5) == 0)) {
            BlockState blockstate = state.setValue(AGE, Integer.valueOf(age + 1));
            level.setBlock(pos, blockstate, Block.UPDATE_CLIENTS);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockstate));
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, state);
        } else if (age >= MAX_AGE && random.nextInt(10) == 0) {
            level.removeBlock(pos, false);
            level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));

            final var looseTumbleweed = new Tumbleweed(ModEntities.TUMBLEWEED.get(), level);
            looseTumbleweed.setPos(Vec3.atBottomCenterOf(pos));

            level.addFreshEntity(looseTumbleweed);

            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (state.getValue(AGE) > 0 && entity instanceof LivingEntity) {
            entity.hurt(DamageSource.CACTUS, 1.0F);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51164_) {
        p_51164_.add(AGE, STUNTED);
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
    public boolean isValidBonemealTarget(BlockGetter p_50897_, BlockPos p_50898_, BlockState state, boolean p_50900_) {
        return !state.getValue(STUNTED) && state.getValue(AGE) < MAX_AGE;
    }

    @Override
    public boolean isBonemealSuccess(Level p_220878_, RandomSource p_220879_, BlockPos p_220880_, BlockState p_220881_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos pos, BlockState state) {
        int age = state.getValue(AGE);
        BlockState blockstate = state.setValue(AGE, age + 1);
        level.setBlock(pos, blockstate, Block.UPDATE_CLIENTS);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockstate));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        final var item = player.getItemInHand(hand);
        if (!item.is(Items.SHEARS)) {
            return InteractionResult.PASS;
        }

        if (state.getValue(STUNTED)) {
            return InteractionResult.PASS;
        }

        final var stunted_state = state.setValue(STUNTED, true);
        level.setBlock(pos, stunted_state, Block.UPDATE_CLIENTS);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(stunted_state));

        item.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);

        return InteractionResult.SUCCESS;
    }
}
