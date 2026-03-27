package com.pz.beyond.safeZoneRule;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public interface ISafeZoneRuleListener {

    /**
     * 从安全区离开
     * @param context
     */
    default void fromSafeZone(SafeZoneContext context){}

    /**
     * 回到安全区
     * @param context
     */
    default void toSafeZone(SafeZoneContext context){}

    /**
     * 玩家受伤
     * @param event
     */
    default void hurt(LivingDamageEvent.Pre event){}

    /**
     * 在安全区内的每个tick
     * @param context
     */
    default void onSafeZoneTick(SafeZoneContext context){}

    /**
     * 在安全区外的每个tick
     * @param context
     */
    default void outSideSafeZoneTick(SafeZoneContext context){}

    record SafeZoneContext(ServerPlayer player){}

}
