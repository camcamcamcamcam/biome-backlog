package com.minecraftabnormals.biome_vote_losers.register;

import com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers;
import com.minecraftabnormals.biome_vote_losers.world.BiomeVoteModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBiomeModifiers {
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, BiomeVoteLosers.MODID);

	public static final RegistryObject<Codec<BiomeVoteModifier>> BIOME_VOTE_SPAWN = BIOME_MODIFIER_SERIALIZERS.register("biome_vote_entity_modifier", () -> Codec.unit(BiomeVoteModifier.INSTANCE));
}