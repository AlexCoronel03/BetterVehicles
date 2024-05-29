package com.example.bettervehicles.net;

import com.example.bettervehicles.entidad.personalizado.EntidadCoche;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ControlMensajesCoche {

    private boolean winput;
    private boolean ainput;

    private boolean dinput;

    private boolean sinput;
    private boolean upinput;

    public ControlMensajesCoche() {

    }

    public ControlMensajesCoche(boolean winput, boolean ainput, boolean dinput, boolean sinput, boolean upinput) {
        this.winput = winput;
        this.ainput = ainput;
        this.dinput = dinput;
        this.sinput = sinput;
        this.upinput = upinput;

    }


    public Dist obtenerEjecucion() {
        return Dist.DEDICATED_SERVER;
    }


    public void ejecutarEnServidor(Supplier<NetworkEvent.Context> ctx) {
        Entity e = ctx.get().getSender().getVehicle();

        if (!(e instanceof EntidadCoche)) {
            return;
        }

        EntidadCoche coche = (EntidadCoche) e;

        coche.updateControls(winput, ainput, dinput, sinput, upinput);
    }

    public static ControlMensajesCoche fromBytes(FriendlyByteBuf buf) {
        return new ControlMensajesCoche(buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean());
    }


    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(winput);
        buf.writeBoolean(ainput);
        buf.writeBoolean(dinput);
        buf.writeBoolean(sinput);
        buf.writeBoolean(upinput);
    }

}
