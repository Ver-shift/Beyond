package com.pz.beyond.safeZoneRule.impl;

import com.pz.beyond.data.PlayerStateData;
import com.pz.beyond.event.custom.PlayerStateChangeEvent;
import com.pz.beyond.registry.ModAttachments;
import com.pz.beyond.safeZoneRule.ISafeZoneRuleListener;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;

public class SafeZoneTransition implements ISafeZoneRuleListener {

    @Override
    public void fromSafeZone(SafeZoneContext context) {
        ServerPlayer serverPlayer = context.player();
        PlayerStateData data = serverPlayer.getData(ModAttachments.PLAYER_STATE);
        data.setPlayerState(PlayerStateData.PlayerState.IN_GAME);
        NeoForge.EVENT_BUS.post(new PlayerStateChangeEvent(serverPlayer, PlayerStateData.PlayerState.INSIDE_SAFETY_ZONE,PlayerStateData.PlayerState.IN_GAME));
        serverPlayer.setData(ModAttachments.PLAYER_STATE,data);
        System.out.println("离开");

    }

    @Override
    public void toSafeZone(SafeZoneContext context) {
        ServerPlayer serverPlayer = context.player();
        PlayerStateData data = serverPlayer.getData(ModAttachments.PLAYER_STATE);
        data.setPlayerState(PlayerStateData.PlayerState.INSIDE_SAFETY_ZONE);
        NeoForge.EVENT_BUS.post(new PlayerStateChangeEvent(serverPlayer,PlayerStateData.PlayerState.IN_GAME,PlayerStateData.PlayerState.INSIDE_SAFETY_ZONE));
        serverPlayer.setData(ModAttachments.PLAYER_STATE,data);
        System.out.println("进入");
    }
}
