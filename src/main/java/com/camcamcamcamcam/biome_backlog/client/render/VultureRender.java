package com.camcamcamcamcam.biome_backlog.client.render;

import com.camcamcamcamcam.biome_backlog.world.level.entity.Vulture;
import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.client.ModModelLayers;
import com.camcamcamcamcam.biome_backlog.client.model.VultureModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VultureRender<T extends Vulture> extends MobRenderer<T, VultureModel<T>> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(BiomeBacklog.MODID, "textures/entity/vulture.png");

	public VultureRender(EntityRendererProvider.Context p_174304_) {
		super(p_174304_, new VultureModel<>(p_174304_.bakeLayer(ModModelLayers.VULTURE)), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(T p_114482_) {
		return TEXTURE;
	}

	@Override
	protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
		super.scale(p_115314_, p_115315_, p_115316_);
		p_115315_.scale(p_115314_.getScale(), p_115314_.getScale(), p_115314_.getScale());
	}
}
