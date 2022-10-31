package com.minecraftabnormals.biome_vote_losers.world.level.item;

import com.minecraftabnormals.biome_vote_losers.register.ModEntities;
import com.minecraftabnormals.biome_vote_losers.world.level.entity.CalcitePowderReaction;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class VinegarBottleItem extends Item {
    public VinegarBottleItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext context) {
        final var level = context.getLevel();
        final var pos   = context.getClickedPos();
        final var block = level.getBlockState(pos);

        if (!block.is(Blocks.CALCITE) && !block.is(BlockTags.WOOL) && !block.is(BlockTags.WOOL_CARPETS)) {
            return InteractionResult.PASS;
        }

        /* Only allow using vinegar on the top face of the block. */
        if (context.getClickedFace() != Direction.UP) {
            return InteractionResult.PASS;
        }

        /* Not Affect The white wool*/
        if (block.is(Blocks.WHITE_WOOL) || block.is(Blocks.WHITE_CARPET)) {
            return InteractionResult.PASS;
        }

        level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS, 1.0F, 1.0F);

        if (block.is(Blocks.CALCITE)) {
            if (!level.isClientSide) {
                final var powderEntity = new CalcitePowderReaction(ModEntities.CALCITE_POWDER.get(), level);
                powderEntity.setPos(Vec3.upFromBottomCenterOf(pos, 1.0));

                level.addFreshEntity(powderEntity);
            }
        }

        if (block.is(BlockTags.WOOL)) {
            level.setBlock(pos, Blocks.WHITE_WOOL.defaultBlockState(), 2);
        }

        if (block.is(BlockTags.WOOL_CARPETS)) {
            level.setBlock(pos, Blocks.WHITE_CARPET.defaultBlockState(), 2);
        }

        final @Nullable var player = context.getPlayer();

        if (player != null && !player.getAbilities().instabuild) {
            final var item = context.getItemInHand();
            item.shrink(1);

            final var emptyBottle = new ItemStack(Items.GLASS_BOTTLE);

            if (item.isEmpty()) {
                player.setItemInHand(context.getHand(), emptyBottle);
            } else {
                player.addItem(emptyBottle);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
