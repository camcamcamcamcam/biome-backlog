package com.minecraftabnormals.biome_vote_losers.world;

import com.minecraftabnormals.biome_vote_losers.register.ModBiomeModifiers;
import com.minecraftabnormals.biome_vote_losers.register.ModEntities;
import com.minecraftabnormals.biome_vote_losers.world.gen.feature.ModPlacements;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public class BiomeVoteModifier implements BiomeModifier {
	public static final BiomeVoteModifier INSTANCE = new BiomeVoteModifier();


	@Override
	public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
		if (phase == Phase.ADD) {
			if (biome.is(Biomes.SAVANNA) || biome.is(Biomes.SAVANNA_PLATEAU) || biome.is(Biomes.WINDSWEPT_SAVANNA)) {
				builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(ModEntities.OSTRICH.get(), 8, 2, 3));
			}
			if (biome.is(Biomes.DESERT)) {
				builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(ModEntities.MEERKAT.get(), 8, 4, 6));
			}

			if (biome.is(Biomes.BADLANDS)) {
				builder.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.PATCH_PEAR_CACTUS);
				builder.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.PATCH_TUMBLEWEED);
			}
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return ModBiomeModifiers.BIOME_VOTE_SPAWN.get();
	}
}