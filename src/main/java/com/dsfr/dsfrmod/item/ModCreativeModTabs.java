package com.dsfr.dsfrmod.item;

import com.dsfr.dsfrmod.DsfrMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final String TUTORIAL_TAB_STRING = "creativetab.dsfr_tab";
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DsfrMod.MODID);

    public static final RegistryObject<CreativeModeTab> DSFR_TAB = CREATIVE_MODE_TABS.register("dsfr_tab",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(ModItems.LXCROSSBOWS.get()))
                    .title(Component.translatable(TUTORIAL_TAB_STRING))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.LXCROSSBOWS.get());
//                        pOutput.accept(ModItems.DCrossBow.get());
                        pOutput.accept(Items.DIAMOND);
                    })
                    .build());


    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
