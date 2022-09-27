package com.minecraftabnormals.biome_vote_losers.client.model;

import com.minecraftabnormals.biome_vote_losers.world.level.entity.Tumbleweed;

// Made with Blockbench 4.3.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class TumbleweedModel extends EntityModel<Tumbleweed> {

	public final ModelPart base;

	public TumbleweedModel(ModelPart root) {
		this.base = root.getChild("base");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		/* This empty base model part is needed to offset the pivot point to the center of the model. */
		PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));
		PartDefinition tumbleweed = base.addOrReplaceChild("tumbleweed", CubeListBuilder.create().texOffs(24, 24).addBox(-4.0F, 2.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition bush_r1 = tumbleweed.addOrReplaceChild("bush_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -3.0F, 0.0F, 16.0F, 16.0F, 0.0F), PartPose.rotation(0.0F, 0.7854F, 0.0F));

		PartDefinition bush_r2 = tumbleweed.addOrReplaceChild("bush_r2", CubeListBuilder.create().texOffs(0, 16).addBox(-8.0F, -3.0F, 0.0F, 16.0F, 16.0F, 0.0F), PartPose.rotation(0.0F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Tumbleweed entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.base.xRot = (headPitch % 360.0F) * Mth.DEG_TO_RAD;
		this.base.yRot = -(netHeadYaw % 360.0F) * Mth.DEG_TO_RAD;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}