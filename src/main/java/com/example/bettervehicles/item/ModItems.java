package com.example.bettervehicles.item;

import com.example.bettervehicles.BetterVehicles;
import com.example.bettervehicles.item.personalizado.ItemCoche;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BetterVehicles.MODID);


    public static final RegistryObject<Item> ITEM_COMBUSTIBLE = ITEMS.register("combustible", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CHASIS = ITEMS.register("chassis", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RUEDA = ITEMS.register("wheel", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MOTOR = ITEMS.register("engine", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PETROLEO = ITEMS.register("petroleum", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RUEDAS_CLAVOS = ITEMS.register("nailed_wheels", () -> new Item(new Item.Properties().durability(300)));

    public static final RegistryObject<Item> FLOTADOR = ITEMS.register("float", () -> new Item(new Item.Properties().durability(300)));

    public static final RegistryObject<Item> COCHE = ITEMS.register("car_item", () -> new ItemCoche(new Item.Properties().stacksTo(1)));


    public static void registrar(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
