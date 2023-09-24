package com.dsfr.dsfrmod.entity;

import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;

public class EffectsArea extends AreaEffectCloud {
//  public class EffectsArea extends Entity implements TraceableEntity {
    public EffectsArea(EntityType<? extends EffectsArea> p_19704_, Level p_19705_) {
        super(p_19704_, p_19705_);
    }

    public EffectsArea(Level p_19707_, double p_19708_, double p_19709_, double p_19710_) {
        this(ModEntities.EffectsArea.get(), p_19707_);
        this.setPos(p_19708_, p_19709_, p_19710_);
    }
}
