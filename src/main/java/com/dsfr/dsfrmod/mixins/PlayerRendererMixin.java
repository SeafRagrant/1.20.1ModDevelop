package com.dsfr.dsfrmod.mixins;


import com.dsfr.dsfrmod.item.LXCrossbowSItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {
    @Inject(method = "getArmPose", at = @At(value = "RETURN", ordinal = 10), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private static void inject_getArmPose1(AbstractClientPlayer p_117795_, InteractionHand p_117796_, CallbackInfoReturnable<HumanoidModel.ArmPose> cir, ItemStack itemstack)
    {
        if (!p_117795_.swinging && itemstack.getItem() instanceof LXCrossbowSItem && LXCrossbowSItem.isCharged(itemstack)) {
            cir.setReturnValue(HumanoidModel.ArmPose.CROSSBOW_HOLD);
        }
    }
}
