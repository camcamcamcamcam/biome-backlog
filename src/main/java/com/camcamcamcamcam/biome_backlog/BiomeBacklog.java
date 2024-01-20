package com.camcamcamcamcam.biome_backlog;

import com.camcamcamcamcam.biome_backlog.client.ClientRegistrar;
import com.camcamcamcamcam.biome_backlog.register.*;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.world.chunk.RegisterTicketControllersEvent;
import net.neoforged.neoforge.common.world.chunk.TicketController;
import net.neoforged.neoforge.common.world.chunk.TicketHelper;
import net.neoforged.neoforge.common.world.chunk.TicketSet;
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


	public static final TicketController deathRequestController = new TicketController(prefix("death_request"), BiomeBacklog::removeAllChunkTickets);

	public BiomeBacklog(IEventBus modEventBus) {
		ModBlocks.BLOCKS.register(modEventBus);

		ModItems.ITEMS.register(modEventBus);
		ModEntities.ENTITIES.register(modEventBus);
		ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
		ModTreeDecoratorTypes.DECORATOR_TYPE.register(modEventBus);
		ModTrunkPlacerTypes.TRUNK_PLACER_TYPE.register(modEventBus);
		ModFoliagePlacerTypes.FOLIAGE_PLACER_TYPE.register(modEventBus);
		ModBiomeModifiers.BIOME_MODIFIER_SERIALIZERS.register(modEventBus);
		ModParticles.PARTICLES.register(modEventBus);
		ModCapabilitys.ATTACHMENT_TYPES.register(modEventBus);

		ModRecipeTypes.RECIPE_TYPES.register(modEventBus);
		ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);

		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::registerTicket);
		if (FMLEnvironment.dist == Dist.CLIENT) {
			modEventBus.addListener(ClientRegistrar::setup);
		}
	}

	private static void removeAllChunkTickets(ServerLevel serverLevel, TicketHelper ticketHelper) {
		int i = 0;
		for (Map.Entry<UUID, TicketSet> entry : ticketHelper.getEntityTickets().entrySet()) {
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


	private void registerTicket(final RegisterTicketControllersEvent event) {
		event.register(deathRequestController);
	}


	private void commonSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			WoodType.register(ModBlocks.PALM_TYPE);
			((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(BuiltInRegistries.BLOCK.getKey(ModBlocks.SUCCULENT.get()), ModBlocks.POTTED_SUCCULENT);
			ModBlocks.flamableInit();
			ModItems.registerCompostableItem();
		});

		ModEntities.spawnPlacementSetup();
		event.enqueueWork(this::afterCommonSetup);
    }

    private void afterCommonSetup() {
		Map<Block, Block> stripMap = new HashMap<>(AxeItem.STRIPPABLES);
		stripMap.put(ModBlocks.PALM_LOG.get(), ModBlocks.STRIPPED_PALM_LOG.get());
		stripMap.put(ModBlocks.PALM_WOOD.get(), ModBlocks.STRIPPED_PALM_WOOD.get());
		AxeItem.STRIPPABLES = stripMap;
	}

	public TicketController getDeathRequestController() {
		return deathRequestController;
	}
}
