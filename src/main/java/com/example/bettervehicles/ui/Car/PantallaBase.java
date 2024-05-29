package com.example.bettervehicles.ui.Car;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class PantallaBase<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    protected ResourceLocation texture;
    protected List<HoverArea> hoverAreas;

    public PantallaBase(ResourceLocation texture, T container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
        this.texture = texture;
        this.hoverAreas = new ArrayList();
    }

    public void render(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, x, y, partialTicks);
        this.renderTooltip(guiGraphics, x, y);
    }

    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(this.texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    public void drawCentered(GuiGraphics guiGraphics, Component text, int x, int y, int color) {
        drawCentered(this.font, guiGraphics, text, x, y, color);
    }

    public static void drawCentered(Font font, GuiGraphics guiGraphics, Component text, int x, int y, int color) {
        int width = font.width(text);
        guiGraphics.drawString(font, text, x - width / 2, y, color, false);
    }

    public static class HoverArea {
        private final int posX;
        private final int posY;
        private final int width;
        private final int height;
        @Nullable
        private final Supplier<List<FormattedCharSequence>> tooltip;

        public HoverArea(int posX, int posY, int width, int height) {
            this(posX, posY, width, height, (Supplier)null);
        }

        public HoverArea(int posX, int posY, int width, int height, Supplier<List<FormattedCharSequence>> tooltip) {
            this.posX = posX;
            this.posY = posY;
            this.width = width;
            this.height = height;
            this.tooltip = tooltip;
        }
    }
}
