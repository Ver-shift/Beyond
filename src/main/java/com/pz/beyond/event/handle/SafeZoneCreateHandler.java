package com.pz.beyond.event.handle;

import com.pz.beyond.data.PlayerStateData;
import com.pz.beyond.data.SafeZoneData;
import com.pz.beyond.event.custom.PlayerStateChangeEvent;
import com.pz.beyond.event.handle.PlayerStateDataHandle;
import com.pz.beyond.registry.ModAttachments;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
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

    /**
     * 玩家tick事件
     * 检测玩家进出安全区并触发状态变更事件
     */
    @SubscribeEvent
    public static void onPlayerTickEvent(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        PlayerStateData playerStateData = player.getData(ModAttachments.PLAYER_STATE_DATA);
        PlayerStateData.PlayerState currentState = playerStateData.getPlayerState();

        if (player.level() instanceof ServerLevel serverLevel) {
            SafeZoneData safeZone = serverLevel.getDataStorage().computeIfAbsent(
                    new SavedData.Factory<>(SafeZoneData::create, SafeZoneData::load), "SafeZone");

            boolean isInSafeZone = safeZone.isWithinSafeZone(player.getX(), player.getZ());

            // 离开安全区
            if (!isInSafeZone && currentState == PlayerStateData.PlayerState.INSIDE_SAFETY_ZONE) {
                playerStateData.setPlayerState(PlayerStateData.PlayerState.IN_GAME);
                player.setData(ModAttachments.PLAYER_STATE_DATA, playerStateData);

                // 触发状态变更事件
                NeoForge.EVENT_BUS.post(new PlayerStateChangeEvent(player,
                        PlayerStateData.PlayerState.INSIDE_SAFETY_ZONE,
                        PlayerStateData.PlayerState.IN_GAME));

                // 触发离开安全区
                PlayerStateDataHandle.handleFromSafeZone(playerStateData, serverPlayer);

            // 进入安全区
            } else if (isInSafeZone && currentState == PlayerStateData.PlayerState.IN_GAME) {
                playerStateData.setPlayerState(PlayerStateData.PlayerState.INSIDE_SAFETY_ZONE);
                player.setData(ModAttachments.PLAYER_STATE_DATA, playerStateData);

                // 触发状态变更事件
                NeoForge.EVENT_BUS.post(new PlayerStateChangeEvent(player,
                        PlayerStateData.PlayerState.IN_GAME,
                        PlayerStateData.PlayerState.INSIDE_SAFETY_ZONE));

                // 触发进入安全区
                PlayerStateDataHandle.handleToSafeZone(playerStateData, serverPlayer);
            }
        }
    }
}
