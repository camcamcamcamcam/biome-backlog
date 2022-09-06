package com.minecraftabnormals.biome_vote_losers.client.render;

import com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers;
import com.minecraftabnormals.biome_vote_losers.client.ModModelLayers;
import com.minecraftabnormals.biome_vote_losers.client.model.TumbleweedModel;
import com.minecraftabnormals.biome_vote_losers.world.level.entity.Tumbleweed;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TumbleweedRender<T extends Tumbleweed> extends MobRenderer<T, TumbleweedModel<T>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(BiomeVoteLosers.MODID, "textures/entity/tumbleweed.png");

    public TumbleweedRender(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new TumbleweedModel<>(p_174304_.bakeLayer(ModModelLayers.TUMBLEWEED)), 0.2F);
    }


    @Override
    public ResourceLocation getTextureLocation(T p_114482_) {
        return TEXTURE;
    }
}
