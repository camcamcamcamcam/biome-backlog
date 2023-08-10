package com.camcamcamcamcam.biome_backlog.client.model;// Made with Blockbench 4.3.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.camcamcamcamcam.biome_backlog.client.animation.definitions.MeerkatAnimation;
import com.camcamcamcamcam.biome_backlog.world.level.entity.Meerkat;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MeerkatModel<T extends Meerkat> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart realRoot;

	protected final ModelPart head;
	protected final ModelPart body;
	protected final ModelPart rightLeg;
	protected final ModelPart leftLeg;
	protected final ModelPart rightArm;
	protected final ModelPart leftArm;

	public MeerkatModel(ModelPart root) {
		this.realRoot = root;
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.rightLeg = this.root.getChild("rightLeg");
		this.leftLeg = this.root.getChild("leftLeg");
		this.rightArm = this.root.getChild("rightArm");
		this.leftArm = this.root.getChild("leftArm");
		this.head = this.root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -5.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(17, 0).addBox(-2.0F, 0.0F, -7.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -2.0F));

		PartDefinition leftEar = head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(2, 14).addBox(0.0F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -1.0F, -2.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition rightEar = head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(2, 10).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -1.0F, -2.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 10).addBox(-2.0F, -8.0F, -1.0F, 4.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -1.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(24, 12).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 6.0F));

		PartDefinition leftArm = root.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(6, 21).addBox(0.01F, 0.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -2.0F, -1.0F));

		PartDefinition rightArm = root.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(0, 21).addBox(-1.01F, 0.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -2.0F, -1.0F));

		PartDefinition leftLeg = root.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(18, 21).addBox(2.01F, 0.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -2.0F, 5.0F));

		PartDefinition rightLeg = root.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(12, 21).addBox(-3.01F, 0.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -2.0F, 5.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);


		this.animate(entity.standingAnimationState, MeerkatAnimation.STAND_UP, ageInTicks);
		this.animate(entity.stopStandingAnimationState, MeerkatAnimation.STOP_STAND, ageInTicks);
		this.animate(entity.diggingAnimationState, MeerkatAnimation.DIGGING, ageInTicks);
		this.animate(entity.digUpAnimationState, MeerkatAnimation.DIGUP, ageInTicks);
		this.animateWalk(MeerkatAnimation.WALK, limbSwing, limbSwingAmount, 1.0F, 2.5F);
		if (this.young) {
			this.applyStatic(MeerkatAnimation.BABY);
		}
	}

	@Override
	public ModelPart root() {
		return this.realRoot;
	}
}