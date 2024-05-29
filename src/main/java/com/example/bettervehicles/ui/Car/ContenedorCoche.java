package com.example.bettervehicles.ui.Car;

import com.example.bettervehicles.BetterVehicles;
import com.example.bettervehicles.entidad.personalizado.EntidadCoche;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class ContenedorCoche extends ContenedorBase {

    protected EntidadCoche coche;

    public ContenedorCoche(int id, EntidadCoche coche, Inventory playerInv) {
        super(BetterVehicles.CAR_CONTAINER_TYPE.get(), id, playerInv, coche);
        this.coche = coche;

        int numRows = coche.getContainerSize() / 9;

        for (int j = 0; j < numRows; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new Slot(coche, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        añadirEspaciosInventario();
    }

    public EntidadCoche getCoche() {
        return coche;
    }

    @Override
    public int getInvOffset() {
        return 0;
    }

}
