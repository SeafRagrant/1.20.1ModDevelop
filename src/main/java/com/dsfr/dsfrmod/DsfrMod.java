package com.dsfr.dsfrmod;

import com.dsfr.dsfrmod.client.Display;
import com.dsfr.dsfrmod.client.Scoped;
import com.dsfr.dsfrmod.effect.ModEffects;
import com.dsfr.dsfrmod.entity.ModEntities;
import com.dsfr.dsfrmod.item.LXCrossbowSItem;
import com.dsfr.dsfrmod.item.ModCreativeModTabs;
import com.dsfr.dsfrmod.item.ModItems;
import com.dsfr.dsfrmod.render.ElectronEARenderer;
import com.dsfr.dsfrmod.render.FireEARenderer;
import com.dsfr.dsfrmod.render.IceEARenderer;
import com.dsfr.dsfrmod.util.ModItemProperties;
import com.dsfr.dsfrmod.util.ScopedKB;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import static net.minecraft.world.inventory.InventoryMenu.BLOCK_ATLAS;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DsfrMod.MODID)
public class DsfrMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "dsfrmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public DsfrMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);
        ModEntities.register(modEventBus);
        ModEffects.register(modEventBus);

        modEventBus.addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    private void clientSetup(final FMLCommonSetupEvent event) {
        ModItemProperties.addCustomItemProperties();
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");

        }

        @SubscribeEvent
        public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
//            event.registerEntityRenderer(EntityInit.RE8DIMI.get(), RenderRe8Dimi::new);

            //添加我们的投掷物的渲染事件
            event.registerEntityRenderer(ModEntities.IceEA.get(), IceEARenderer::new);
            event.registerEntityRenderer(ModEntities.FireEA.get(), FireEARenderer::new);
            event.registerEntityRenderer(ModEntities.ElectronEA.get(), ElectronEARenderer::new);
            event.registerEntityRenderer(ModEntities.EffectsArea.get(), NoopRenderer::new);
        }

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            event.register(ScopedKB.SCOPED_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlay(RegisterGuiOverlaysEvent event){
            //渲染hud界面
            event.registerAbove(VanillaGuiOverlay.SPYGLASS.id(), "lx_scoped", Scoped.SCOPED_SCREEN);
            event.registerAbove(VanillaGuiOverlay.POTION_ICONS.id(), "display", Display.DISPLAY);
        }
    }
    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ForgeModEvents
    {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event){
            Minecraft client = Minecraft.getInstance();
            LocalPlayer player = client.player;
            if(player != null && ScopedKB.SCOPED_KEY.isDown()){
                ItemStack itemStack = player.getMainHandItem();
                if(itemStack.is(ModItems.LXCROSSBOWS.get()) && LXCrossbowSItem.isCharged(itemStack)){
                    LXCrossbowSItem.setScoped(true);
                }else{
                    LXCrossbowSItem.setScoped(false);
                }
            }else {
                LXCrossbowSItem.setScoped(false);
            }

        }

        @SubscribeEvent
        public static void onHurt(LivingDamageEvent event){
            LivingEntity entity = event.getEntity();
            Entity causingEntity = event.getSource().getEntity();

//            if(event.getSource().getDirectEntity() instanceof AreaEffectCloud){
//                Display.isHurt = causingEntity != null && causingEntity.equals(Minecraft.getInstance().player) && entity.equals(Display.InCrossHirEntity);
//            }else{
//                Display.isHurt = causingEntity != null && causingEntity.equals(Minecraft.getInstance().player);
//            }
            Display.isHurt = causingEntity != null && causingEntity.equals(Minecraft.getInstance().player);


            Display.Damage = event.getAmount();
        }


        @SubscribeEvent
        public static void OnTick(TickEvent.PlayerTickEvent event){
            Display.SetLivingEntity();
            Display.updateScale(Display.InCrossHirEntity);
            Display.NumberDisplayTime();
        }
    }
}
