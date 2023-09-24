package com.dsfr.dsfrmod.entity;

import com.dsfr.dsfrmod.DsfrMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DsfrMod.MODID);

    public static final RegistryObject<EntityType<FireEA>> FireEA =
            ENTITIES.register("fire_arrow", () -> EntityType.Builder.<FireEA>of(FireEA::new, MobCategory.MISC).build("fire_arrow"));

    public static final RegistryObject<EntityType<IceEA>> IceEA =
            ENTITIES.register("ice_arrow", () -> EntityType.Builder.<IceEA>of(IceEA::new, MobCategory.MISC).build("ice_arrow"));

    public static final RegistryObject<EntityType<ElectronEA>> ElectronEA =
            ENTITIES.register("electron_arrow", () -> EntityType.Builder.<ElectronEA>of(ElectronEA::new, MobCategory.MISC).build("electron_arrow"));

    public static final RegistryObject<EntityType<EffectsArea>> EffectsArea =
            ENTITIES.register("effects_area", () -> EntityType.Builder.<EffectsArea>of(EffectsArea::new, MobCategory.MISC).build("effects_area"));


    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }
}
