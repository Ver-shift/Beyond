package com.pz.beyond.event.custom;

import com.pz.beyond.data.PlayerStateData;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

public class PlayerStateChangeEvent extends Event implements ICancellableEvent {
    private final Player player;
    private final PlayerStateData.PlayerState fromState;
    private final PlayerStateData.PlayerState toState;

    public PlayerStateChangeEvent(Player player, PlayerStateData.PlayerState fromState, PlayerStateData.PlayerState toState) {
        this.player = player;
        this.fromState = fromState;
        this.toState = toState;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerStateData.PlayerState getFromState() {
        return fromState;
    }

    public PlayerStateData.PlayerState getToState() {
        return toState;
    }

}
