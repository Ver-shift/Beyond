package com.pz.beyond.safeZoneRule;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public interface ISafeZoneRuleListener {

    default void fromSafeZone(SafeZoneContext context){}

    default void toSafeZone(SafeZoneContext context){}

    default void hurt(SafeZoneContext context , LivingDamageEvent.Pre event){}

    default void onSafeZoneTick(SafeZoneContext context){}

    default void outSideSafeZoneTick(SafeZoneContext context){}

    record SafeZoneContext(ServerPlayer player){}

}
