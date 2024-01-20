package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.world.level.block.entity.BaobabBlockEntity;
import com.camcamcamcamcam.biome_backlog.world.level.block.entity.BurrowBlockEntity;
import com.camcamcamcamcam.biome_backlog.world.level.block.entity.ModSignBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BiomeBacklog.MODID);

    public static final Supplier<BlockEntityType<BaobabBlockEntity>> BAOBAB_FLOWER = BLOCK_ENTITIES.register("baobab_flower", () -> BlockEntityType.Builder.of(BaobabBlockEntity::new, ModBlocks.BAOBAB_FLOWER.get()).build(null));
    public static final Supplier<BlockEntityType<BurrowBlockEntity>> BURROW = BLOCK_ENTITIES.register("burrow", () -> BlockEntityType.Builder.of(BurrowBlockEntity::new, ModBlocks.BURROW.get()).build(null));
    public static final Supplier<BlockEntityType<ModSignBlockEntity>> MOD_SIGN = BLOCK_ENTITIES.register("mod_sign", () -> BlockEntityType.Builder.of(ModSignBlockEntity::new, ModBlocks.PALM_SIGN.get(), ModBlocks.PALM_WALL_SIGN.get()).build(null));

}
