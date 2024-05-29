package com.example.bettervehicles.bloques;

import com.example.bettervehicles.BetterVehicles;
import com.example.bettervehicles.bloques.personalizado.SintetizadorCombustible;
import com.example.bettervehicles.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BloquesMod {
    public static final DeferredRegister<Block> BLOQUES =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BetterVehicles.MODID);

    public static final RegistryObject<Block> PETROLEO_SOLIDIFICADO = registrarBloque("solidified_petroleum",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE).strength(4f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> SINTETIZADOR_DE_COMBUSTIBLE = registrarBloque("fuel_sintetizer",
            () -> new SintetizadorCombustible(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(1f).noOcclusion()));


    private static <T extends Block> RegistryObject<T> registrarBloque(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOQUES.register(name, block);
        registrarItemDeBloque(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registrarItemDeBloque(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void registrar(IEventBus eventBus) {
        BLOQUES.register(eventBus);
    }
}
