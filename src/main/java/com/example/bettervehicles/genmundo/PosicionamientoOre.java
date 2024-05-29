package com.example.bettervehicles.genmundo;

import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class PosicionamientoOre {
    public static List<PlacementModifier> posicionOre(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> posicionComunOre(int pCount, PlacementModifier pHeightRange) {
        return posicionOre(CountPlacement.of(pCount), pHeightRange);
    }
}