package com.camcamcamcamcam.biome_backlog.world;

import com.camcamcamcamcam.biome_backlog.register.ModBiomeModifiers;
import com.camcamcamcamcam.biome_backlog.register.ModEntities;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public class BiomeVoteSpawnModifier implements BiomeModifier {
	public static final BiomeVoteSpawnModifier INSTANCE = new BiomeVoteSpawnModifier();


	@Override
	public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
		if (phase == Phase.ADD) {
			if (biome.is(Biomes.SAVANNA) || biome.is(Biomes.SAVANNA_PLATEAU) || biome.is(Biomes.WINDSWEPT_SAVANNA)) {
				builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(ModEntities.OSTRICH.get(), 8, 2, 3));
			}
			if (biome.is(Biomes.DESERT)) {
				builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(ModEntities.MEERKAT.get(), 8, 5, 6));
			}
			if (biome.is(Biomes.BADLANDS) || biome.is(Biomes.ERODED_BADLANDS) || biome.is(Biomes.WOODED_BADLANDS)) {
				builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(ModEntities.VULTURE.get(), 8, 1, 2));
			}
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return ModBiomeModifiers.BIOME_VOTE_SPAWN.get();
	}
}