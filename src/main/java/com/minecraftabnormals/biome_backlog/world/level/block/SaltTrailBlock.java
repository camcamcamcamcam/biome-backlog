package com.minecraftabnormals.biome_backlog.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.minecraftabnormals.biome_backlog.register.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;

public class SaltTrailBlock extends Block {

    public static final EnumProperty<RedstoneSide> NORTH = BlockStateProperties.NORTH_REDSTONE;
    public static final EnumProperty<RedstoneSide> EAST = BlockStateProperties.EAST_REDSTONE;
    public static final EnumProperty<RedstoneSide> SOUTH = BlockStateProperties.SOUTH_REDSTONE;
    public static final EnumProperty<RedstoneSide> WEST = BlockStateProperties.WEST_REDSTONE;
    public static final Map<Direction, EnumProperty<RedstoneSide>> PROPERTY_BY_DIRECTION = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST));

    private final BlockState crossState;

    public static final Map<Direction, EnumProperty<RedstoneSide>> FACING_PROPERTY_MAP = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST));
    protected static final VoxelShape[] SHAPES = new VoxelShape[]{Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D), Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Block.box(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D), Block.box(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Block.box(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Block.box(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Block.box(0.0D, 0.0D, 0.0D, 13.0D, 1.0D, 16.0D), Block.box(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Block.box(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 16.0D), Block.box(0.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Block.box(0.0D, 0.0D, 3.0D, 16.0D, 1.0D, 16.0D), Block.box(3.0D, 0.0D, 0.0D, 16.0D, 1.0D, 13.0D), Block.box(3.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 13.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D)};

    public SaltTrailBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(NORTH, RedstoneSide.NONE).setValue(EAST, RedstoneSide.NONE).setValue(SOUTH, RedstoneSide.NONE).setValue(WEST, RedstoneSide.NONE));
        this.crossState = this.defaultBlockState().setValue(NORTH, RedstoneSide.SIDE).setValue(EAST, RedstoneSide.SIDE).setValue(SOUTH, RedstoneSide.SIDE).setValue(WEST, RedstoneSide.SIDE);

        for(BlockState blockstate : this.getStateDefinition().getPossibleStates()) {
            SHAPES_CACHE.put(blockstate, this.calculateShape(blockstate));
        }
    }


    private static final VoxelShape SHAPE_DOT = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);
    private static final Map<Direction, VoxelShape> SHAPES_FLOOR = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Direction.SOUTH, Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Direction.EAST, Block.box(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Direction.WEST, Block.box(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)));
    private static final Map<Direction, VoxelShape> SHAPES_UP = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Shapes.or(SHAPES_FLOOR.get(Direction.NORTH), Block.box(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 1.0D)), Direction.SOUTH, Shapes.or(SHAPES_FLOOR.get(Direction.SOUTH), Block.box(3.0D, 0.0D, 15.0D, 13.0D, 16.0D, 16.0D)), Direction.EAST, Shapes.or(SHAPES_FLOOR.get(Direction.EAST), Block.box(15.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D)), Direction.WEST, Shapes.or(SHAPES_FLOOR.get(Direction.WEST), Block.box(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 13.0D))));
    private static final Map<BlockState, VoxelShape> SHAPES_CACHE = Maps.newHashMap();

    private VoxelShape calculateShape(BlockState p_55643_) {
        VoxelShape voxelshape = SHAPE_DOT;

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide redstoneside = p_55643_.getValue(PROPERTY_BY_DIRECTION.get(direction));
            if (redstoneside == RedstoneSide.SIDE) {
                voxelshape = Shapes.or(voxelshape, SHAPES_FLOOR.get(direction));
            } else if (redstoneside == RedstoneSide.UP) {
                voxelshape = Shapes.or(voxelshape, SHAPES_UP.get(direction));
            }
        }

        return voxelshape;
    }

    public VoxelShape getShape(BlockState p_55620_, BlockGetter p_55621_, BlockPos p_55622_, CollisionContext p_55623_) {
        return SHAPES_CACHE.get(p_55620_);
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_55513_) {
        return this.getConnectionState(p_55513_.getLevel(), this.crossState, p_55513_.getClickedPos());
    }

    private BlockState getConnectionState(BlockGetter p_55515_, BlockState p_55516_, BlockPos p_55517_) {
        boolean flag = isDot(p_55516_);
        p_55516_ = this.getMissingConnections(p_55515_, this.defaultBlockState(), p_55517_);
        if (flag && isDot(p_55516_)) {
            return p_55516_;
        } else {
            boolean flag1 = p_55516_.getValue(NORTH).isConnected();
            boolean flag2 = p_55516_.getValue(SOUTH).isConnected();
            boolean flag3 = p_55516_.getValue(EAST).isConnected();
            boolean flag4 = p_55516_.getValue(WEST).isConnected();
            boolean flag5 = !flag1 && !flag2;
            boolean flag6 = !flag3 && !flag4;
            if (!flag4 && flag5) {
                p_55516_ = p_55516_.setValue(WEST, RedstoneSide.SIDE);
            }

            if (!flag3 && flag5) {
                p_55516_ = p_55516_.setValue(EAST, RedstoneSide.SIDE);
            }

            if (!flag1 && flag6) {
                p_55516_ = p_55516_.setValue(NORTH, RedstoneSide.SIDE);
            }

            if (!flag2 && flag6) {
                p_55516_ = p_55516_.setValue(SOUTH, RedstoneSide.SIDE);
            }

            return p_55516_;
        }
    }

    private BlockState getMissingConnections(BlockGetter p_55609_, BlockState p_55610_, BlockPos p_55611_) {
        boolean flag = !p_55609_.getBlockState(p_55611_.above()).isRedstoneConductor(p_55609_, p_55611_);

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (!p_55610_.getValue(PROPERTY_BY_DIRECTION.get(direction)).isConnected()) {
                RedstoneSide redstoneside = this.getConnectingSide(p_55609_, p_55611_, direction, flag);
                p_55610_ = p_55610_.setValue(PROPERTY_BY_DIRECTION.get(direction), redstoneside);
            }
        }

        return p_55610_;
    }

    public BlockState updateShape(BlockState p_55598_, Direction p_55599_, BlockState p_55600_, LevelAccessor p_55601_, BlockPos p_55602_, BlockPos p_55603_) {
        if (p_55599_ == Direction.DOWN) {
            return p_55598_;
        } else if (p_55599_ == Direction.UP) {
            return this.getConnectionState(p_55601_, p_55598_, p_55602_);
        } else {
            RedstoneSide redstoneside = this.getConnectingSide(p_55601_, p_55602_, p_55599_);
            return redstoneside.isConnected() == p_55598_.getValue(PROPERTY_BY_DIRECTION.get(p_55599_)).isConnected() && !isCross(p_55598_) ? p_55598_.setValue(PROPERTY_BY_DIRECTION.get(p_55599_), redstoneside) : this.getConnectionState(p_55601_, this.crossState.setValue(PROPERTY_BY_DIRECTION.get(p_55599_), redstoneside), p_55602_);
        }
    }

    private static boolean isCross(BlockState p_55645_) {
        return p_55645_.getValue(NORTH).isConnected() && p_55645_.getValue(SOUTH).isConnected() && p_55645_.getValue(EAST).isConnected() && p_55645_.getValue(WEST).isConnected();
    }

    private static boolean isDot(BlockState p_55647_) {
        return !p_55647_.getValue(NORTH).isConnected() && !p_55647_.getValue(SOUTH).isConnected() && !p_55647_.getValue(EAST).isConnected() && !p_55647_.getValue(WEST).isConnected();
    }

    public void updateIndirectNeighbourShapes(BlockState p_55579_, LevelAccessor p_55580_, BlockPos p_55581_, int p_55582_, int p_55583_) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide redstoneside = p_55579_.getValue(PROPERTY_BY_DIRECTION.get(direction));
            if (redstoneside != RedstoneSide.NONE && !p_55580_.getBlockState(blockpos$mutableblockpos.setWithOffset(p_55581_, direction)).is(this)) {
                blockpos$mutableblockpos.move(Direction.DOWN);
                BlockState blockstate = p_55580_.getBlockState(blockpos$mutableblockpos);
                if (blockstate.is(this)) {
                    BlockPos blockpos = blockpos$mutableblockpos.relative(direction.getOpposite());
                    p_55580_.neighborShapeChanged(direction.getOpposite(), p_55580_.getBlockState(blockpos), blockpos$mutableblockpos, blockpos, p_55582_, p_55583_);
                }

                blockpos$mutableblockpos.setWithOffset(p_55581_, direction).move(Direction.UP);
                BlockState blockstate1 = p_55580_.getBlockState(blockpos$mutableblockpos);
                if (blockstate1.is(this)) {
                    BlockPos blockpos1 = blockpos$mutableblockpos.relative(direction.getOpposite());
                    p_55580_.neighborShapeChanged(direction.getOpposite(), p_55580_.getBlockState(blockpos1), blockpos$mutableblockpos, blockpos1, p_55582_, p_55583_);
                }
            }
        }

    }

    private RedstoneSide getConnectingSide(BlockGetter p_55519_, BlockPos p_55520_, Direction p_55521_) {
        return this.getConnectingSide(p_55519_, p_55520_, p_55521_, !p_55519_.getBlockState(p_55520_.above()).isRedstoneConductor(p_55519_, p_55520_));
    }

    private RedstoneSide getConnectingSide(BlockGetter p_55523_, BlockPos p_55524_, Direction p_55525_, boolean p_55526_) {
        BlockPos blockpos = p_55524_.relative(p_55525_);
        BlockState blockstate = p_55523_.getBlockState(blockpos);
        if (p_55526_) {
            boolean flag = this.canSurviveOn(p_55523_, blockpos, blockstate);
            if (flag && p_55523_.getBlockState(blockpos.above()).getBlock() instanceof SaltTrailBlock) {
                if (blockstate.isFaceSturdy(p_55523_, blockpos, p_55525_.getOpposite())) {
                    return RedstoneSide.UP;
                }

                return RedstoneSide.SIDE;
            }
        }

        if (blockstate.getBlock() instanceof SaltTrailBlock) {
            return RedstoneSide.SIDE;
        } else if (blockstate.isRedstoneConductor(p_55523_, blockpos)) {
            return RedstoneSide.NONE;
        } else {
            BlockPos blockPosBelow = blockpos.below();
            return p_55523_.getBlockState(blockPosBelow).getBlock() instanceof SaltBlock ? RedstoneSide.SIDE : RedstoneSide.NONE;
        }
    }

    public boolean canSurvive(BlockState p_55585_, LevelReader p_55586_, BlockPos p_55587_) {
        BlockPos blockpos = p_55587_.below();
        BlockState blockstate = p_55586_.getBlockState(blockpos);
        return this.canSurviveOn(p_55586_, blockpos, blockstate);
    }

    private boolean canSurviveOn(BlockGetter p_55613_, BlockPos p_55614_, BlockState p_55615_) {
        return p_55615_.isFaceSturdy(p_55613_, p_55614_, Direction.UP) || p_55615_.is(Blocks.HOPPER);
    }

    private void checkCornerChangeAt(Level p_55617_, BlockPos p_55618_) {
        if (p_55617_.getBlockState(p_55618_).is(this)) {
            p_55617_.updateNeighborsAt(p_55618_, this);

            for(Direction direction : Direction.values()) {
                p_55617_.updateNeighborsAt(p_55618_.relative(direction), this);
            }

        }
    }

    public void onPlace(BlockState p_55630_, Level p_55631_, BlockPos p_55632_, BlockState p_55633_, boolean p_55634_) {
        if (!p_55633_.is(p_55630_.getBlock()) && !p_55631_.isClientSide) {

            for(Direction direction : Direction.Plane.VERTICAL) {
                p_55631_.updateNeighborsAt(p_55632_.relative(direction), this);
            }

            this.updateNeighborsOfNeighboringWires(p_55631_, p_55632_);
        }
    }

    public void onRemove(BlockState p_55568_, Level p_55569_, BlockPos p_55570_, BlockState p_55571_, boolean p_55572_) {
        if (!p_55572_ && !p_55568_.is(p_55571_.getBlock())) {
            super.onRemove(p_55568_, p_55569_, p_55570_, p_55571_, p_55572_);
            if (!p_55569_.isClientSide) {
                for(Direction direction : Direction.values()) {
                    p_55569_.updateNeighborsAt(p_55570_.relative(direction), this);
                }

                this.updateNeighborsOfNeighboringWires(p_55569_, p_55570_);
            }
        }
    }

    private void updateNeighborsOfNeighboringWires(Level p_55638_, BlockPos p_55639_) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            this.checkCornerChangeAt(p_55638_, p_55639_.relative(direction));
        }

        for(Direction direction1 : Direction.Plane.HORIZONTAL) {
            BlockPos blockpos = p_55639_.relative(direction1);
            if (p_55638_.getBlockState(blockpos).isRedstoneConductor(p_55638_, blockpos)) {
                this.checkCornerChangeAt(p_55638_, blockpos.above());
            } else {
                this.checkCornerChangeAt(p_55638_, blockpos.below());
            }
        }

    }

    public void neighborChanged(BlockState p_55561_, Level p_55562_, BlockPos p_55563_, Block p_55564_, BlockPos p_55565_, boolean p_55566_) {
        if (!p_55562_.isClientSide) {
            if (!p_55561_.canSurvive(p_55562_, p_55563_)) {
                dropResources(p_55561_, p_55562_, p_55563_);
                p_55562_.removeBlock(p_55563_, false);
            }

        }
    }

    protected static boolean shouldConnectTo(BlockState p_55641_) {
        return shouldConnectTo(p_55641_, (Direction)null);
    }

    protected static boolean shouldConnectTo(BlockState p_55595_, @Nullable Direction p_55596_) {
        return p_55595_.is(ModBlocks.SALT_TRAIL.get());
    }

    public BlockState rotate(BlockState p_55592_, Rotation p_55593_) {
        switch (p_55593_) {
            case CLOCKWISE_180:
                return p_55592_.setValue(NORTH, p_55592_.getValue(SOUTH)).setValue(EAST, p_55592_.getValue(WEST)).setValue(SOUTH, p_55592_.getValue(NORTH)).setValue(WEST, p_55592_.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return p_55592_.setValue(NORTH, p_55592_.getValue(EAST)).setValue(EAST, p_55592_.getValue(SOUTH)).setValue(SOUTH, p_55592_.getValue(WEST)).setValue(WEST, p_55592_.getValue(NORTH));
            case CLOCKWISE_90:
                return p_55592_.setValue(NORTH, p_55592_.getValue(WEST)).setValue(EAST, p_55592_.getValue(NORTH)).setValue(SOUTH, p_55592_.getValue(EAST)).setValue(WEST, p_55592_.getValue(SOUTH));
            default:
                return p_55592_;
        }
    }

    public BlockState mirror(BlockState p_55589_, Mirror p_55590_) {
        switch (p_55590_) {
            case LEFT_RIGHT:
                return p_55589_.setValue(NORTH, p_55589_.getValue(SOUTH)).setValue(SOUTH, p_55589_.getValue(NORTH));
            case FRONT_BACK:
                return p_55589_.setValue(EAST, p_55589_.getValue(WEST)).setValue(WEST, p_55589_.getValue(EAST));
            default:
                return super.mirror(p_55589_, p_55590_);
        }
    }

    private void updatesOnShapeChange(Level p_55535_, BlockPos p_55536_, BlockState p_55537_, BlockState p_55538_) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockpos = p_55536_.relative(direction);
            if (p_55537_.getValue(PROPERTY_BY_DIRECTION.get(direction)).isConnected() != p_55538_.getValue(PROPERTY_BY_DIRECTION.get(direction)).isConnected() && p_55535_.getBlockState(blockpos).isRedstoneConductor(p_55535_, blockpos)) {
                p_55535_.updateNeighborsAtExceptFromFacing(blockpos, p_55538_.getBlock(), direction.getOpposite());
            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55605_) {
        p_55605_.add(NORTH, EAST, SOUTH, WEST);
    }

}
