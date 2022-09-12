package com.minecraftabnormals.biome_vote_losers.register;

import com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers;
import com.minecraftabnormals.biome_vote_losers.world.level.entity.CalcitePowderReaction;
import com.minecraftabnormals.biome_vote_losers.world.level.entity.Meerkat;
import com.minecraftabnormals.biome_vote_losers.world.level.entity.Ostrich;
import com.minecraftabnormals.biome_vote_losers.world.level.entity.Tumbleweed;
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

@Mod.EventBusSubscriber(modid = BiomeVoteLosers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BiomeVoteLosers.MODID);

	public static final RegistryObject<EntityType<Meerkat>> MEERKAT = ENTITIES.register("meerkat", () -> EntityType.Builder.of(Meerkat::new, MobCategory.CREATURE).sized(0.6F, 0.7F).clientTrackingRange(8).build(prefix("meerkat")));
	public static final RegistryObject<EntityType<Ostrich>> OSTRICH = ENTITIES.register("ostrich", () -> EntityType.Builder.of(Ostrich::new, MobCategory.CREATURE).sized(1.0F, 2.5F).clientTrackingRange(8).build(prefix("ostrich")));
	public static final RegistryObject<EntityType<Tumbleweed>> TUMBLEWEED = ENTITIES.register("tumbleweed", () -> EntityType.Builder.of(Tumbleweed::new, MobCategory.AMBIENT).sized(0.7F, 0.7F).clientTrackingRange(8).build(prefix("tumbleweed")));
	public static final RegistryObject<EntityType<CalcitePowderReaction>> CALCITE_POWDER = ENTITIES.register("calcite_powder_reaction", () -> EntityType.Builder.of(CalcitePowderReaction::new, MobCategory.MISC).sized(1.0F, 0.2F).clientTrackingRange(6).build(prefix("calcite_powder_reaction")));

	private static String prefix(String path) {
		return BiomeVoteLosers.MODID + "." + path;
	}

	public static void spawnPlacementSetup() {
		SpawnPlacements.register(MEERKAT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
	}

	@SubscribeEvent
	public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
		event.put(MEERKAT.get(), Meerkat.createAttributes().build());
		event.put(OSTRICH.get(), Ostrich.createAttributes().build());
		event.put(TUMBLEWEED.get(), Tumbleweed.createAttributes().build());
	}
}
