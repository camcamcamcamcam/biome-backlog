package com.camcamcamcamcam.biome_backlog;

import com.camcamcamcamcam.biome_backlog.capability.DeathTrackCapability;
import com.camcamcamcamcam.biome_backlog.client.ClientRegistrar;
import com.camcamcamcamcam.biome_backlog.register.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Mod(BiomeBacklog.MODID)
public class BiomeBacklog {
	// Define mod id in a common place for everything to reference
	public static final String MODID = "biome_backlog";

	private static final Logger LOGGER = LogUtils.getLogger();

	public static Capability<DeathTrackCapability> TRUSTED_VULTURE_CAP = CapabilityManager.get(new CapabilityToken<>() {
	});

	public BiomeBacklog() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModBlocks.BLOCKS.register(modEventBus);

		ModItems.ITEMS.register(modEventBus);
		ModEntities.ENTITIES.register(modEventBus);
		ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
		ModTreeDecoratorTypes.DECORATOR_TYPE.register(modEventBus);
		ModFoliagePlacerTypes.FOLIAGE_PLACER_TYPE.register(modEventBus);
		ModBiomeModifiers.BIOME_MODIFIER_SERIALIZERS.register(modEventBus);
		ModParticles.PARTICLES.register(modEventBus);

		ModRecipeTypes.RECIPE_TYPES.register(modEventBus);
		ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);

		modEventBus.addListener(this::commonSetup);
		MinecraftForge.EVENT_BUS.register(this);
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientRegistrar::setup));
	}

	public void serverInit() {
		ForgeChunkManager.setForcedChunkLoadingCallback(BiomeBacklog.MODID, this::removeAllChunkTickets);
	}

	private void removeAllChunkTickets(ServerLevel serverLevel, ForgeChunkManager.TicketHelper ticketHelper) {
		int i = 0;
		for (Map.Entry<UUID, Pair<LongSet, LongSet>> entry : ticketHelper.getEntityTickets().entrySet()) {
			ticketHelper.removeAllTickets(entry.getKey());
			i++;
		}
		BiomeBacklog.LOGGER.debug("Removed " + i + " chunkloading tickets");
	}


	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
	}

	public static String prefixOnString(String name) {
		return MODID + ":" + name.toLowerCase(Locale.ROOT);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			WoodType.register(ModBlocks.PALM_TYPE);
			((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.SUCCULENT.getId(), ModBlocks.POTTED_SUCCULENT);
			ModTrunkPlacerTypes.init();
			ModBlocks.flamableInit();
		});

		ModEntities.spawnPlacementSetup();
		event.enqueueWork(this::serverInit);
		event.enqueueWork(this::afterCommonSetup);
    }

    private void afterCommonSetup() {
		Map<Block, Block> stripMap = new HashMap<>(AxeItem.STRIPPABLES);
		stripMap.put(ModBlocks.PALM_LOG.get(), ModBlocks.STRIPPED_PALM_LOG.get());
		stripMap.put(ModBlocks.PALM_WOOD.get(), ModBlocks.STRIPPED_PALM_WOOD.get());
		AxeItem.STRIPPABLES = stripMap;
	}

}
