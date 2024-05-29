package com.example.bettervehicles.bloques.entidad;

import com.example.bettervehicles.bloques.BloquesMod;
import com.example.bettervehicles.bloques.personalizado.SintetizadorCombustible;
import com.example.bettervehicles.item.ModItems;
import com.example.bettervehicles.ui.FuelSintetizer.SintetizadorCombustibleMenu;
import com.example.sonidos.SonidosMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SintetizadorCombustibleEntidadBloque extends BlockEntity implements MenuProvider {
    private final ItemStackHandler manejadorDeItems = new ItemStackHandler(2);

    private static final int RANURA_ENTRADA = 0;
    private static final int RANURA_SALIDA = 1;

    private LazyOptional<IItemHandler> manejadorDeItemsAux = LazyOptional.empty();

    protected final ContainerData datos;
    private int progreso = 0;
    private int maximoProgreso = 120;

    public SintetizadorCombustibleEntidadBloque(BlockPos posicion, BlockState estadoBloque) {
        super(BloquesModEntidades.SINTETIZADOR_DE_COMBUSTIBLE_BE.get(), posicion, estadoBloque);
        this.datos = new ContainerData() {
            @Override
            public int get(int indice) {
                return switch (indice) {
                    case 0 -> SintetizadorCombustibleEntidadBloque.this.progreso;
                    case 1 -> SintetizadorCombustibleEntidadBloque.this.maximoProgreso;
                    default -> 0;
                };
            }

            @Override
            public void set(int indice, int valor) {
                switch (indice) {
                    case 0 -> SintetizadorCombustibleEntidadBloque.this.progreso = valor;
                    case 1 -> SintetizadorCombustibleEntidadBloque.this.maximoProgreso = valor;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction lado) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return manejadorDeItemsAux.cast();
        }

        return super.getCapability(cap, lado);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        manejadorDeItemsAux = LazyOptional.of(() -> manejadorDeItems);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        manejadorDeItemsAux.invalidate();
    }

    public void soltarItems() {
        SimpleContainer inventario = new SimpleContainer(manejadorDeItems.getSlots());
        for (int i = 0; i < manejadorDeItems.getSlots(); i++) {
            inventario.setItem(i, manejadorDeItems.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventario);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.bettervehicles.fuel_sintetizer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int idContenedor, Inventory inventarioJugador, Player jugador) {
        return new SintetizadorCombustibleMenu(idContenedor, inventarioJugador, this, this.datos);
    }

    @Override
    protected void saveAdditional(CompoundTag etiqueta) {
        etiqueta.put("inventario", manejadorDeItems.serializeNBT());
        etiqueta.putInt("fuel_sintetizer.progreso", progreso);

        super.saveAdditional(etiqueta);
    }

    @Override
    public void load(CompoundTag etiqueta) {
        super.load(etiqueta);
        manejadorDeItems.deserializeNBT(etiqueta.getCompound("inventario"));
        progreso = etiqueta.getInt("fuel_sintetizer.progreso");
    }

    public void tick(Level nivel, BlockPos posicion, BlockState estadoBloque) {
        if (tieneReceta()) {
            if (progreso == 0 || progreso == 60) {
                level.playSeededSound(null, (double) posicion.getX(), (double) posicion.getY(), (double) posicion.getZ(), SonidosMod.FUEL_SINTETIZER_SOUND.get(), SoundSource.BLOCKS, 0.3f, 1f, 0);
            }
            aumentarProgresoDeCrafteo();
            level.setBlock(posicion, estadoBloque.setValue(SintetizadorCombustible.ENCENDIDO, true), 3);
            setChanged(nivel, posicion, estadoBloque);

            if (progresoTerminado()) {
                craftearItem();
                reiniciarProgreso();
            }
        } else {
            level.setBlock(posicion, estadoBloque.setValue(SintetizadorCombustible.ENCENDIDO, false), 3);
            reiniciarProgreso();
        }
    }

    private void reiniciarProgreso() {
        progreso = 0;
    }

    private void craftearItem() {
        ItemStack resultado = new ItemStack(ModItems.PETROLEO.get(), 1);
        this.manejadorDeItems.extractItem(RANURA_ENTRADA, 1, false);

        this.manejadorDeItems.setStackInSlot(RANURA_SALIDA, new ItemStack(resultado.getItem(),
                this.manejadorDeItems.getStackInSlot(RANURA_SALIDA).getCount() + resultado.getCount()));
    }

    private boolean tieneReceta() {
        boolean tieneItemParaCraftear = this.manejadorDeItems.getStackInSlot(RANURA_ENTRADA).getItem() == BloquesMod.PETROLEO_SOLIDIFICADO.get().asItem();
        ItemStack resultado = new ItemStack(ModItems.PETROLEO.get());

        return tieneItemParaCraftear && puedeInsertarCantidadEnRanuraDeSalida(resultado.getCount()) && puedeInsertarItemEnRanuraDeSalida(resultado.getItem());
    }

    private boolean puedeInsertarItemEnRanuraDeSalida(Item item) {
        return this.manejadorDeItems.getStackInSlot(RANURA_SALIDA).isEmpty() || this.manejadorDeItems.getStackInSlot(RANURA_SALIDA).is(item);
    }

    private boolean puedeInsertarCantidadEnRanuraDeSalida(int cantidad) {
        return this.manejadorDeItems.getStackInSlot(RANURA_SALIDA).getCount() + cantidad <= this.manejadorDeItems.getStackInSlot(RANURA_SALIDA).getMaxStackSize();
    }

    private boolean progresoTerminado() {
        return progreso >= maximoProgreso;
    }

    private void aumentarProgresoDeCrafteo() {
        progreso++;
    }
}
