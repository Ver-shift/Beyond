package com.pz.beyond.safeZoneRule.impl;

import com.pz.beyond.safeZoneRule.ISafeZoneRuleListener;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class Invincible implements ISafeZoneRuleListener {
    @Override
    public void hurt(SafeZoneContext context, LivingDamageEvent.Pre event) {
        event.setNewDamage(0);
    }
}
