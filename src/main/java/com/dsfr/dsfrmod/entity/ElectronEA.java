package com.dsfr.dsfrmod.entity;

import com.dsfr.dsfrmod.effect.ModEffects;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class ElectronEA extends EnergyArrow{
    private static final int EXPOSED_POTION_DECAY_TIME = 600;
    private static final int NO_EFFECT_COLOR = -1;
    private static final EntityDataAccessor<Integer> ID_EFFECT_COLOR = SynchedEntityData.defineId(ElectronEA.class, EntityDataSerializers.INT);
    private static final byte EVENT_POTION_PUFF = 0;

    public ElectronEA(EntityType< ? extends ElectronEA> p_36858_, Level p_36859_) {
        super(p_36858_, p_36859_);
    }

    public ElectronEA(Level p_36861_, double p_36862_, double p_36863_, double p_36864_) {
        super(ModEntities.ElectronEA.get(), p_36862_, p_36863_, p_36864_, p_36861_);
    }

    public ElectronEA(Level p_36866_, LivingEntity p_36867_) {
        super(ModEntities.ElectronEA.get(), p_36867_, p_36866_);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_EFFECT_COLOR, 9771762);
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.makeParticle(1);
                }
            } else {
                this.makeParticle(2);
            }
//            if (!this.inGround) {
//                this.makeParticle(2);
//            }
        } else if (this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600){
//        else if (this.inGround && this.inGroundTime != 0 && !this.effects.isEmpty() && this.inGroundTime >= 600) {
            this.level().broadcastEntityEvent(this, (byte)0);
//            this.potion = Potions.EMPTY;
//            this.effects.clear();
            this.entityData.set(ID_EFFECT_COLOR, 9771762);
        }

    }

    protected MobEffectInstance getEffect(){
        return new MobEffectInstance(ModEffects.ELECTRON.get(), 20, 0);
    }

    protected ParticleOptions getParticle(){
        return ParticleTypes.DRAGON_BREATH;
    }

    private void makeParticle(int p_36877_) {
        int i = this.getColor();
        if (i != -1 && p_36877_ > 0) {
            double d0 = (double)(i >> 16 & 255) / 255.0D;
            double d1 = (double)(i >> 8 & 255) / 255.0D;
            double d2 = (double)(i >> 0 & 255) / 255.0D;

            for(int j = 0; j < p_36877_; ++j) {
                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
            }

        }
    }

    public int getColor() {
        return this.entityData.get(ID_EFFECT_COLOR);
    }

    public void handleEntityEvent(byte p_36869_) {
        if (p_36869_ == 0) {
            int i = this.getColor();
            if (i != -1) {
                double d0 = (double)(i >> 16 & 255) / 255.0D;
                double d1 = (double)(i >> 8 & 255) / 255.0D;
                double d2 = (double)(i >> 0 & 255) / 255.0D;

                for(int j = 0; j < 20; ++j) {
                    this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
                }
            }
        } else {
            super.handleEntityEvent(p_36869_);
        }

    }

}
