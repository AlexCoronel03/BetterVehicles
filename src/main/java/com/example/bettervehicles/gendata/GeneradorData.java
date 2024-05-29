package com.example.bettervehicles.gendata;

import com.example.bettervehicles.BetterVehicles;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = BetterVehicles.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GeneradorData {
    @SubscribeEvent
    public static void recolectarDatos(GatherDataEvent evento) {
        DataGenerator generador = evento.getGenerator();
        PackOutput salidaPaquete = generador.getPackOutput();
        CompletableFuture<HolderLookup.Provider> proveedorBusqueda = evento.getLookupProvider();

        generador.addProvider(evento.includeServer(), new ProveedorGeneradorMundo(salidaPaquete, proveedorBusqueda));
        generador.addProvider(evento.includeServer(), ProveedorLootTabla.crear(salidaPaquete));
    }
}
