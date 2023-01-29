package com.minecraftabnormals.biome_backlog.client.render;

import com.google.common.collect.ImmutableMap;
import com.minecraftabnormals.biome_backlog.BiomeBacklog;
import com.minecraftabnormals.biome_backlog.world.level.entity.ModBoat;
import com.minecraftabnormals.biome_backlog.world.level.entity.ModBoatType;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

import java.util.Map;
import java.util.stream.Stream;

public class ModBoatRenderer extends BoatRenderer {
	private final Map<ModBoat.BoatType, Pair<ResourceLocation, BoatModel>> boatResources;

	public ModBoatRenderer(EntityRendererProvider.Context p_234563_, boolean p_234564_) {
		super(p_234563_, p_234564_);
		this.boatResources = Stream.of(ModBoat.BoatType.values()).collect(ImmutableMap.toImmutableMap((p_173938_) -> {
			return p_173938_;
		}, (p_234575_) -> {
			return Pair.of(new ResourceLocation(getTextureLocation(p_234575_, p_234564_)), this.createBoatModel(p_234563_, p_234575_, p_234564_));
		}));
	}

	private static String getTextureLocation(ModBoat.BoatType p_234566_, boolean p_234567_) {
		return p_234567_ ? BiomeBacklog.MODID + ":textures/entity/chest_boat/" + p_234566_.getType() + ".png" : BiomeBacklog.MODID + ":textures/entity/boat/" + p_234566_.getType() + ".png";
	}

	private BoatModel createBoatModel(EntityRendererProvider.Context p_234569_, ModBoat.BoatType p_234570_, boolean p_234571_) {
		ModelLayerLocation modellayerlocation = p_234571_ ? createChestBoatModelName(p_234570_) : createBoatModelName(p_234570_);
		return new BoatModel(p_234569_.bakeLayer(modellayerlocation), p_234571_);
	}

	public static ModelLayerLocation createBoatModelName(ModBoat.BoatType p_171290_) {
		return createLocation("boat/" + p_171290_.getType(), "main");
	}

	public static ModelLayerLocation createChestBoatModelName(ModBoat.BoatType p_233551_) {
		return createLocation("chest_boat/" + p_233551_.getType(), "main");
	}

	private static ModelLayerLocation createLocation(String p_171301_, String p_171302_) {
		return new ModelLayerLocation(new ResourceLocation(BiomeBacklog.MODID, p_171301_), p_171302_);
	}

	public Pair<ResourceLocation, BoatModel> getModelWithLocation(Boat boat) {
		if (boat instanceof ModBoatType boatType) {
			return this.boatResources.get(boatType.getModBoatType());
		}
		return super.getModelWithLocation(boat);
	}
}
