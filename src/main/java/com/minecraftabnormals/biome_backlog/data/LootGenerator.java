package com.minecraftabnormals.biome_backlog.data;

import com.google.common.collect.ImmutableList;
import com.minecraftabnormals.biome_backlog.data.loot.BlockLootTables;
import com.minecraftabnormals.biome_backlog.data.loot.EntityLootTables;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootGenerator extends LootTableProvider {
	public LootGenerator(DataGenerator generator) {
		super(generator);
	}


	private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables = ImmutableList.of(
			Pair.of(BlockLootTables::new, LootContextParamSets.BLOCK),
			Pair.of(EntityLootTables::new, LootContextParamSets.ENTITY)
	);

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
		map.forEach((id, builder) -> LootTables.validate(validationtracker, id, builder));
	}

	@Override
	public List<com.mojang.datafixers.util.Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
		return tables;
	}

	@Override
	public String getName() {
		return "BiomeVoteLosers loot tables";
	}
}