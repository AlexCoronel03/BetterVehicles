package com.example.bettervehicles;

import com.example.bettervehicles.herramientas.HerramientasEntidad;
import com.example.bettervehicles.entidad.personalizado.EntidadCoche;
import com.example.bettervehicles.bloques.BloquesMod;
import com.example.bettervehicles.bloques.entidad.BloquesModEntidades;
import com.example.bettervehicles.entidad.EntidadMod;
import com.example.bettervehicles.entidad.cliente.RenderizadorEntidadCoche;
import com.example.bettervehicles.evento.SuscriptorEventos;
import com.example.bettervehicles.net.ControlMensajesCoche;
import com.example.bettervehicles.ui.Car.ContenedorCoche;
import com.example.bettervehicles.ui.Car.InterfazCoche;
import com.example.bettervehicles.item.ModItems;
import com.example.bettervehicles.ui.FuelSintetizer.PantallaSintetizadorCombustible;
import com.example.bettervehicles.ui.FuelSintetizer.TiposMenu;
import com.example.sonidos.SonidosMod;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;


@Mod(BetterVehicles.MODID)
public class BetterVehicles
{
    
    public static final String MODID = "bettervehicles";

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    
    private static final Logger LOGGER = LogUtils.getLogger();
    

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    public static final DeferredRegister<MenuType<?>> MENU_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BetterVehicles.MODID);

    public static final RegistryObject<MenuType<ContenedorCoche>> CAR_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("car", () ->
            IForgeMenuType.create((IContainerFactory<ContenedorCoche>) (windowId, inv, data) -> {
                EntidadCoche coche = HerramientasEntidad.getCarByUUID(inv.player, data.readUUID());
                if (coche == null) {
                    return null;
                }
                return new ContenedorCoche(windowId, coche, inv);
            })
    );


    

    
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("items", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.ITEM_COMBUSTIBLE.get().getDefaultInstance())
            .title(Component.translatable("creativeTab.items"))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.ITEM_COMBUSTIBLE.get()); 
                output.accept(BloquesMod.PETROLEO_SOLIDIFICADO.get());
                output.accept(BloquesMod.SINTETIZADOR_DE_COMBUSTIBLE.get());
                output.accept(ModItems.COCHE.get());
                output.accept(ModItems.PETROLEO.get());
                output.accept(ModItems.CHASIS.get());
                output.accept(ModItems.RUEDAS_CLAVOS.get());
                output.accept(ModItems.FLOTADOR.get());
                output.accept(ModItems.RUEDA.get());
                output.accept(ModItems.MOTOR.get());
            }).build());


    public BetterVehicles()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MENU_TYPE_REGISTER.register(modEventBus);

        INSTANCE.registerMessage(0, ControlMensajesCoche.class,
                ControlMensajesCoche::toBytes,
                ControlMensajesCoche::fromBytes,
                ControlMensajesCoche::ejecutarEnServidor);

        
        modEventBus.addListener(this::commonSetup);


        
        BloquesMod.registrar(modEventBus);
        
        ModItems.registrar(modEventBus);

        EntidadMod.registrar(modEventBus);

        BloquesModEntidades.registrar(modEventBus);
        TiposMenu.register(modEventBus);


        
        CREATIVE_MODE_TABS.register(modEventBus);

        
        MinecraftForge.EVENT_BUS.register(new SuscriptorEventos());
        MinecraftForge.EVENT_BUS.register(this);

        SonidosMod.register(modEventBus);
        modEventBus.addListener(this::addCreative);
        
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }


    private void commonSetup(final FMLCommonSetupEvent event)
    {
        
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS)
            event.accept(BloquesMod.PETROLEO_SOLIDIFICADO);

        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS)
            event.accept(BloquesMod.SINTETIZADOR_DE_COMBUSTIBLE);

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.PETROLEO);
            event.accept(ModItems.RUEDA);
            event.accept(ModItems.MOTOR);
            event.accept(ModItems.CHASIS);
        }


        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.COCHE);
            event.accept(ModItems.ITEM_COMBUSTIBLE);
            event.accept(ModItems.RUEDAS_CLAVOS);
            event.accept(ModItems.FLOTADOR);
        }
        
    }

    
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            
            EntityRenderers.register(EntidadMod.COCHE.get(), RenderizadorEntidadCoche::new);
            MenuScreens.register(CAR_CONTAINER_TYPE.get(), InterfazCoche::new);
            MenuScreens.register(TiposMenu.SINTETIZADOR_COMBUSTIBLE_MENU.get(), PantallaSintetizadorCombustible::new);
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
