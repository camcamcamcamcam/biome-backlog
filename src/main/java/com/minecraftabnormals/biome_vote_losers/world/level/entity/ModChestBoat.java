package com.minecraftabnormals.biome_vote_losers.world.level.entity;

import com.minecraftabnormals.biome_vote_losers.register.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.level.Level;

public class ModChestBoat extends ChestBoat implements ModBoatType {
	private static final EntityDataAccessor<String> DATA_ID_TYPE = SynchedEntityData.defineId(ModChestBoat.class, EntityDataSerializers.STRING);

	public ModChestBoat(EntityType<? extends ModChestBoat> p_38290_, Level p_38291_) {
		super(p_38290_, p_38291_);
	}

	public ModChestBoat(Level p_219872_, double p_219873_, double p_219874_, double p_219875_) {
		this(ModEntities.MOD_CHEST_BOAT.get(), p_219872_);
		this.setPos(p_219873_, p_219874_, p_219875_);
		this.xo = p_219873_;
		this.yo = p_219874_;
		this.zo = p_219875_;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ID_TYPE, ModBoat.BoatType.PALM.type);
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag p_38359_) {
		p_38359_.putString("Type", this.getModBoatType().type);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag p_38338_) {
		if (p_38338_.contains("Type", 8)) {
			this.setModBoatType(ModBoat.BoatType.byType(p_38338_.getString("Type")));
		}
	}

	public void setModBoatType(ModBoat.BoatType p_28929_) {
		this.entityData.set(DATA_ID_TYPE, p_28929_.type);
	}

	public ModBoat.BoatType getModBoatType() {
		return ModBoat.BoatType.byType(this.entityData.get(DATA_ID_TYPE));
	}
}
