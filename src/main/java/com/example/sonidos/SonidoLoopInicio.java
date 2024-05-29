package com.example.sonidos;

import com.example.bettervehicles.entidad.personalizado.EntidadCoche;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SonidoLoopInicio extends SonidosCocheLoop {

    public SonidoLoopInicio(EntidadCoche coche, SoundEvent event, SoundSource category) {
        super(coche, event, category);
        this.looping = true;
    }

    @Override
    public boolean debePararSonido() {
        return (!(coche.getPassengers().size() > 0) || !(coche.obtenerCombustible() > 0));
    }
}