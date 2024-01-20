package com.camcamcamcamcam.biome_backlog.world.server;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.UUID;

public class DeathTrackRequest {
	private String entityType;
	private UUID vultureUUID;
	private UUID ownerUUID;

	private BlockPos chunkPosition;

	public DeathTrackRequest(UUID vultureUUID, String entityType, UUID ownerUUID, BlockPos chunkPosition) {
		this.vultureUUID = vultureUUID;
		this.entityType = entityType;
		this.chunkPosition = chunkPosition;
		this.ownerUUID = ownerUUID;
	}

	public UUID getVultureUUID() {
		return vultureUUID;
	}

	public String getEntityTypeLoc() {
		return this.entityType;
	}

	public EntityType getEntityType() {
		return BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation(this.entityType));
	}

	public UUID getOwnerUUID() {
		return ownerUUID;
	}

	public BlockPos getChunkPosition() {
		return chunkPosition;
	}
}