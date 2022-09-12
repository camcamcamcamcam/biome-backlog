package com.minecraftabnormals.biome_vote_losers.world.level.item;

import org.jetbrains.annotations.Nullable;

import com.minecraftabnormals.biome_vote_losers.register.ModEntities;
import com.minecraftabnormals.biome_vote_losers.world.level.entity.CalcitePowderReaction;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class VinegarBottleItem extends Item {
    public VinegarBottleItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext context) {
        final var level = context.getLevel();
        final var pos   = context.getClickedPos();
        final var block = level.getBlockState(pos);

        if (!block.is(Blocks.CALCITE)) {
            return InteractionResult.PASS;
        }

        /* Only allow using vinegar on the top face of the block. */
        if (context.getClickedFace() != Direction.UP) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {
            final var powderEntity = new CalcitePowderReaction(ModEntities.CALCITE_POWDER.get(), level);
            powderEntity.setPos(Vec3.upFromBottomCenterOf(pos, 1.0));

            level.addFreshEntity(powderEntity);
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
