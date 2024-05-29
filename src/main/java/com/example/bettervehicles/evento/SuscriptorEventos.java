package com.example.bettervehicles.evento;

import com.example.bettervehicles.BetterVehicles;
import com.example.bettervehicles.entidad.personalizado.EntidadCoche;
import com.example.bettervehicles.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterVehicles.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class SuscriptorEventos {
    private static final Component EXAMPLE_KEY_PRESSED =
            Component.translatable("message." + BetterVehicles.MODID + ".example_key_pressed");

    @SubscribeEvent
    public void alPulsarTecla(InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();

        Player player = minecraft.player;

        if (player == null) {
            return;
        }

        Entity riding = player.getVehicle();

        if (!(riding instanceof EntidadCoche)) {
            return;
        }

        EntidadCoche car = (EntidadCoche) riding;

        car.updateControls(MapeoTeclas.wKEY.isDown(), MapeoTeclas.aKEY.isDown(), MapeoTeclas.dKEY.isDown(), MapeoTeclas.sKEY.isDown(), MapeoTeclas.upKEY.isDown());
    }

    @SubscribeEvent
    public void alAtacarJugador(AttackEntityEvent event) {
        Minecraft minecraft = Minecraft.getInstance();

        Player player = minecraft.player;

        if (event.getTarget() instanceof EntidadCoche car) {
            ItemStack item = new ItemStack(ModItems.COCHE.get());
            if (car.getNBT() != null) {
                item.setTag(car.getNBT());
            }
            car.spawnAtLocation(item, 1);
            car.remove(Entity.RemovalReason.DISCARDED);
        }
    }
}
