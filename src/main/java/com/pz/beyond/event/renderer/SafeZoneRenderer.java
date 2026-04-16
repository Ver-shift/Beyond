package com.pz.beyond.event.renderer;

import com.mojang.blaze3d.vertex.*;
import com.pz.beyond.data.cache.SafeZoneClientCache;
import com.pz.beyond.util.BorderRenderUtil;
import net.minecraft.client.Camera;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber
public class SafeZoneRenderer {

    private static final ResourceLocation FORCEFIELD = ResourceLocation.parse("minecraft:textures/misc/forcefield.png");
    @SubscribeEvent
    public static void onRenderLevelStageEvent(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
            return;
        }


        int r = 0, g = 255, b = 0;
        int bottomAlpha = 180;
        int topAlpha = 0;

        int radius = SafeZoneClientCache.getRadius();
        int centerX = SafeZoneClientCache.getCenterX();
        int centerZ = SafeZoneClientCache.getCenterZ();
        if (radius <= 0) {
            return;
        }
        Camera camera = event.getCamera();
        PoseStack poseStack = event.getPoseStack();


        BorderRenderUtil.render(centerX, centerZ, radius, r, g, b, bottomAlpha, topAlpha, FORCEFIELD, camera, poseStack);

    }

}
