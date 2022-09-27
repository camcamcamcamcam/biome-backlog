package com.minecraftabnormals.biome_vote_losers.register;

import com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers;
import com.minecraftabnormals.biome_vote_losers.world.level.block.entity.BaobabBlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BiomeVoteLosers.MODID);

    public static final RegistryObject<BlockEntityType<BaobabBlockEntity>> BAOBAB_FLOWER = BLOCK_ENTITIES.register("baobab_flower", () -> BlockEntityType.Builder.of(BaobabBlockEntity::new, ModBlocks.BAOBAB_FLOWER.get()).build(null));
}
