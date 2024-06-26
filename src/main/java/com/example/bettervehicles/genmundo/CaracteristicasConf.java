package com.example.bettervehicles.genmundo;

import com.example.bettervehicles.BetterVehicles;
import com.example.bettervehicles.bloques.BloquesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class CaracteristicasConf {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_PETROLEUM_ORE_KEY = registerKey("petroleum_ore");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> overworldPetroleumOres = List.of(OreConfiguration.target(stoneReplaceable,
                BloquesMod.PETROLEO_SOLIDIFICADO.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, BloquesMod.PETROLEO_SOLIDIFICADO.get().defaultBlockState()));

        register(context, OVERWORLD_PETROLEUM_ORE_KEY, Feature.ORE, new OreConfiguration(overworldPetroleumOres, 4));

    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(BetterVehicles.MODID, name));

    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}