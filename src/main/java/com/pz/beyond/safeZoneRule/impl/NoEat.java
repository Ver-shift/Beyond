package com.pz.beyond.safeZoneRule.impl;

import com.pz.beyond.safeZoneRule.ISafeZoneRuleListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodData;

public class NoEat implements ISafeZoneRuleListener {
    @Override
    public void onSafeZoneTick(SafeZoneContext context) {
        ServerPlayer player = context.player();
        FoodData foodData = player.getFoodData();
        if (player.tickCount % 20 == 0) {
            foodData.eat(2, 2.0f);
        }

    }
}
