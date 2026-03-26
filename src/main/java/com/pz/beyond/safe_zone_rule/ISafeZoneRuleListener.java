package com.pz.beyond.safe_zone_rule;


import net.minecraft.server.level.ServerPlayer;

/**
 * 给出入规则设置的接口
 */
public interface ISafeZoneRuleListener {

    /**
     * 从安全区离开
     */
    default void fromSafeZone(SafeZoneContext context){

    }

    /**
     * 从外部进入安全区
     */
    default void toSafeZone(SafeZoneContext context){

    }

    /**
     * 受到伤害时触发
     */
    default void hurt(SafeZoneContext context){

    }

    /**
     * 在安全区内每tick触发
     */
    default void onSafeZoneTick(SafeZoneContext context){

    }

    /**
     * 在安全区外每tick触发
     */
    default void outSideSafeZoneTick(SafeZoneContext context){

    }

    record SafeZoneContext(ServerPlayer player){

    }
}
