package com.pz.beyond.safeZoneRule;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public interface ISafeZoneRuleListener {

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
