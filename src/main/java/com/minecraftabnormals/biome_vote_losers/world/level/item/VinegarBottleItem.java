package com.minecraftabnormals.biome_vote_losers.world.level.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class VinegarBottleItem extends Item {
    public VinegarBottleItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext p_41427_) {

        return InteractionResult.PASS;
    }
}
