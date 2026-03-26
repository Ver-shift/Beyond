package com.pz.beyond.event;

import com.pz.beyond.data.PlayerStateData;
import com.pz.beyond.data.SafeZoneData;
import com.pz.beyond.event.custom.PlayerStateChangeEvent;
import com.pz.beyond.registry.ModAttachments;
import com.pz.beyond.safeZoneRule.ISafeZoneRuleListener;
import com.pz.beyond.safeZoneRule.impl.AutoRegeneration;
import com.pz.beyond.safeZoneRule.impl.Invincible;
import com.pz.beyond.safeZoneRule.impl.NoEat;
import com.pz.beyond.safeZoneRule.impl.SafeZoneTransition;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

@EventBusSubscriber
public class PlayerStateHandle {

    @SubscribeEvent
    public static void  onPlayerTickEvent(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer){
            PlayerStateData data = serverPlayer.getData(ModAttachments.PLAYER_STATE);
            List<ISafeZoneRuleListener> listeners = data.getListeners();

            ISafeZoneRuleListener.SafeZoneContext safeZoneContext = new ISafeZoneRuleListener.SafeZoneContext(serverPlayer);

            for (ISafeZoneRuleListener listener : listeners){
                switch (data.getPlayerState()){
                    case INSIDE_SAFETY_ZONE -> {
                        listener.onSafeZoneTick(safeZoneContext);
                    }
                    case IN_GAME -> {
                        listener.outSideSafeZoneTick(safeZoneContext);
                    }
                }

                /**
                 * 处理玩家状态切换，广播对应的事件
                 * @see PlayerStateChangeEvent
                 */
                if (serverPlayer.level() instanceof ServerLevel serverLevel) {
                    SafeZoneData safeZone = serverLevel.getDataStorage().computeIfAbsent(new SavedData.Factory<>(SafeZoneData::create, SafeZoneData::load), "SafeZone");

                    if (!safeZone.isWithinSafeZone(serverPlayer.getX(), serverPlayer.getZ()) && data.getPlayerState() == PlayerStateData.PlayerState.INSIDE_SAFETY_ZONE) {
                        listener.fromSafeZone(safeZoneContext);


                    }else if (safeZone.isWithinSafeZone(serverPlayer.getX(), serverPlayer.getZ()) && data.getPlayerState() == PlayerStateData.PlayerState.IN_GAME){
                        listener.toSafeZone(safeZoneContext);


                    }
                }

            }



        }

    }

    //注册规则监听器
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent e) {
        Player entity = e.getEntity();
        PlayerStateData data = entity.getData(ModAttachments.PLAYER_STATE);
        data.addStateListener(new NoEat(),new Invincible(),new AutoRegeneration(),new SafeZoneTransition());

        entity.setData(ModAttachments.PLAYER_STATE,data);
    }
}
