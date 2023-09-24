package com.dsfr.dsfrmod.effect;

import com.dsfr.dsfrmod.DsfrMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DsfrMod.MODID);

    public static final RegistryObject<MobEffect> Frozen = MOB_EFFECTS.register("frozen",
            () -> new IceEffect(MobEffectCategory.HARMFUL, 1764594));

    public static final RegistryObject<MobEffect> Fervor = MOB_EFFECTS.register("fervor",
            () -> new FireEffect(MobEffectCategory.HARMFUL, 15890202));

    public static final RegistryObject<MobEffect> ELECTRON = MOB_EFFECTS.register("electron",
            () -> new ElectronEffect(MobEffectCategory.HARMFUL, 9771762));

    public static void register(IEventBus eventbus){
        MOB_EFFECTS.register(eventbus);
    }
}
