package com.camcamcamcamcam.biome_backlog;

import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BiomeBacklog.MODID)
public class CommonEvent {
    @SubscribeEvent
    public static void burnTimeEvent(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().is(ModBlocks.COCONUT_SAPLING.get().asItem()) || event.getItemStack().is(ModBlocks.DATE_SAPLING.get().asItem())) {
            event.setBurnTime(200);
        }
    }
}
