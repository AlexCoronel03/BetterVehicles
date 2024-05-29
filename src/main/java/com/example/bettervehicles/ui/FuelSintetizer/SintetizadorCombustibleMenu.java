package com.example.bettervehicles.ui.FuelSintetizer;

import com.example.bettervehicles.bloques.BloquesMod;
import com.example.bettervehicles.bloques.entidad.SintetizadorCombustibleEntidadBloque;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class SintetizadorCombustibleMenu extends AbstractContainerMenu {
    public final SintetizadorCombustibleEntidadBloque entidadBloque;
    private final Level level;
    private final ContainerData data;

    public SintetizadorCombustibleMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public SintetizadorCombustibleMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(TiposMenu.SINTETIZADOR_COMBUSTIBLE_MENU.get(), pContainerId);
        checkContainerSize(inv, 2);
        entidadBloque = ((SintetizadorCombustibleEntidadBloque) entity);
        this.level = inv.player.level();
        this.data = data;

        añadirInvJugador(inv);
        añadirBarraJugador(inv);

        this.entidadBloque.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 80, 11));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 80, 59));
        });

        addDataSlots(data);
    }

    public boolean estaCrafteando() {
        return data.get(0) > 0;
    }

    public int obtenerProgreso() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  
        int progressArrowSize = 26; 

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    private static final int CONT_SLOT_BARRA = 9;
    private static final int CONT_FILA_INV_JUGADOR = 3;
    private static final int CONT_COLUMNA_INV_JUGADOR = 9;
    private static final int CONT_SLOT_INV_JUGADOR = CONT_COLUMNA_INV_JUGADOR * CONT_FILA_INV_JUGADOR;
    private static final int VANILLA_CONT_SLOT = CONT_SLOT_BARRA + CONT_SLOT_INV_JUGADOR;
    private static final int VANILLA_PRIMER_SLOT_INDEX = 0;
    private static final int TE_INVPRIMER_SLOT = VANILLA_PRIMER_SLOT_INDEX + VANILLA_CONT_SLOT;

    private static final int TE_INVENTORY_SLOT_COUNT = 2;  
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        
        if (pIndex < VANILLA_PRIMER_SLOT_INDEX + VANILLA_CONT_SLOT) {
            
            if (!moveItemStackTo(sourceStack, TE_INVPRIMER_SLOT, TE_INVPRIMER_SLOT
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  
            }
        } else if (pIndex < TE_INVPRIMER_SLOT + TE_INVENTORY_SLOT_COUNT) {
            
            if (!moveItemStackTo(sourceStack, VANILLA_PRIMER_SLOT_INDEX, VANILLA_PRIMER_SLOT_INDEX + VANILLA_CONT_SLOT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, entidadBloque.getBlockPos()),
                pPlayer, BloquesMod.SINTETIZADOR_DE_COMBUSTIBLE.get());
    }

    private void añadirInvJugador(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void añadirBarraJugador(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
