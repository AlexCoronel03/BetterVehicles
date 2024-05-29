package com.example.bettervehicles.entidad;

import com.example.bettervehicles.BetterVehicles;
import com.example.bettervehicles.entidad.personalizado.EntidadCoche;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntidadMod {
    public static final DeferredRegister<EntityType<?>> TIPOS_DE_ENTIDAD =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BetterVehicles.MODID);

    public static final RegistryObject<EntityType<EntidadCoche>> COCHE =
            TIPOS_DE_ENTIDAD.register("car", () -> EntityType.Builder.of(EntidadCoche::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).clientTrackingRange(80).updateInterval(20)
                    .sized(1.5f, 1.1f)
                    .build(String.valueOf(new ResourceLocation(BetterVehicles.MODID, "car"))));

    public static void registrar(IEventBus eventBus) {
        TIPOS_DE_ENTIDAD.register(eventBus);
    }
}
