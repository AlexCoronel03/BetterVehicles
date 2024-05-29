package com.example.bettervehicles.ui.Car;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public abstract class ContenedorBase2 extends AbstractContainerMenu {
    @Nullable
    protected Container inventario;
    protected Container inventarioJug;

    public ContenedorBase2(MenuType containerType, int id, Container inventarioJug, Container inventario) {
        super(containerType, id);
        this.inventarioJug = inventarioJug;
        this.inventario = inventario;
    }

    protected void añadirEspaciosInventario() {
        if (this.inventarioJug != null) {
            int k;
            for(k = 0; k < 3; ++k) {
                for(int j = 0; j < 9; ++j) {
                    this.addSlot(new Slot(this.inventarioJug, j + k * 9 + 9, 8 + j * 18, 84 + k * 18 + this.getInvOffset()));
                }
            }

            for(k = 0; k < 9; ++k) {
                this.addSlot(new Slot(this.inventarioJug, k, 8 + k * 18, 142 + this.getInvOffset()));
            }
        }

    }

    public int getInvOffset() {
        return 0;
    }

    public int obtenerTamañoInventario() {
        return this.inventario == null ? 0 : this.inventario.getContainerSize();
    }


    public ItemStack quickMoveStack(Player jugador, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index < this.obtenerTamañoInventario()) {
                if (!this.moveItemStackTo(stack, this.obtenerTamañoInventario(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 0, this.obtenerTamañoInventario(), false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public boolean stillValid(Player jugador) {
        return this.inventario == null ? true : this.inventario.stillValid(jugador);
    }

    public void removed(Player jugador) {
        super.removed(jugador);
        if (this.inventario != null) {
            this.inventario.stopOpen(jugador);
        }

    }
}