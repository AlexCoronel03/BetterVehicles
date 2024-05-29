package com.example.sonidos;

import com.example.bettervehicles.entidad.personalizado.EntidadCoche;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public abstract class SonidosCocheLoop extends AbstractTickableSoundInstance {

    protected EntidadCoche coche;

    public SonidosCocheLoop(EntidadCoche coche, SoundEvent event, SoundSource category) {
        super(event, category, SoundInstance.createUnseededRandom());
        this.coche = coche;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.4f;
        this.pitch = 1F;
        this.relative = false;
        this.attenuation = Attenuation.LINEAR;
        this.actualizarPos();
    }

    public void actualizarPos() {
        this.x = (float) coche.getX();
        this.y = (float) coche.getY();
        this.z = (float) coche.getZ();
    }

    @Override
    public void tick() {
        if (isStopped()) {
            return;
        }

        if (!coche.isAlive()) {
            setAcabaSonido();
            return;
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !player.isAlive()) {
            setAcabaSonido();
            return;
        }

        if (debePararSonido()) {
            setAcabaSonido();
            return;
        }

        actualizarPos();
    }

    public void setAcabaSonido() {
        stop();
    }

    public abstract boolean debePararSonido();

}