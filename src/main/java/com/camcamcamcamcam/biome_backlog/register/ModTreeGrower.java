package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.world.gen.feature.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrower {
    public static final TreeGrower PALM_COCONUT = new TreeGrower(
            BiomeBacklog.prefix("palm_coconuts").toString(),
            0.1F,
            Optional.empty(),
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.PALM_COCONUTS),
            Optional.empty(),
            Optional.empty(),
            Optional.empty()
    );
    public static final TreeGrower PALM_DATE = new TreeGrower(
            BiomeBacklog.prefix("palm_date").toString(),
            0.1F,
            Optional.empty(),
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.PALM_DATE),
            Optional.empty(),
            Optional.empty(),
            Optional.empty()
    );
}
