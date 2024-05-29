package com.example.bettervehicles.ui.Car;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public abstract class ContenedorBase extends ContenedorBase2 {

    public ContenedorBase(MenuType type, int id, Inventory inventarioJug, Container tileInventory) {
        super(type, id, inventarioJug, tileInventory);
        if (tileInventory instanceof TileEntityBase) {
            addDataSlots(((TileEntityBase) tileInventory).getFields());
        }
    }
}