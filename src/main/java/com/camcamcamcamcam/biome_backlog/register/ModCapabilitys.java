package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.capability.DeathTrackCapability;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModCapabilitys {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, BiomeBacklog.MODID);

    public static final Supplier<AttachmentType<DeathTrackCapability>> DEATH_TRACK = ATTACHMENT_TYPES.register(
            "death_track", () -> AttachmentType.serializable(DeathTrackCapability::new).build());
}