package com.dsfr.dsfrmod.mixins;

import com.dsfr.dsfrmod.item.LXCrossbowSItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MouseHandler.class)
public abstract class MouseHandleMixin {

    private double accumulatedDX$Old;
    private double accumulatedDY$Old;

    private double block$d5;

    @Accessor("accumulatedDX")
    public abstract double getAccumulatedDX();

    @Accessor("accumulatedDY")
    public abstract double getAccumulatedDY();

    @Accessor("accumulatedDX")
    public abstract void setAccumulatedDX(double d);

    @Accessor("accumulatedDY")
    public abstract void setAccumulatedDY(double d);

    @Accessor("minecraft")
    public abstract Minecraft getMinecraft();

    @Inject(method = "turnPlayer", at = @At(value = "INVOKE", target = "net/minecraft/util/SmoothDouble.reset ()V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void turnPlayer_Mixin(CallbackInfo ci, double d0, double d1, double d4, double d5, double d6){
        accumulatedDX$Old = getAccumulatedDX();
        accumulatedDY$Old = getAccumulatedDY();
        block$d5 = d5;
    }

    @ModifyVariable(method = "turnPlayer", at = @At(value = "STORE"), name = "d2")
    public double turnPlayer_Mixin_d2(double d2old){
        if(getMinecraft().options.getCameraType().isFirstPerson() && LXCrossbowSItem.isScoped()){
            return accumulatedDX$Old * block$d5;
        }
        return d2old;
    }

    @ModifyVariable(method = "turnPlayer", at = @At(value = "STORE"), name = "d3")
    public double turnPlayer_Mixin_d3(double d3old){
        if(getMinecraft().options.getCameraType().isFirstPerson() && LXCrossbowSItem.isScoped()){
            return accumulatedDY$Old * block$d5;
        }
        return d3old;
    }

}
