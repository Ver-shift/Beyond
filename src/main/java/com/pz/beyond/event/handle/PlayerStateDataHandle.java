package com.pz.beyond.event.handle;


import com.pz.beyond.data.PlayerStateData;
import com.pz.beyond.registry.ModAttachments;
import com.pz.beyond.safe_zone_rule.ISafeZoneRuleListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber
public class PlayerStateDataHandle {

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        var playerStateData = player.getData(ModAttachments.PLAYER_STATE_DATA);
        List<ISafeZoneRuleListener> listeners = playerStateData.getListeners();

        if (listeners.isEmpty()) {
            return;
        }

        // 使用副本遍历，避免并发修改异常
        List<ISafeZoneRuleListener> listenersCopy = new ArrayList<>(listeners);
        var context = new ISafeZoneRuleListener.SafeZoneContext(player);

        // 根据玩家状态调用对应接口
        for (ISafeZoneRuleListener listener : listenersCopy) {
            switch (playerStateData.getPlayerState()) {
                case INSIDE_SAFETY_ZONE -> handleOnSafeZoneTick(listener, context);
                case IN_GAME -> handleOutSideSafeZoneTick(listener, context);
            }
        }
    }

    @SubscribeEvent
    public static void hurt(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        var playerStateData = player.getData(ModAttachments.PLAYER_STATE_DATA);
        List<ISafeZoneRuleListener> listeners = playerStateData.getListeners();

        if (listeners.isEmpty()) {
            return;
        }

        List<ISafeZoneRuleListener> listenersCopy = new ArrayList<>(listeners);
        var context = new ISafeZoneRuleListener.SafeZoneContext((ServerPlayer) player);

        for (ISafeZoneRuleListener listener : listenersCopy) {
            handleHurt(listener, context);
        }
    }

    /**
     * 处理进入安全区
     */
    public static void handleToSafeZone(PlayerStateData playerStateData, ServerPlayer player) {
        List<ISafeZoneRuleListener> listeners = playerStateData.getListeners();
        if (listeners.isEmpty()) {
            return;
        }

        List<ISafeZoneRuleListener> listenersCopy = new ArrayList<>(listeners);
        var context = new ISafeZoneRuleListener.SafeZoneContext(player);

        for (ISafeZoneRuleListener listener : listenersCopy) {
            listener.toSafeZone(context);
        }
    }

    /**
     * 处理离开安全区
     */
    public static void handleFromSafeZone(PlayerStateData playerStateData, ServerPlayer player) {
        List<ISafeZoneRuleListener> listeners = playerStateData.getListeners();
        if (listeners.isEmpty()) {
            return;
        }

        List<ISafeZoneRuleListener> listenersCopy = new ArrayList<>(listeners);
        var context = new ISafeZoneRuleListener.SafeZoneContext(player);

        for (ISafeZoneRuleListener listener : listenersCopy) {
            listener.fromSafeZone(context);
        }
    }

    /**
     * 处理受到伤害
     */
    private static void handleHurt(ISafeZoneRuleListener listener, ISafeZoneRuleListener.SafeZoneContext context) {
        listener.hurt(context);
    }

    /**
     * 处理安全区内tick
     */
    private static void handleOnSafeZoneTick(ISafeZoneRuleListener listener, ISafeZoneRuleListener.SafeZoneContext context) {
        listener.onSafeZoneTick(context);
    }

    /**
     * 处理安全区外tick
     */
    private static void handleOutSideSafeZoneTick(ISafeZoneRuleListener listener, ISafeZoneRuleListener.SafeZoneContext context) {
        listener.outSideSafeZoneTick(context);
    }
}
