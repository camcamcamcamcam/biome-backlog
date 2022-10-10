package com.minecraftabnormals.biome_vote_losers.data.loot;

import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashSet;
import java.util.Set;

public class EntityLootTables extends EntityLoot {

    private final Set<EntityType<?>> knownEntities = new HashSet<>();

    @Override
    protected void add(EntityType<?> entityType, LootTable.Builder builder) {
        this.add(entityType.getDefaultLootTable(), builder);
        knownEntities.add(entityType);
    }

    @Override
    protected void addTables() {
        //this.add(ModEntities.OSTRICH)
    }

    @Override
    protected Iterable<EntityType<?>> getKnownEntities() {
        return knownEntities;
    }

}
