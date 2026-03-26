package com.pz.beyond.safe_zone_rule.rules;

import com.pz.beyond.safe_zone_rule.ISafeZoneRuleListener;

/**
 * 安全区内自动恢复生命值
 */
public class AutoRegeneration implements ISafeZoneRuleListener {

    @Override
    public void onSafeZoneTick(SafeZoneContext context) {
        var player = context.player();
        if (player.tickCount % 20 == 0) {
            player.heal(1.0f);
        }
    }
}
