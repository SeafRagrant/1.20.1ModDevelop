package com.dsfr.dsfrmod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public class ElectronEffect extends MobEffect {
    protected ElectronEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    public void applyInstantenousEffect(@Nullable Entity p_19462_, @Nullable Entity p_19463_, LivingEntity p_19464_, int p_19465_, double p_19466_){
        if(this == ModEffects.ELECTRON.get()){
            if(Math.ceil(p_19464_.getMaxHealth())/Math.ceil(p_19464_.getHealth()) >= 5){
                p_19464_.kill();
            }
        }
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }

    @Override
    public boolean isDurationEffectTick(int p_19444_, int p_19445_) {
        return p_19444_ >= 1;
    }
}
