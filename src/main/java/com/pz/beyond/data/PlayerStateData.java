package com.pz.beyond.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pz.beyond.safe_zone_rule.ISafeZoneRuleListener;
import net.minecraft.util.StringRepresentable;

import java.util.ArrayList;
import java.util.List;

public class PlayerStateData {
    /**
     * 用来存储玩家状态
     * <ul>
     *     <li>{@link #INSIDE_SAFETY_ZONE} - 在安全区内</li>
     *     <li>{@link #IN_GAME} - 在游戏中</li>
     * </ul>
     */
    public enum PlayerState implements StringRepresentable {
        INSIDE_SAFETY_ZONE("insideSafetyZone"),
        IN_GAME("inGame");

        private final String name;

        PlayerState(String name) {
            this.name = name;
        }
        public static final Codec<PlayerState> CODEC =
                StringRepresentable.fromEnum(PlayerState::values);
        @Override
        public String getSerializedName() {
            return name;
        }
    }

    // Codec for PlayerStateData
    public static final Codec<PlayerStateData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    PlayerState.CODEC.fieldOf("playerState").forGetter(data -> data.playerState)
            ).apply(instance, PlayerStateData::new)
    );

    private PlayerState playerState = PlayerState.IN_GAME;
    private transient List<ISafeZoneRuleListener> listeners = new ArrayList<>();

    public PlayerStateData() {
    }

    public PlayerStateData(PlayerState playerState) {
        this.playerState = playerState;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public void addStateListener(ISafeZoneRuleListener... listener) {
        for (var safeListener : listener){
            listeners.add(safeListener);
        }
    }



    public List<ISafeZoneRuleListener> getListeners() {
        return listeners;
    }
}
