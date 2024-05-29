package com.example.bettervehicles.entidad.cliente;// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class CarModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart coche;

	public CarModel(ModelPart root) {
		this.coche = root.getChild("coche");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition coche = partdefinition.addOrReplaceChild("coche", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition carroceria = coche.addOrReplaceChild("carroceria", CubeListBuilder.create().texOffs(0, 17).addBox(-15.0F, -3.0F, 10.0F, 46.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(0, 111).addBox(-15.0F, -11.0F, 10.0F, 7.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(114, 17).addBox(-15.0F, -10.0F, 31.0F, 34.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(82, 66).addBox(-15.0F, -10.0F, 10.0F, 34.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 65).addBox(7.0F, -11.0F, 10.0F, 12.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(0, 40).addBox(-6.0F, -2.0F, 10.0F, 16.0F, 3.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(114, 32).addBox(-30.0F, -2.0F, 10.0F, 18.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(114, 25).addBox(-30.0F, -2.0F, 28.0F, 18.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(124, 132).addBox(16.0F, -2.0F, 10.0F, 15.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(124, 125).addBox(16.0F, -2.0F, 28.0F, 15.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-32.0F, -2.0F, 14.0F, 63.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(54, 43).addBox(-30.0F, -3.0F, 10.0F, 15.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -4.0F, -21.0F));

		PartDefinition cube_r1 = carroceria.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(90, 89).addBox(-18.0F, -15.0F, 18.0F, 8.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 88).addBox(-18.0F, -15.0F, 10.0F, 8.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition cube_r2 = carroceria.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, 4.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-27.0F, -15.0F, 11.0F, -1.5708F, 0.0F, -1.5708F));

		PartDefinition cube_r3 = carroceria.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(62, 124).addBox(-8.0F, 0.0F, -4.0F, 4.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-25.0F, -11.0F, 11.0F, -1.5708F, 0.0F, -1.5708F));

		PartDefinition cube_r4 = carroceria.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 33).addBox(-8.0F, 0.0F, 8.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-25.0F, -14.0F, 11.0F, -1.5708F, 0.0F, -1.5708F));

		PartDefinition cube_r5 = carroceria.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(12, 17).addBox(-8.0F, 0.0F, 8.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-27.0F, -14.0F, 11.0F, -1.5708F, 0.0F, -1.5708F));

		PartDefinition cube_r6 = carroceria.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(12, 25).addBox(-8.0F, 0.0F, 8.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(54, 47).addBox(-8.0F, 0.0F, 4.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-27.0F, -14.0F, 32.0F, -1.5708F, 0.0F, -1.5708F));

		PartDefinition cube_r7 = carroceria.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(102, 125).addBox(-8.0F, 0.0F, -4.0F, 4.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-25.0F, -11.0F, 32.0F, -1.5708F, 0.0F, -1.5708F));

		PartDefinition cube_r8 = carroceria.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(10, 34).addBox(-8.0F, 0.0F, 8.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-25.0F, -14.0F, 32.0F, -1.5708F, 0.0F, -1.5708F));

		PartDefinition cube_r9 = carroceria.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 7).addBox(-8.0F, 0.0F, 4.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-27.0F, -15.0F, 32.0F, -1.5708F, 0.0F, -1.5708F));

		PartDefinition cube_r10 = carroceria.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(116, 102).addBox(-8.0F, 0.0F, -11.0F, 4.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-30.0F, -11.0F, 21.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r11 = carroceria.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(106, 40).addBox(-17.0F, -15.0F, -11.0F, 7.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, 6.0F, 21.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition cube_r12 = carroceria.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(38, 101).addBox(-18.0F, -15.0F, -11.0F, 8.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 21.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition cube_r13 = carroceria.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(76, 102).addBox(-8.0F, 0.0F, -10.0F, 11.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -11.0F, 21.0F, 0.0F, 0.0F, -1.0908F));

		PartDefinition cube_r14 = carroceria.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(48, 68).addBox(-8.0F, 0.0F, -10.0F, 7.0F, 12.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -11.0F, 21.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r15 = carroceria.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(118, 74).addBox(-8.0F, 0.0F, -10.0F, 7.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -11.0F, 21.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r16 = carroceria.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(76, 123).addBox(8.0F, -2.0F, -11.0F, 2.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(8.0F, -2.0F, 10.0F, -6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(8.0F, -2.0F, -11.0F, -6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 124).addBox(0.0F, -2.0F, -11.0F, 2.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -9.0F, 21.0F, 0.0F, 0.0F, -0.9163F));

		PartDefinition rueda4 = coche.addOrReplaceChild("rueda4", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, -3.0F, 9.0F));

		PartDefinition rueda3 = coche.addOrReplaceChild("rueda3", CubeListBuilder.create().texOffs(0, 40).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, -3.0F, 9.0F));

		PartDefinition rueda1 = coche.addOrReplaceChild("rueda1", CubeListBuilder.create().texOffs(0, 25).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, -3.0F, -9.0F));

		PartDefinition rueda2 = coche.addOrReplaceChild("rueda2", CubeListBuilder.create().texOffs(0, 17).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, -3.0F, -9.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		coche.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	/**
	 * @return
	 */
	@Override
	public ModelPart root() {
		return null;
	}
}