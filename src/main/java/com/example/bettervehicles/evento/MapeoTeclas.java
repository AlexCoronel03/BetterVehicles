package com.example.bettervehicles.evento;

import com.example.bettervehicles.BetterVehicles;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public final class MapeoTeclas {

    private MapeoTeclas() {}

    private static final String CATEGORY = "key.categories." + BetterVehicles.MODID;

    public static final KeyMapping wKEY = new KeyMapping(
            "key." + BetterVehicles.MODID + ".w_key",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_W, -1),
            CATEGORY
    );

    public static final KeyMapping aKEY = new KeyMapping(
            "key." + BetterVehicles.MODID + ".a_key",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_A, -1),
            CATEGORY
    );

    public static final KeyMapping dKEY = new KeyMapping(
            "key." + BetterVehicles.MODID + ".d_key",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_D, -1),
            CATEGORY
    );

    public static final KeyMapping upKEY = new KeyMapping(
            "key." + BetterVehicles.MODID + ".up_key",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_SPACE, -1),
            CATEGORY
    );

    public static final KeyMapping sKEY = new KeyMapping(
            "key." + BetterVehicles.MODID + ".s_key",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_S, -1),
            CATEGORY
    );
}
