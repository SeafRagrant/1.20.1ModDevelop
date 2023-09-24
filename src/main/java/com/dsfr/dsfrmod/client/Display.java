package com.dsfr.dsfrmod.client;

import com.dsfr.dsfrmod.DsfrMod;
import com.dsfr.dsfrmod.item.LXCrossbowSItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Optional;
import java.util.function.Predicate;

import static net.minecraft.world.level.BlockGetter.traverseBlocks;

public class Display{
    private static final ResourceLocation ENTITY_FRAME = new ResourceLocation(DsfrMod.MODID, "textures/gui/model_display.png");
    private static final ResourceLocation BAR_FRAME = new ResourceLocation(DsfrMod.MODID, "textures/gui/bar.png");
    private static final ResourceLocation DAMAGE_FRAME = new ResourceLocation(DsfrMod.MODID, "textures/gui/damage.png");
    private static final ResourceLocation HEALTH_FRAME = new ResourceLocation(DsfrMod.MODID, "textures/gui/health.png");

    private static final ResourceLocation ARMOR_ICON = new ResourceLocation("textures/gui/icons.png");

    private static final float RENDER_HEIGHT = 30;
    private static final float RENDER_WIDTH = 25;
    private static final float WIDTH = 42;
    private static final float HEIGHT = 42;

    private static int entityScale = 1;

    private static float xOffset;
    private static float yOffset;

    private static boolean isShow = false;
    public static LivingEntity InCrossHirEntity = null;

    private static int lifeTime = 0;

    private static int DamageDisplayTime = 0;

    private static float MaxHealth;
    private static float Health;
    private static int armor = 0;
    public static boolean isHurt = false;
    public static float Damage = 0.f;
    static Font font = Minecraft.getInstance().font;

    static Component name = null;

    private static Predicate<Entity> isVisible = entity -> !entity.isSpectator();
    public static final IGuiOverlay DISPLAY = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
//        HitResult result = ProjectileUtil.getHitResultOnViewVector(Minecraft.getInstance().getCameraEntity(), isVisible, 1.0D);

        if(isShow){
            renderFrame(guiGraphics);

            if(InCrossHirEntity != null){
                InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, (int) xOffset, (int) yOffset, entityScale, -80, -20, InCrossHirEntity);
            }
        }

        if(isHurt){
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.scale(3.f, 3.f, 3.f);
            String s = Integer.toString(Math.round(Damage));
            int sw = font.width(s);
//                minecraft.font.draw(matrix, s, (int) (x + (width / 2) - sw), (int) y + 5, color);
            guiGraphics.drawString(font, s, screenWidth/8+1, screenHeight/8 + 1, 0x000000);
            guiGraphics.drawString(font, s, screenWidth/8, screenHeight/8, 0xff0000);
            poseStack.popPose();
        }
    });

    private static void renderFrame(GuiGraphics guiGraphics){
        RenderSystem.enableBlend();
        //参数说明: 纹理, 在gui显示的x,y坐标, 在纹理的x,y起始坐标, 绘制的gui窗口大小和纹理大小, 这里是整张图渲染
        guiGraphics.blit(DAMAGE_FRAME, 43, 5, 0.f, 0.f, 80, 30, 80, 30);
        guiGraphics.blit(HEALTH_FRAME, 43, 5, 0.f, 0.f, Mth.floor(Health / MaxHealth * 80), 30, Mth.floor(Health / MaxHealth * 80), 30);
        guiGraphics.blit(BAR_FRAME, 43, 5, 0.f, 0.f, 80, 30, 80, 30);
        //参数说明: 纹理, 在gui显示的x,y坐标, 在纹理的x,y坐标, 纹理绘制的大小即  纹理的x, y分别加上大小, 这里是渲染选中的纹理区域
        guiGraphics.blit(ARMOR_ICON, 50, 36, 34, 9, 9, 9);
        guiGraphics.blit(ENTITY_FRAME, 5, 5, 0.f, 0.f, 42, 42, 42, 42);

        guiGraphics.drawString(font, name, 43 + (80 - font.width(name.getString()))/2, 8, 0xffffff);

        String h = (int)Health + "/" + (int)MaxHealth;
        guiGraphics.drawString(font, h, 43 + (80 - font.width(h))/2, 23, 0xffffff);

        guiGraphics.drawString(font, Integer.toString(armor), 63 + (40 - font.width(String.valueOf(armor)))/2, 36, 0xffffff);
    }

    public static void updateScale(LivingEntity entity) {
        if(entity == null) return;

        float height = entity.getBbHeight();
        float width = entity.getBbWidth();

        int scaleY = RENDER_HEIGHT < Mth.ceil(height) ? Mth.ceil(height/RENDER_HEIGHT) : Mth.ceil(RENDER_HEIGHT / height);
        int scaleX = RENDER_WIDTH < Mth.ceil(width) ? Mth.ceil(width / RENDER_WIDTH) : Mth.ceil(RENDER_WIDTH / width);
        entityScale = Math.min(scaleX, scaleY);

        if (entity instanceof Chicken) {
            entityScale *= 0.7;
        }

        if (entity instanceof Villager && entity.isSleeping()) {
            entityScale = entity.isBaby() ? 31 : 16;
        }

        xOffset = WIDTH / 2 + 3.5f;

        yOffset = HEIGHT / 2 + 22.f;
        if (entity instanceof Ghast) {
            entityScale *= 0.75;
            yOffset -= 15;
        }
        if (entity instanceof EnderDragon){
            entityScale *= 2;
        }
    }

    public static void SetLivingEntity(){
        Minecraft client = Minecraft.getInstance();


        LocalPlayer player = client.player;
        double reachDistance;
        if(player == null){
            reachDistance = 60.D;
        }else {
            reachDistance  =  client.player.isScoping()|| LXCrossbowSItem.isScoped() ? 600.D : 60.D;
        }

        // copy from net/minecraft/client/renderer/GameRenderer.java -> pick
        Entity viewer = client.getCameraEntity();

        if (viewer == null) {
            return;
        }
        Vec3 position = viewer.getEyePosition(0.F);
        Vec3 look = viewer.getViewVector(1.0F);
        Vec3 max = position.add(look.x * reachDistance, look.y * reachDistance, look.z * reachDistance);
        AABB searchBox =
                viewer.getBoundingBox().expandTowards(look.scale(reachDistance)).inflate(1.0D, 1.0D, 1.0D);

        BlockHitResult hitresult = clip(new ClipContext(position, max, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, client.player));
        EntityHitResult result = getEntityHitResult(viewer, position, max, searchBox,
                    isVisible, reachDistance * reachDistance);

        if(result != null && result.getEntity() instanceof LivingEntity livingEntity) {
            if(hitresult.getType().equals(BlockHitResult.Type.MISS) || hitresult.getType().equals(BlockHitResult.Type.BLOCK) && hitresult.getLocation().distanceTo(position) > livingEntity.distanceTo(client.player)){
                isShow = true;
                if(!livingEntity.equals(InCrossHirEntity)){
                    InCrossHirEntity = livingEntity;
                    MaxHealth = livingEntity.getMaxHealth();
                    if(InCrossHirEntity != null) {
                        name = InCrossHirEntity.getDisplayName();
                        armor = InCrossHirEntity.getArmorValue();
                    }
                }

                lifeTime = 0;
                Health = livingEntity.getHealth();
            }else {
                if(isShow){
                    lifeTime++;
                    if(lifeTime == 20){
                        isShow = false;
                    }
                }
            }
        }else{
            if (isShow){
                lifeTime++;
                if(lifeTime == 20){
                    isShow = false;
                }
            }
        }
    }

    public static void NumberDisplayTime(){
        if (isHurt){
            DamageDisplayTime ++;
            if(DamageDisplayTime == 20){
                isHurt = false;
                DamageDisplayTime = 0;
            }
        }else {
            if(DamageDisplayTime != 0)
                DamageDisplayTime = 0;
        }
    }

    // copy from net/minecraft/world/entity/projectile/ProjectileUtil.java -> getEntityHitResult
    private static EntityHitResult getEntityHitResult(Entity p_37288_, Vec3 p_37289_, Vec3 p_37290_, AABB p_37291_, Predicate<Entity> p_37292_, double p_37293_) {
        Level level = p_37288_.level();
        double d0 = p_37293_;
        Entity entity = null;
        Vec3 vec3 = null;

        for(Entity entity1 : level.getEntities(p_37288_, p_37291_, p_37292_)) {
            if(entity1 instanceof AreaEffectCloud) continue;
            AABB aabb = entity1.getBoundingBox().inflate((double)entity1.getPickRadius());
            Optional<Vec3> optional = aabb.clip(p_37289_, p_37290_);
            if (aabb.contains(p_37289_)) {
                if (d0 >= 0.0D) {
                    entity = entity1;
                    vec3 = optional.orElse(p_37289_);
                    d0 = 0.0D;
                }
            } else if (optional.isPresent()) {
                Vec3 vec31 = optional.get();
                double d1 = p_37289_.distanceToSqr(vec31);
                if (d1 < d0 || d0 == 0.0D) {
                    if (entity1.getRootVehicle() == p_37288_.getRootVehicle() && !entity1.canRiderInteract()) {
                        if (d0 == 0.0D) {
                            entity = entity1;
                            vec3 = vec31;
                        }
                    } else {
                        entity = entity1;
                        vec3 = vec31;
                        d0 = d1;
                    }
                }
            }
        }

        return entity == null ? null : new EntityHitResult(entity, vec3);
    }

    //copy from net/minecraft/world/level/BlockGetter.java -> clip
    //生物被block遮挡和原来函数逻辑一样,但是生物在水中的时候还是看得见的,故去掉fluid的处理
    private static BlockHitResult clip(ClipContext context) {
        return traverseBlocks(context.getFrom(), context.getTo(), context, (p_151359_, p_151360_) -> {
            BlockState blockstate = Minecraft.getInstance().level.getBlockState(p_151360_);
            if(!blockstate.canOcclude()){
                return null;
            }
            Vec3 vec3 = p_151359_.getFrom();
            Vec3 vec31 = p_151359_.getTo();
            VoxelShape voxelshape = p_151359_.getBlockShape(blockstate, Minecraft.getInstance().level, p_151360_);
            return clipWithInteractionOverride(vec3, vec31, p_151360_, voxelshape, blockstate);


        }, (p_275153_) -> {
            Vec3 vec3 = p_275153_.getFrom().subtract(p_275153_.getTo());
            return BlockHitResult.miss(p_275153_.getTo(), Direction.getNearest(vec3.x, vec3.y, vec3.z), BlockPos.containing(p_275153_.getTo()));
        });
    }

    // copy from net/minecraft/world/level/BlockGetter.java -> clipWithInteractionOverride
    private static BlockHitResult clipWithInteractionOverride(Vec3 p_45559_, Vec3 p_45560_, BlockPos p_45561_, VoxelShape p_45562_, BlockState p_45563_) {
        BlockHitResult blockhitresult = p_45562_.clip(p_45559_, p_45560_, p_45561_);
        if (blockhitresult != null) {
            BlockHitResult blockhitresult1 = p_45563_.getInteractionShape(Minecraft.getInstance().level, p_45561_).clip(p_45559_, p_45560_, p_45561_);
            if (blockhitresult1 != null && blockhitresult1.getLocation().subtract(p_45559_).lengthSqr() < blockhitresult.getLocation().subtract(p_45559_).lengthSqr()) {
                return blockhitresult.withDirection(blockhitresult1.getDirection());
            }
        }

        return blockhitresult;
    }
}
