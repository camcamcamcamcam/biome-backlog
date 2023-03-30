package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.world.level.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = BiomeBacklog.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BiomeBacklog.MODID);

	public static final RegistryObject<EntityType<Meerkat>> MEERKAT = ENTITIES.register("meerkat", () -> EntityType.Builder.of(Meerkat::new, MobCategory.CREATURE).sized(0.6F, 0.7F).clientTrackingRange(8).build(prefix("meerkat")));
	public static final RegistryObject<EntityType<Ostrich>> OSTRICH = ENTITIES.register("ostrich", () -> EntityType.Builder.of(Ostrich::new, MobCategory.CREATURE).sized(0.9F, 2.5F).clientTrackingRange(8).build(prefix("ostrich")));
	public static final RegistryObject<EntityType<Vulture>> VULTURE = ENTITIES.register("vulture", () -> EntityType.Builder.of(Vulture::new, MobCategory.CREATURE).sized(0.85F, 0.85F).clientTrackingRange(8).build(prefix("vulture")));
	public static final RegistryObject<EntityType<Tumbleweed>> TUMBLEWEED = ENTITIES.register("tumbleweed", () -> EntityType.Builder.of(Tumbleweed::new, MobCategory.MISC).sized(0.7F, 0.8F).clientTrackingRange(8).build(prefix("tumbleweed")));
	public static final RegistryObject<EntityType<CalcitePowderReaction>> CALCITE_POWDER = ENTITIES.register("calcite_powder_reaction", () -> EntityType.Builder.of(CalcitePowderReaction::new, MobCategory.MISC).sized(1.0F, 0.2F).clientTrackingRange(6).build(prefix("calcite_powder_reaction")));
	public static final RegistryObject<EntityType<CoconutProjectile>> COCONUT = ENTITIES.register("coconut", () -> EntityType.Builder.<CoconutProjectile>of(CoconutProjectile::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(prefix("coconut")));
	public static final RegistryObject<EntityType<ModBoat>> MOD_BOAT = ENTITIES.register("mod_boat", () -> EntityType.Builder.<ModBoat>of(ModBoat::new, MobCategory.MISC).sized(0.25F, 0.25F).sized(1.375F, 0.5625F).clientTrackingRange(10).build(prefix("mod_boat")));
	public static final RegistryObject<EntityType<ModChestBoat>> MOD_CHEST_BOAT = ENTITIES.register("mod_chest_boat", () -> EntityType.Builder.<ModChestBoat>of(ModChestBoat::new, MobCategory.MISC).sized(0.25F, 0.25F).sized(1.375F, 0.5625F).clientTrackingRange(10).build(prefix("mod_chest_boat")));


	private static String prefix(String path) {
		return BiomeBacklog.MODID + "." + path;
	}

	public static void spawnPlacementSetup() {
		SpawnPlacements.register(MEERKAT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Meerkat::checkMeerkatSpawnRules);
		SpawnPlacements.register(OSTRICH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
	}

	@SubscribeEvent
	public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
		event.put(MEERKAT.get(), Meerkat.createAttributes().build());
		event.put(OSTRICH.get(), Ostrich.createAttributes().build());
		event.put(VULTURE.get(), Vulture.createAttributes().build());
	}
}
