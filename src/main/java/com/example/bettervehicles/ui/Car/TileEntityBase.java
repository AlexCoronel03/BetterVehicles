package com.example.bettervehicles.ui.Car;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Nameable;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class TileEntityBase extends BlockEntity implements Nameable {

    private Component nombre;

    public TileEntityBase(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }


    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag updateTag = super.getUpdateTag();
        saveAdditional(updateTag);
        return updateTag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public abstract Component getTranslatedName();


    @Override
    public Component getName() {
        return nombre != null ? nombre : getTranslatedName();
    }

    @Override
    @Nullable
    public Component getCustomName() {
        return nombre;
    }

    public abstract ContainerData getFields();

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);

        if (nombre != null) {
            compound.putString("CustomName", Component.Serializer.toJson(nombre));
        }
    }

    @Override
    public void load(CompoundTag compound) {
        if (compound.contains("CustomName")) {
            nombre = Component.Serializer.fromJson(compound.getString("CustomName"));
        }
        super.load(compound);
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @org.jetbrains.annotations.Nullable Direction side) {
        if ((this instanceof IFluidHandler && cap.equals(ForgeCapabilities.FLUID_HANDLER)) ||
                (this instanceof IEnergyStorage && cap.equals(ForgeCapabilities.ENERGY))) {
            return LazyOptional.of(() -> (T) this);
        }
        return LazyOptional.empty();
    }
}