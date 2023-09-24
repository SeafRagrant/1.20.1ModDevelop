package com.dsfr.dsfrmod.mixins;

import com.dsfr.dsfrmod.item.LXCrossbowSItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {
    @Inject(method = "getFieldOfViewModifier", at = @At(value = "INVOKE", target = "net/minecraft/client/player/AbstractClientPlayer.isUsingItem ()Z"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void scopedMultiplier(CallbackInfoReturnable<Float> cir, float f, ItemStack itemstack){
        if(Minecraft.getInstance().options.getCameraType().isFirstPerson() && LXCrossbowSItem.isScoped()){
            cir.setReturnValue(0.1F);
        }
    }
}
