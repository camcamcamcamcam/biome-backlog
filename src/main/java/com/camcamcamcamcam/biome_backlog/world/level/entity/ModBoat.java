package com.camcamcamcamcam.biome_backlog.world.level.entity;

import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import com.camcamcamcamcam.biome_backlog.register.ModEntities;
import com.camcamcamcamcam.biome_backlog.register.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ModBoat extends Boat implements ModBoatType {
	private static final EntityDataAccessor<String> DATA_ID_TYPE = SynchedEntityData.defineId(ModBoat.class, EntityDataSerializers.STRING);

	public ModBoat(EntityType<? extends ModBoat> p_38290_, Level p_38291_) {
		super(p_38290_, p_38291_);
	}

	public ModBoat(Level p_219872_, double p_219873_, double p_219874_, double p_219875_) {
		this(ModEntities.MOD_BOAT.get(), p_219872_);
		this.setPos(p_219873_, p_219874_, p_219875_);
		this.xo = p_219873_;
		this.yo = p_219874_;
		this.zo = p_219875_;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ID_TYPE, BoatType.PALM.type);
	}

	public Item getDropItem() {
		Item item;
		switch (this.getModBoatType()) {
			case PALM:
				item = ModItems.PALM_BOAT.get();
				break;
			default:
				item = Items.OAK_BOAT;
		}

		return item;
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag p_38359_) {
		p_38359_.putString("Type", this.getModBoatType().type);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag p_38338_) {
		if (p_38338_.contains("Type", 8)) {
			this.setModBoatType(BoatType.byType(p_38338_.getString("Type")));
		}
	}

	public void setModBoatType(ModBoat.BoatType p_28929_) {
		this.entityData.set(DATA_ID_TYPE, p_28929_.type);
	}

	public ModBoat.BoatType getModBoatType() {
		return ModBoat.BoatType.byType(this.entityData.get(DATA_ID_TYPE));
	}

	public static enum BoatType {
		PALM("palm", ModBlocks.PALM_PLANKS.get());

		final String type;
		final Block block;

		private BoatType(String p_28967_, Block p_28968_) {
			this.type = p_28967_;
			this.block = p_28968_;
		}

		public Block getBlock() {
			return this.block;
		}

		public String getType() {
			return type;
		}

		static ModBoat.BoatType byType(String p_28977_) {
			for (ModBoat.BoatType type : values()) {
				if (type.type.equals(p_28977_)) {
					return type;
				}
			}

			return PALM;
		}
	}
}
