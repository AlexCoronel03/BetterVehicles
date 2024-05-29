package com.example.bettervehicles.ui.FuelSintetizer;

import com.example.bettervehicles.BetterVehicles;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TiposMenu {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, BetterVehicles.MODID);

    public static final RegistryObject<MenuType<SintetizadorCombustibleMenu>> SINTETIZADOR_COMBUSTIBLE_MENU =
        registrarMenu("fuel_sintetizer_menu", SintetizadorCombustibleMenu::new);


    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registrarMenu(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
