package com.pz.beyond.safeZoneRule.impl;

import com.pz.beyond.safeZoneRule.ISafeZoneRuleListener;
import net.minecraft.server.level.ServerPlayer;

public class AutoRegeneration implements ISafeZoneRuleListener {

    @Override
    public void onSafeZoneTick(SafeZoneContext context) {
        ServerPlayer player = context.player();
        if (player.tickCount % 20 == 0 ) {
            player.heal(1.0f);
        }
    }
}
