package com.example.bettervehicles.entidad.cliente;

import com.example.bettervehicles.BetterVehicles;
import com.example.bettervehicles.entidad.personalizado.EntidadCoche;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RenderizadorEntidadCoche extends EntityRenderer<EntidadCoche> {
    private static final ResourceLocation textura = new ResourceLocation(BetterVehicles.MODID, "textures/entity/car.png");
    private static final ResourceLocation texturaCofre = new ResourceLocation(BetterVehicles.MODID, "textures/entity/car_chest.png");
    private static final ResourceLocation texturaCofreLlantas = new ResourceLocation(BetterVehicles.MODID, "textures/entity/car_chest_nailed_wheels.png");
    private static final ResourceLocation texturaCofreFlotador = new ResourceLocation(BetterVehicles.MODID, "textures/entity/car_chest_float.png");
    private static final ResourceLocation texturaCofreLlantasFlotador = new ResourceLocation(BetterVehicles.MODID, "textures/entity/car_chest_nailed_wheels_float.png");

    protected final EntityModel<EntidadCoche> modelo;

    public RenderizadorEntidadCoche(EntityRendererProvider.Context contexto) {
        super(contexto);
        this.modelo = new CarModel<>(contexto.bakeLayer(ModeladoCapas.CAPA_COCHE));
    }

    @Override
    public ResourceLocation getTextureLocation(EntidadCoche entidad) {
        ResourceLocation texturaActual = textura;

        if (entidad.tieneCofre()) {
            texturaActual = texturaCofre;
            if (entidad.tieneFlotador() && !entidad.tieneRuedasClavos()) {
                texturaActual = texturaCofreFlotador;
            } else if (entidad.tieneFlotador() && entidad.tieneRuedasClavos()) {
                texturaActual = texturaCofreLlantasFlotador;
            } else if (!entidad.tieneFlotador() && entidad.tieneRuedasClavos()) {
                texturaActual = texturaCofreLlantas;
            }
        }
        return texturaActual;
    }

    @Override
    public void render(EntidadCoche entidad, float entidadYaw, float ticksParciales, PoseStack matriz, MultiBufferSource buffer, int luzEmpaquetada) {
        matriz.pushPose();
        matriz.translate(0.0D, 1.3D, 0.0D);

        matriz.mulPose(Axis.YP.rotationDegrees(entidad.getViewYRot(ticksParciales) * -1));

        matriz.scale(1F, -1F, 1F);
        VertexConsumer consumidorVertices = buffer.getBuffer(this.modelo.renderType(getTextureLocation(entidad)));
        this.modelo.renderToBuffer(matriz, consumidorVertices, luzEmpaquetada, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matriz.popPose();

        super.render(entidad, entidadYaw, ticksParciales, matriz, buffer, luzEmpaquetada);
    }
}
