package com.example.bettervehicles.gendata;

import com.example.bettervehicles.gendata.loot.TablaLootBloqueMod;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class ProveedorLootTabla {
    public static LootTableProvider crear(PackOutput salida) {
        return new LootTableProvider(salida, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(TablaLootBloqueMod::new, LootContextParamSets.BLOCK)
        ));
    }
}
