package com.example.bettervehicles.ui.Car;

import com.example.bettervehicles.BetterVehicles;
import com.example.bettervehicles.entidad.personalizado.EntidadCoche;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class InterfazCoche extends PantallaBase<ContenedorCoche> {

    private static final ResourceLocation TEXTURA_COCHE_GUI = new ResourceLocation(BetterVehicles.MODID, "textures/gui/gui_car.png");

    private static final int fontColor = 4210752;

    private Inventory jugInv;
    private EntidadCoche coche;

    public InterfazCoche(ContenedorCoche contenedorCoche, Inventory jugInv, Component title) {
        super(TEXTURA_COCHE_GUI, contenedorCoche, jugInv, title);
        this.jugInv = jugInv;
        this.coche = contenedorCoche.getCoche();

        imageWidth = 176;
        imageHeight = 166;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);

        //Titles
        guiGraphics.drawString(font, coche.getDisplayName().getVisualOrderText(), 8, 6, fontColor, false);
        guiGraphics.drawString(font, jugInv.getDisplayName().getVisualOrderText(), 8, this.imageHeight - 96 + 4, fontColor, false);

    }

}
