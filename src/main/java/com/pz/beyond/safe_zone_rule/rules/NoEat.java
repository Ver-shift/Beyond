package com.pz.beyond.safe_zone_rule.rules;


import com.pz.beyond.safe_zone_rule.ISafeZoneRuleListener;
import net.minecraft.world.food.FoodData;

/**
 * 安全区内自动恢复饱食度
 */
public class NoEat implements ISafeZoneRuleListener {

    /**
     * 在安全区内每tick恢复饱食度
     * @param context
     */
    @Override
    public void onSafeZoneTick(SafeZoneContext context) {
        var player = context.player();
        FoodData foodData = player.getFoodData();
        if (player.tickCount % 20 == 0) {
            foodData.eat(2, 2);
        }
    }
}
