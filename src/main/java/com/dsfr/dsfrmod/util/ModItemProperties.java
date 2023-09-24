package com.dsfr.dsfrmod.util;

import com.dsfr.dsfrmod.item.LXCrossbowSItem;
import com.dsfr.dsfrmod.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        defineBow(ModItems.LXCROSSBOWS.get());
    }


    private static void defineBow(Item item){
        ItemProperties.register(item, new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity, n) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return LXCrossbowSItem.isCharged(itemStack) ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / (float)LXCrossbowSItem.getChargeDuration(itemStack);
            }
        });
        ItemProperties.register(item, new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity, n) -> {
            return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && !LXCrossbowSItem.isCharged(itemStack) ? 1.0F : 0.0F;
        });
        ItemProperties.register(item, new ResourceLocation("charged"), (itemStack, clientLevel, livingEntity, n) -> {
            return LXCrossbowSItem.isCharged(itemStack) ? 1.0F : 0.0F;
        });
        ItemProperties.register(item, new ResourceLocation("mode"), (itemStack, clientLevel, livingEntity, n) -> {
            if(LXCrossbowSItem.isCharged(itemStack)){
                return LXCrossbowSItem.Mode(itemStack);
            }
            return 0.0F;
        });
    }
}
