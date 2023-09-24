package com.dsfr.dsfrmod.datagen;

import com.dsfr.dsfrmod.DsfrMod;
import com.dsfr.dsfrmod.item.ModItems;
import com.dsfr.dsfrmod.item.ModCreativeModTabs;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class Language extends LanguageProvider {
    public Language(PackOutput output,String locale) {
        super(output, DsfrMod.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModItems.LXCROSSBOWS.get(),"LXCrossBowS");
        add(ModCreativeModTabs.TUTORIAL_TAB_STRING,"DsfrMod");
    }

}
