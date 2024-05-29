package com.example.bettervehicles.gendata;

import com.example.bettervehicles.BetterVehicles;
import com.example.bettervehicles.genmundo.ModificadorBiomas;
import com.example.bettervehicles.genmundo.CaracteristicasConf;
import com.example.bettervehicles.genmundo.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ProveedorGeneradorMundo extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder CONSTRUCTOR = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, CaracteristicasConf::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModificadorBiomas::bootstrap);

    public ProveedorGeneradorMundo(PackOutput salida, CompletableFuture<HolderLookup.Provider> registros) {
        super(salida, registros, CONSTRUCTOR, Set.of(BetterVehicles.MODID));
    }
}
