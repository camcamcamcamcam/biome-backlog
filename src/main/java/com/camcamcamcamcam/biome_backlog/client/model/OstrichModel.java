package com.camcamcamcamcam.biome_backlog.client.model;

// Made with Blockbench 4.3.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.camcamcamcamcam.biome_backlog.client.animation.definitions.OstrichAnimation;
import com.camcamcamcamcam.biome_backlog.world.level.entity.Ostrich;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class OstrichModel<T extends Ostrich> extends HierarchicalModel<T> {
	private final ModelPart root;

	private final ModelPart body;
	private final ModelPart legR;
	private final ModelPart legL;

	public OstrichModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.legR = root.getChild("legR");
		this.legL = root.getChild("legL");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -6.0F, -9.0F, 14.0F, 12.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 4.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(46, 0).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(60, 57).addBox(-4.0F, 8.0F, 0.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 9.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 30).addBox(-2.0F, -24.0F, -2.0F, 4.0F, 21.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, -22.0F, -5.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -11.0F));

		PartDefinition neckthing = neck.addOrReplaceChild("neckthing", CubeListBuilder.create().texOffs(40, 30).addBox(-4.0F, -0.5F, -2.5F, 8.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(26, 56).addBox(-4.0F, 6.5F, -2.5F, 8.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -0.5F));

		PartDefinition wingR = body.addOrReplaceChild("wingR", CubeListBuilder.create().texOffs(16, 30).addBox(-1.0F, 0.0F, -5.0F, 1.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition wingL = body.addOrReplaceChild("wingL", CubeListBuilder.create().texOffs(28, 38).addBox(0.0F, 0.0F, -5.0F, 1.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 1.0F, 2.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition legR = partdefinition.addOrReplaceChild("legR", CubeListBuilder.create().texOffs(12, 51).addBox(-1.5F, -1.0F, -4.0F, 3.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 10.0F, 1.0F));

		PartDefinition legL = partdefinition.addOrReplaceChild("legL", CubeListBuilder.create().texOffs(50, 42).addBox(-1.5F, -1.0F, -4.0F, 3.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 10.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		//you should reset animation
		this.root().getAllParts().forEach(ModelPart::resetPose);
		float f = Math.min((float) entity.getDeltaMovement().lengthSqr() * 100.0F, 8.0F);
		float f2 = Math.min((float) entity.getDeltaMovement().lengthSqr() * 50.0F, 8.0F);
		this.animate(entity.idlingState, OstrichAnimation.IDLE, ageInTicks);
		this.animateWalk(OstrichAnimation.WALK, limbSwing, limbSwingAmount * (1.0F - entity.getRunningScale()), 1.0F, 1.5F);

		this.animateWalk(OstrichAnimation.RUN, limbSwing, limbSwingAmount * entity.getRunningScale(), 1.0F, 1.5F);
		this.animate(entity.dippingState, OstrichAnimation.DIP, ageInTicks);
		this.animate(entity.kickingState, OstrichAnimation.KICK, ageInTicks);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		legR.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		legL.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}