package com.dsfr.dsfrmod.client;

import com.dsfr.dsfrmod.DsfrMod;
import com.dsfr.dsfrmod.item.LXCrossbowSItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class Scoped{
    private static final ResourceLocation LX_SCOPED = new ResourceLocation(DsfrMod.MODID, "textures/gui/lx_scoped.png");

    public static final IGuiOverlay SCOPED_SCREEN = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        gui.setupOverlayRenderState(true, false);
        renderScopedOverlay(gui, guiGraphics, screenWidth, screenHeight);
    });

    private static void renderScopedOverlay(ForgeGui gui, GuiGraphics guiGraphics, int screenWidth, int screenHeight){
        Minecraft minecraft = gui.getMinecraft();

        if(minecraft.options.getCameraType().isFirstPerson()){
            if(LXCrossbowSItem.isScoped()) {
                float f = (float) Math.min(screenWidth, screenHeight);
                float f1 = Math.min((float) screenWidth / f, (float) screenHeight / f);
                int i = Mth.floor(f * f1);
                int j = Mth.floor(f * f1);
                int k = (screenWidth - i) / 2;
                int l = (screenHeight - j) / 2;
                int i1 = k + i;
                int j1 = l + j;
                guiGraphics.blit(LX_SCOPED, k, l, -90, 0.0F, 0.0F, i, j, i, j);
                guiGraphics.fill(RenderType.guiOverlay(), 0, j1, screenWidth, screenHeight, -90, -16777216);
                guiGraphics.fill(RenderType.guiOverlay(), 0, 0, screenWidth, l, -90, -16777216);
                guiGraphics.fill(RenderType.guiOverlay(), 0, l, k, j1, -90, -16777216);
                guiGraphics.fill(RenderType.guiOverlay(), i1, l, screenWidth, j1, -90, -16777216);
            }

        }
    }
}
