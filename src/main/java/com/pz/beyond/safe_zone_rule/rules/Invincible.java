package com.pz.beyond.safe_zone_rule.rules;

import com.pz.beyond.safe_zone_rule.ISafeZoneRuleListener;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 安全区内无敌（不受伤害）
 */
@EventBusSubscriber
public class Invincible implements ISafeZoneRuleListener {

    @Override
    public void hurt(SafeZoneContext context) {

    }

    /**
     * 监听伤害事件，如果在安全区内则取消伤害
     */


    @Override
    public void toSafeZone(SafeZoneContext context) {
        // 进入安全区时的逻辑
    }

    @Override
    public void fromSafeZone(SafeZoneContext context) {
        // 离开安全区时的逻辑
    }
}
