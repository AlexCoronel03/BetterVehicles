package com.example.bettervehicles.evento;

import com.example.bettervehicles.BetterVehicles;
import com.example.bettervehicles.entidad.cliente.CarModel;
import com.example.bettervehicles.entidad.cliente.ModeladoCapas;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterVehicles.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BusEventosClientes {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModeladoCapas.CAPA_COCHE, CarModel::createBodyLayer);
    }

}