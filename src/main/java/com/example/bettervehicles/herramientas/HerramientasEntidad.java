package com.example.bettervehicles.herramientas;

import com.example.bettervehicles.entidad.personalizado.EntidadCoche;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import javax.annotation.Nullable;
import java.util.UUID;

public class HerramientasEntidad {
    @Nullable
    public static EntidadCoche getCarByUUID(Player player, UUID uuid) {
        double distance = 10D;
        return player.level().getEntitiesOfClass(EntidadCoche.class, new AABB(player.getX() - distance, player.getY() - distance, player.getZ() - distance, player.getX() + distance, player.getY() + distance, player.getZ() + distance), entity -> entity.getUUID().equals(uuid)).stream().findAny().orElse(null);
    }

}
