package com.example.bettervehicles.entidad.cliente;

import com.example.bettervehicles.BetterVehicles;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModeladoCapas {
    public static final ModelLayerLocation CAPA_COCHE = new ModelLayerLocation(
            new ResourceLocation(BetterVehicles.MODID, "car_layer"), "principal");
}
