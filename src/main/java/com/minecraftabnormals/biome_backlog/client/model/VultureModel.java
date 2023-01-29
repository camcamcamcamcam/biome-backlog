package com.minecraftabnormals.biome_backlog.client.model;// Made with Blockbench 4.3.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.minecraftabnormals.biome_backlog.world.level.entity.Vulture;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class VultureModel<T extends Vulture> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart vulture;
	private final ModelPart right_wing;
	private final ModelPart right_wing_but_more;
	private final ModelPart left_wing;
	private final ModelPart left_wing_but_more;


	public VultureModel(ModelPart root) {
		this.vulture = root.getChild("vulture");
		this.right_wing = this.vulture.getChild("right_wing");
		this.right_wing_but_more = this.right_wing.getChild("right_wing_but_more");
		this.left_wing = this.vulture.getChild("left_wing");
		this.left_wing_but_more = this.left_wing.getChild("left_wing_but_more");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition vulture = partdefinition.addOrReplaceChild("vulture", CubeListBuilder.create().texOffs(-8, 33).addBox(-6.5F, -7.0F, -11.0F, 13.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body_r1 = vulture.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(21, 11).addBox(-4.5F, -6.5F, -4.5F, 9.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -13.5F, 1.5F, 3.1416F, 0.0F, 3.1416F));

		PartDefinition collar_r1 = vulture.addOrReplaceChild("collar_r1", CubeListBuilder.create().texOffs(23, 0).addBox(-3.5F, -3.5F, -1.0F, 7.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.5F, 8.0F, 3.1416F, 0.0F, 3.1416F));

		PartDefinition neck = vulture.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -15.5F, 7.0F));

		PartDefinition neck_1_r1 = neck.addOrReplaceChild("neck_1_r1", CubeListBuilder.create().texOffs(12, 11).addBox(-1.5F, -1.5F, -6.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition neck_but_more = neck.addOrReplaceChild("neck_but_more", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.5F));

		PartDefinition neck_2_r1 = neck_but_more.addOrReplaceChild("neck_2_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-1.5F, -5.5F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition head = neck_but_more.addOrReplaceChild("head", CubeListBuilder.create().texOffs(18, 0).addBox(-0.5F, 0.0F, 8.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.5F, 0.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.0F, -4.0F, -5.5F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 3.1416F));

		PartDefinition beak_r1 = head.addOrReplaceChild("beak_r1", CubeListBuilder.create().texOffs(15, 1).addBox(-0.5F, -1.5F, -3.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 5.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition left_wing = vulture.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(-4.5F, -15.5F, 0.5F));

		PartDefinition left_wing_1_r1 = left_wing.addOrReplaceChild("left_wing_1_r1", CubeListBuilder.create().texOffs(0, 41).addBox(0.0F, -4.5F, -0.5F, 11.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 3.1416F));

		PartDefinition left_wing_but_more = left_wing.addOrReplaceChild("left_wing_but_more", CubeListBuilder.create(), PartPose.offset(-11.0F, 0.0F, 0.0F));

		PartDefinition left_wing_2_r1 = left_wing_but_more.addOrReplaceChild("left_wing_2_r1", CubeListBuilder.create().texOffs(24, 41).addBox(0.0F, -4.5F, -0.5F, 9.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 3.1416F));

		PartDefinition right_wing = vulture.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(4.5F, -15.5F, 0.5F));

		PartDefinition right_wing_1_r1 = right_wing.addOrReplaceChild("right_wing_1_r1", CubeListBuilder.create().texOffs(0, 51).addBox(-11.0F, -4.5F, -0.5F, 11.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 3.1416F));

		PartDefinition right_wing_but_more = right_wing.addOrReplaceChild("right_wing_but_more", CubeListBuilder.create(), PartPose.offset(11.0F, 0.0F, 0.0F));

		PartDefinition right_wing_2_r1 = right_wing_but_more.addOrReplaceChild("right_wing_2_r1", CubeListBuilder.create().texOffs(24, 51).addBox(-9.0F, -4.5F, -0.5F, 9.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 3.1416F));

		PartDefinition left_leg = vulture.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(43, 0).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(48, 8).addBox(-1.5F, 4.0F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -7.0F, 0.0F));

		PartDefinition right_leg = vulture.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(26, 33).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(40, 33).addBox(-1.5F, 4.0F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -7.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float f = ((float) entity.getUniqueFlapTickOffset() + ageInTicks) * 7.448451F * ((float) Math.PI / 180F);
		float f1 = 16.0F;
		this.left_wing.yRot = Mth.cos(f) * 16.0F * ((float) Math.PI / 180F);
		this.left_wing_but_more.yRot = Mth.cos(f) * 16.0F * ((float) Math.PI / 180F);
		this.right_wing.yRot = -this.left_wing.yRot;
		this.right_wing_but_more.yRot = -this.left_wing_but_more.yRot;

		this.vulture.yRot = 3.1416F;

		this.right_wing.xRot = -(float) ((Math.PI / 2F) * limbSwingAmount);
		this.left_wing.xRot = -(float) ((Math.PI / 2F) * limbSwingAmount);

		this.vulture.xRot = -0.25F * limbSwingAmount;

	}

	@Override
	public ModelPart root() {
		return this.vulture;
	}
}