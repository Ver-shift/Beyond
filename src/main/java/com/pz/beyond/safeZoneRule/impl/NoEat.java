package com.pz.beyond.safeZoneRule.impl;

import com.pz.beyond.data.SafeZoneData;
import com.pz.beyond.registry.ModAttachments;
import com.pz.beyond.safeZoneRule.ISafeZoneRuleListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

public class NoEat implements ISafeZoneRuleListener {
    @Override
    public void onSafeZoneTick(SafeZoneContext context) {
        ServerPlayer player = context.player();
        FoodData foodData = player.getFoodData();
        SafeZoneData safeZone = player.level().getServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(new SavedData.Factory<>(SafeZoneData::create, SafeZoneData::load), "SafeZone");
        if (player.tickCount % 20 == 0 && safeZone.isWithinSafeZone(player.getX(),player.getZ())) {
            foodData.eat(2, 2.0f);
        }

    }
}
