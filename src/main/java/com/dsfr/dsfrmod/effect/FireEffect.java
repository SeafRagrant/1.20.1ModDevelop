package com.dsfr.dsfrmod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FireEffect extends MobEffect {

    protected FireEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    public void applyEffectTick(LivingEntity p_19467_, int p_19468_){
        p_19467_.hurt(p_19467_.damageSources().magic(), 1.0F);
    }

    public boolean isDurationEffectTick(int p_19444_, int p_19445_) {
        return p_19444_ % 2 == 0;
    }
}
