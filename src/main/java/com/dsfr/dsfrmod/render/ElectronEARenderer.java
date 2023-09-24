package com.dsfr.dsfrmod.render;

import com.dsfr.dsfrmod.DsfrMod;
import com.dsfr.dsfrmod.entity.ElectronEA;
import com.dsfr.dsfrmod.entity.IceEA;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class ElectronEARenderer extends EnergyArrowRenderer<ElectronEA> {
    public static final ResourceLocation ELECTRONEA__LOCATION = new ResourceLocation(DsfrMod.MODID, "textures/entity/electronea.png");

    public ElectronEARenderer(EntityRendererProvider.Context p_173917_) {
        super(p_173917_);
    }

    @Override
    public ResourceLocation getTextureLocation(ElectronEA p_114482_) {
        return ELECTRONEA__LOCATION;
    }

    public void vertex(Matrix4f p_254392_, Matrix3f p_254011_, VertexConsumer p_253902_, int p_254058_, int p_254338_, int p_254196_, float p_254003_, float p_254165_, int p_253982_, int p_254037_, int p_254038_, int p_254271_) {
        p_253902_.vertex(p_254392_, (float)p_254058_, (float)p_254338_, (float)p_254196_).color(149, 26, 242, 255).uv(p_254003_, p_254165_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_254271_).normal(p_254011_, (float)p_253982_, (float)p_254038_, (float)p_254037_).endVertex();
    }
}
