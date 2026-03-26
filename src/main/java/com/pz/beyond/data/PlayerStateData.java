package com.pz.beyond.data;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

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


}
