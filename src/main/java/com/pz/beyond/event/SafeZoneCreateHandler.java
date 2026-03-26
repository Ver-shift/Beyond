package com.pz.beyond.event;

import com.pz.beyond.data.PlayerStateData;
import com.pz.beyond.data.SafeZoneData;
import com.pz.beyond.registry.ModAttachments;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public class SafeZoneCreateHandler {

    /**
     * 这个事件控制 安全区的初始数据
     * @param event
     */
    @SubscribeEvent
    public static void onServerStartedEvent(ServerStartedEvent event) {
        ServerLevel overworld = event.getServer().getLevel(Level.OVERWORLD);
        SafeZoneData safeZone = overworld.getDataStorage().computeIfAbsent(new SavedData.Factory<>(SafeZoneData::create, SafeZoneData::load), "SafeZone");
        if (!safeZone.isFirst()){
            safeZone.updateCenter(overworld.getLevel().getSharedSpawnPos());
            safeZone.addRadius(15);
            safeZone.setFirst(true);
        }
    }
    @SubscribeEvent
    public static void onPlayerDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player player) {
            if(player.getData(ModAttachments.PLAYER_STATE) == PlayerStateData.PlayerState.INSIDE_SAFETY_ZONE) {
                event.setNewDamage(0);
            }
        }
    }

    @SubscribeEvent
    public static void  onPlayerTickEvent(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        PlayerStateData.PlayerState data = player.getData(ModAttachments.PLAYER_STATE);
        FoodData foodData = player.getFoodData();
        if (player.level() instanceof ServerLevel serverLevel) {
            SafeZoneData safeZone = serverLevel.getDataStorage().computeIfAbsent(new SavedData.Factory<>(SafeZoneData::create, SafeZoneData::load), "SafeZone");

            if (!safeZone.isWithinSafeZone(player.getX(), player.getZ()) && data == PlayerStateData.PlayerState.INSIDE_SAFETY_ZONE) {
                player.setData(ModAttachments.PLAYER_STATE, PlayerStateData.PlayerState.IN_GAME);

            }else if (safeZone.isWithinSafeZone(player.getX(), player.getZ()) && data == PlayerStateData.PlayerState.IN_GAME){
                player.setData(ModAttachments.PLAYER_STATE, PlayerStateData.PlayerState.INSIDE_SAFETY_ZONE);
            }
        }


        if (data == PlayerStateData.PlayerState.INSIDE_SAFETY_ZONE) {
            if (player.tickCount % 20 == 0) foodData.eat(2,2);
        }
    }



}
