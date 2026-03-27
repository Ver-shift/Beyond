package com.pz.beyond.event;

import com.pz.beyond.data.PlayerStateData;
import com.pz.beyond.data.SafeZoneData;
import com.pz.beyond.event.custom.PlayerStateChangeEvent;
import com.pz.beyond.network.SafeZonePayload;
import com.pz.beyond.registry.ModAttachments;
import com.pz.beyond.safeZoneRule.ISafeZoneRuleListener;
import com.pz.beyond.util.SafeZonePayloadUtil;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

@EventBusSubscriber
public class SafeZoneHandler {

    /**
     * 这个事件控制 安全区的初始数据
     * @param event
     */
    @SubscribeEvent
    public static void onServerStartedEvent(ServerStartedEvent event) {
        ServerLevel overworld = event.getServer().getLevel(Level.OVERWORLD);
        SafeZoneData safeZone = overworld.getDataStorage().computeIfAbsent(new SavedData.Factory<>(SafeZoneData::create, SafeZoneData::load), "SafeZone");
        if (!safeZone.isFirst()){
            safeZone.updateCenter(overworld.getLevel().getSharedSpawnPos(),event.getServer().overworld(),safeZone);
            safeZone.addRadius(15,event.getServer().overworld(),safeZone);
            safeZone.setFirst(true);
        }
    }


    @SubscribeEvent
    public static void onPlayerDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer){
            PlayerStateData data = serverPlayer.getData(ModAttachments.PLAYER_STATE);
            List<ISafeZoneRuleListener> listeners = data.getListeners();


            for (ISafeZoneRuleListener listener : listeners){
                if (data.getPlayerState() == PlayerStateData.PlayerState.INSIDE_SAFETY_ZONE){
                   listener.hurt(event);
                }
            }

        }
    }
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            SafeZoneData data = serverPlayer.server.getLevel(Level.OVERWORLD).getDataStorage()
                    .computeIfAbsent(new SavedData.Factory<>(SafeZoneData::create, SafeZoneData::load), "SafeZone");
            SafeZonePayloadUtil.safeZoneToPlayer(serverPlayer,data);
        }
    }


}
