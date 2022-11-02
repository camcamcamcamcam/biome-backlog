package com.minecraftabnormals.biome_vote_losers;

import com.minecraftabnormals.biome_vote_losers.capability.DeathTrackCapability;
import com.minecraftabnormals.biome_vote_losers.world.level.entity.Vulture;
import com.minecraftabnormals.biome_vote_losers.world.server.DeathTrackRequest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = BiomeVoteLosers.MODID)
public class CommonEvents {

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.register(DeathTrackCapability.class);
	}

	@SubscribeEvent
	public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Level> event) {
		event.addCapability(new ResourceLocation(BiomeVoteLosers.MODID, "death_track"), new DeathTrackCapability());
	}


	@SubscribeEvent
	public static void livingDeathEvent(LivingDeathEvent event) {
		LivingEntity livingEntity = event.getEntity();

		if (livingEntity instanceof Player) {
			if (livingEntity.level instanceof ServerLevel serverLevel) {

				livingEntity.level.getCapability(BiomeVoteLosers.TRUSTED_VULTURE_CAP).ifPresent(deathTrackCapability -> {
					for (DeathTrackRequest request : deathTrackCapability.getDeathTrackRequestsFor()) {
						loadChunksAround(serverLevel, request.getVultureUUID(), request.getChunkPosition(), true);
						Entity entityFromChunk = serverLevel.getEntity(request.getVultureUUID());
						if (entityFromChunk != null) {
							entityFromChunk.teleportToWithTicket(livingEntity.getX() + 0.5F, 110, livingEntity.getZ() + 0.5F);
							if (entityFromChunk instanceof Vulture) {
								((Vulture) entityFromChunk).setCircle(false);
							}
						}
						loadChunksAround(serverLevel, request.getVultureUUID(), request.getChunkPosition(), false);
						//deathTrackCapability.removeDeathTrackRequestAll(deathTrackCapability.getDeathTrackRequestsFor());
					}
				});
			}
		}
	}

	private static void loadChunksAround(ServerLevel serverLevel, UUID ticket, BlockPos center, boolean load) {
		ChunkPos chunkPos = new ChunkPos(center);
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				ForgeChunkManager.forceChunk(serverLevel, BiomeVoteLosers.MODID, ticket, chunkPos.x + i, chunkPos.z + j, load, true);
			}
		}
	}

	@SubscribeEvent
	public static void onEntityLeaveWorld(EntityLeaveLevelEvent event) {
		if (event.getEntity() instanceof LivingEntity living) {
			if (!event.getLevel().isClientSide && living.isAlive() && living instanceof Vulture && ((Vulture) living).isTame() && ((Vulture) living).hasCircle()) {
				UUID ownerUUID = ((Vulture) living).getOwnerUUID();
				event.getLevel().getCapability(BiomeVoteLosers.TRUSTED_VULTURE_CAP).ifPresent(cap -> {
					DeathTrackRequest request = new DeathTrackRequest(living.getUUID(), Registry.ENTITY_TYPE.getKey(event.getEntity().getType()).toString(), ownerUUID, living.blockPosition(), event.getEntity().level.dayTime());
					cap.addDeathTrackRequest(request);
				});
			}
		}
	}

}
