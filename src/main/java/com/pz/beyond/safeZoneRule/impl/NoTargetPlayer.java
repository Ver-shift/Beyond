package com.pz.beyond.safeZoneRule.impl;

import com.pz.beyond.data.PlayerStateData;
import com.pz.beyond.registry.ModAttachments;
import com.pz.beyond.safeZoneRule.ISafeZoneRuleListener;
import com.pz.beyond.safeZoneRule.SafeZoneRule;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;

@SafeZoneRule
public class NoTargetPlayer implements ISafeZoneRuleListener {
    @Override
    public void onLivingChangeTarget(LivingChangeTargetEvent event) {
        if (event.getNewAboutToBeSetTarget() instanceof ServerPlayer player) {
            PlayerStateData data = player.getData(ModAttachments.PLAYER_STATE);
            if (data.getPlayerState() == PlayerStateData.PlayerState.INSIDE_SAFETY_ZONE) event.setCanceled(true);
        }
    }
}
