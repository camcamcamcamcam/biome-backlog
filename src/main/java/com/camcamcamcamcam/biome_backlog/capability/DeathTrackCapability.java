package com.camcamcamcamcam.biome_backlog.capability;

import com.camcamcamcamcam.biome_backlog.world.server.DeathTrackRequest;
import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeathTrackCapability implements ICapabilityProvider, INBTSerializable<CompoundTag> {
	private List<DeathTrackRequest> deathTrackList = new ArrayList<>();

	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
		return capability == BiomeBacklog.TRUSTED_VULTURE_CAP ? LazyOptional.of(() -> this).cast() : LazyOptional.empty();
	}

	public CompoundTag serializeNBT() {
		CompoundTag compound = new CompoundTag();

		if (!this.deathTrackList.isEmpty()) {
			ListTag listTag = new ListTag();
			for (DeathTrackRequest request : deathTrackList) {
				CompoundTag tag = new CompoundTag();
				tag.putUUID("VultureUUID", request.getVultureUUID());
				tag.putString("EntityType", request.getEntityTypeLoc());
				tag.putUUID("OwnerUUID", request.getOwnerUUID());
				tag.putLong("Timestamp", request.getTimestamp());
				tag.putInt("X", request.getChunkPosition().getX());
				tag.putInt("Y", request.getChunkPosition().getY());
				tag.putInt("Z", request.getChunkPosition().getZ());
				listTag.add(tag);
			}
			compound.put("VultureList", listTag);
		}


		return compound;
	}

	public void deserializeNBT(CompoundTag nbt) {
		if (nbt.contains("VultureList")) {
			ListTag listtag = nbt.getList("VultureList", 10);
			for (int i = 0; i < listtag.size(); ++i) {
				CompoundTag innerTag = listtag.getCompound(i);
				deathTrackList.add(new DeathTrackRequest(innerTag.getUUID("VultureUUID"), innerTag.getString("EntityType"), innerTag.getUUID("OwnerUUID"), new BlockPos(innerTag.getInt("X"), innerTag.getInt("Y"), innerTag.getInt("Z")), innerTag.getLong("Timestamp")));
			}
		}
	}


	public void addDeathTrackRequest(DeathTrackRequest request) {
		this.deathTrackList.add(request);
	}

	public void removeDeathTrackRequestAll(List<DeathTrackRequest> request) {
		this.deathTrackList.removeAll(request);
	}

	public void removeDeathTrackRequest(DeathTrackRequest request) {
		this.deathTrackList.remove(request);
	}

	public List<DeathTrackRequest> getDeathTrackRequestsFor() {
		List<DeathTrackRequest> list = new ArrayList<>();
		for (DeathTrackRequest request : this.deathTrackList) {
			list.add(request);
			Collections.shuffle(list);
		}
		return list;
	}
}