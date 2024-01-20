package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.world.BiomeVoteSpawnModifier;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModBiomeModifiers {
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, BiomeBacklog.MODID);

	public static final Supplier<Codec<BiomeVoteSpawnModifier>> BIOME_VOTE_SPAWN = BIOME_MODIFIER_SERIALIZERS.register("biome_vote_entity_modifier", () -> Codec.unit(BiomeVoteSpawnModifier.INSTANCE));

}