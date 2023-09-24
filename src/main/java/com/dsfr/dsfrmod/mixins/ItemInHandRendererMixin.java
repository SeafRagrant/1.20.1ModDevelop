package com.dsfr.dsfrmod.mixins;

import com.dsfr.dsfrmod.item.LXCrossbowSItem;
import com.dsfr.dsfrmod.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin{

    @Inject(method = "evaluateWhichHandsToRender", at = @At(value = "INVOKE_ASSIGN", target = "net/minecraft/client/player/LocalPlayer.getOffhandItem ()Lnet/minecraft/world/item/ItemStack;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private static void inject_evaluateWhichHandsToRender(LocalPlayer p_172915_, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir, ItemStack itemstack, ItemStack itemstack1)
    {

        if(itemstack.is(ModItems.LXCROSSBOWS.get()) || itemstack1.is(ModItems.LXCROSSBOWS.get()))
        {
            if(p_172915_.isUsingItem())
            {
                cir.setReturnValue(UsingItemLikaBow(p_172915_));
            }
            else
            {
                cir.setReturnValue(isChargedLXCrossbowS(p_172915_.getMainHandItem()) ? ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY : ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS);
            }
        }
    }

    private static ItemInHandRenderer.HandRenderSelection UsingItemLikaBow(LocalPlayer p_172917_)
    {
        ItemStack itemstack = p_172917_.getUseItem();
        InteractionHand interactionhand = p_172917_.getUsedItemHand();
        if (!itemstack.is(ModItems.LXCROSSBOWS.get())) {
            return interactionhand == InteractionHand.MAIN_HAND && isChargedLXCrossbowS(p_172917_.getOffhandItem()) ? ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY : ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS;
        } else {
            return ItemInHandRenderer.HandRenderSelection.onlyForHand(interactionhand);
        }
    }

    private static boolean isChargedLXCrossbowS(ItemStack p_172913_)
    {
        return (p_172913_.is(ModItems.LXCROSSBOWS.get()) && LXCrossbowSItem.isCharged(p_172913_)) || (p_172913_.is(Items.CROSSBOW) && CrossbowItem.isCharged(p_172913_));
    }

    @Accessor("minecraft")
    public abstract Minecraft getMinecraft();

    @Invoker("renderItem")
    public abstract void invokeRenderItem(LivingEntity p_270072_, ItemStack p_270793_, ItemDisplayContext p_270837_, boolean p_270203_, PoseStack p_270974_, MultiBufferSource p_270686_, int p_270103_);

    @Invoker("applyItemArmTransform")
    public abstract void invokeApplyItemArmTransform(PoseStack p_109383_, HumanoidArm p_109384_, float p_109385_);

    @Invoker("applyItemArmAttackTransform")
    public abstract void invokeApplyItemArmAttackTransform(PoseStack p_109336_, HumanoidArm p_109337_, float p_109338_);

    @Inject(method = "renderArmWithItem", at = @At(value = "HEAD"), cancellable = true)
    private void inject_renderArmWithItem2(AbstractClientPlayer p_109372_, float p_109373_, float p_109374_, InteractionHand p_109375_, float p_109376_, ItemStack p_109377_, float p_109378_, PoseStack p_109379_, MultiBufferSource p_109380_, int p_109381_, CallbackInfo ci){
        if(LXCrossbowSItem.isScoped()){
            ci.cancel();
        }
    }

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "com/mojang/blaze3d/vertex/PoseStack.pushPose ()V", shift = At.Shift.AFTER), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void inject_renderArmWithItem(AbstractClientPlayer p_109372_, float p_109373_, float p_109374_, InteractionHand p_109375_, float p_109376_, ItemStack p_109377_, float p_109378_, PoseStack p_109379_, MultiBufferSource p_109380_, int p_109381_, CallbackInfo ci, boolean flag, HumanoidArm humanoidarm) {
        if (p_109377_.getItem() instanceof LXCrossbowSItem) {
            boolean flag1 = LXCrossbowSItem.isCharged(p_109377_);
            boolean flag2 = humanoidarm == HumanoidArm.RIGHT;
            int i = flag2 ? 1 : -1;
            if (p_109372_.isUsingItem() && p_109372_.getUseItemRemainingTicks() > 0 && p_109372_.getUsedItemHand() == p_109375_) {
                invokeApplyItemArmTransform(p_109379_, humanoidarm, p_109378_);
                p_109379_.translate((float) i * -0.4785682F, -0.094387F, 0.05731531F);
                p_109379_.mulPose(Axis.XP.rotationDegrees(-11.935F));
                p_109379_.mulPose(Axis.YP.rotationDegrees((float) i * 65.3F));
                p_109379_.mulPose(Axis.ZP.rotationDegrees((float) i * -9.785F));
                float f9 = (float) p_109377_.getUseDuration() - ((float) getMinecraft().player.getUseItemRemainingTicks() - p_109373_ + 1.0F);
                float f13 = f9 / (float) LXCrossbowSItem.getChargeDuration(p_109377_);
                if (f13 > 1.0F) {
                    f13 = 1.0F;
                }

                if (f13 > 0.1F) {
                    float f16 = Mth.sin((f9 - 0.1F) * 1.3F);
                    float f3 = f13 - 0.1F;
                    float f4 = f16 * f3;
                    p_109379_.translate(f4 * 0.0F, f4 * 0.004F, f4 * 0.0F);
                }

                p_109379_.translate(f13 * 0.0F, f13 * 0.0F, f13 * 0.04F);
                p_109379_.scale(1.0F, 1.0F, 1.0F + f13 * 0.2F);
                p_109379_.mulPose(Axis.YN.rotationDegrees((float) i * 45.0F));
            } else {
                float f = -0.4F * Mth.sin(Mth.sqrt(p_109376_) * (float) Math.PI);
                float f1 = 0.2F * Mth.sin(Mth.sqrt(p_109376_) * ((float) Math.PI * 2F));
                float f2 = -0.2F * Mth.sin(p_109376_ * (float) Math.PI);
                p_109379_.translate((float) i * f, f1, f2);
                invokeApplyItemArmTransform(p_109379_, humanoidarm, p_109378_);
                invokeApplyItemArmAttackTransform(p_109379_, humanoidarm, p_109376_);
                if (flag1 && p_109376_ < 0.001F && flag) {
                    p_109379_.translate((float) i * -0.641864F, 0.0F, 0.0F);
                    p_109379_.mulPose(Axis.YP.rotationDegrees((float) i * 10.0F));
                }
            }

            invokeRenderItem(p_109372_, p_109377_, flag2 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !flag2, p_109379_, p_109380_, p_109381_);
            p_109379_.popPose();
            ci.cancel();
        }
    }
}
