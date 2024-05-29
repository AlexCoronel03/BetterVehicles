package com.example.bettervehicles.gendata.loot;

import com.example.bettervehicles.bloques.BloquesMod;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class TablaLootBloqueMod extends BlockLootSubProvider {
    public TablaLootBloqueMod() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

        this.dropSelf(BloquesMod.SINTETIZADOR_DE_COMBUSTIBLE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BloquesMod.BLOQUES.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
