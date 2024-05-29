package com.example.bettervehicles.bloques.entidad;

import com.example.bettervehicles.BetterVehicles;
import com.example.bettervehicles.bloques.BloquesMod;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BloquesModEntidades {
    public static final DeferredRegister<BlockEntityType<?>> ENTIDADES_DE_BLOQUE =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BetterVehicles.MODID);

    public static final RegistryObject<BlockEntityType<SintetizadorCombustibleEntidadBloque>> SINTETIZADOR_DE_COMBUSTIBLE_BE =
            ENTIDADES_DE_BLOQUE.register("fuel_sintetizer_be", () ->
                    BlockEntityType.Builder.of(SintetizadorCombustibleEntidadBloque::new,
                            BloquesMod.SINTETIZADOR_DE_COMBUSTIBLE.get()).build(null));

    public static void registrar(IEventBus eventBus) {
        ENTIDADES_DE_BLOQUE.register(eventBus);
    }
}
