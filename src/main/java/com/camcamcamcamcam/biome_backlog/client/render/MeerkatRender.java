package com.camcamcamcamcam.biome_backlog.client.render;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.client.ModModelLayers;
import com.camcamcamcamcam.biome_backlog.client.model.MeerkatModel;
import com.camcamcamcamcam.biome_backlog.world.level.entity.Meerkat;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MeerkatRender<T extends Meerkat> extends MobRenderer<T, MeerkatModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(BiomeBacklog.MODID, "textures/entity/meerkat.png");

	public MeerkatRender(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new MeerkatModel<>(p_173952_.bakeLayer(ModModelLayers.MEERKAT)), 0.35F);
	}


	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return TEXTURE;
	}
}