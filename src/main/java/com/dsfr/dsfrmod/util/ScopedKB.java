package com.dsfr.dsfrmod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class ScopedKB {

        public static final String KEY_CATEGORY_TUTORIAL = "key.category.dsfrmod.tutorial";
        public static final String KEY_SCOPED =  "key.dsfrmod.scoped";

        public static final KeyMapping SCOPED_KEY = new KeyMapping(KEY_SCOPED, KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z,KEY_CATEGORY_TUTORIAL);
}
