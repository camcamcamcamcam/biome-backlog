package com.camcamcamcamcam.biome_backlog.client.render.item;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.register.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PalmShieldBWLR extends BlockEntityWithoutLevelRenderer {
    private ShieldModel shieldModel;

    public PalmShieldBWLR() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.shieldModel = new ShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.SHIELD));
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pOverlay) {
        if (pStack.is(ModItems.PALM_SHIELD.get())) {
            pPoseStack.pushPose();
            pPoseStack.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(pBuffer, this.shieldModel.renderType(new ResourceLocation(BiomeBacklog.MODID, "textures/entity/shield/palm_shield.png")), true, pStack.hasFoil());
            this.shieldModel.handle().render(pPoseStack, vertexconsumer, pPackedLight, pOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            this.shieldModel.plate().render(pPoseStack, vertexconsumer, pPackedLight, pOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

            pPoseStack.popPose();
        }
    }
}