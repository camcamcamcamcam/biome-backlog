package com.minecraftabnormals.biome_vote_losers.world.level.block;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SucculentBlock extends BushBlock {

    private static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 8.0, 14.0);


    public static IntegerProperty TYPE = IntegerProperty.create("type", 0, 3);
    public static IntegerProperty COLOR = IntegerProperty.create("color", 0, 15);

    private static final Vec3[] COLORS =  {
            Vec3.fromRGB24(0x764257),
            Vec3.fromRGB24(0xC77979),
            Vec3.fromRGB24(0xA4734F),
            Vec3.fromRGB24(0xF1AE4F),
            Vec3.fromRGB24(0xD4C78A),
            Vec3.fromRGB24(0xE0EA8C),
            Vec3.fromRGB24(0x9A946E),
            Vec3.fromRGB24(0x8BC761),
            Vec3.fromRGB24(0x5A8553),
            Vec3.fromRGB24(0x626F60),
            Vec3.fromRGB24(0x536383),
            Vec3.fromRGB24(0x8494D2),
            Vec3.fromRGB24(0xA7DBD7),
            Vec3.fromRGB24(0x8A8CAB),
            Vec3.fromRGB24(0x9A77A7),
            Vec3.fromRGB24(0x4A3A54)
    };

    public static int getColor(int p_55607_) {
        Vec3 vec3 = COLORS[p_55607_];
        return Mth.color((float)vec3.x(), (float)vec3.y(), (float)vec3.z());
    }

    public SucculentBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, 0));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return state.is(BlockTags.DIRT) || state.is(BlockTags.SAND);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_57799_) {
        p_57799_.add(TYPE, COLOR);
    }

    public VoxelShape getShape(BlockState p_153342_, BlockGetter p_153343_, BlockPos p_153344_, CollisionContext p_153345_) {
        return SHAPE;
    }

}
