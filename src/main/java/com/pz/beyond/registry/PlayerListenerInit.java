package com.pz.beyond.registry;

import com.pz.beyond.safe_zone_rule.rules.AutoRegeneration;
import com.pz.beyond.safe_zone_rule.rules.Invincible;
import com.pz.beyond.safe_zone_rule.rules.NoEat;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
public class PlayerListenerInit {


    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent e) {
        var player = e.getEntity();
        var playerStateData = player.getData(ModAttachments.PLAYER_STATE_DATA);
        playerStateData.addStateListener(new NoEat(),new Invincible(),new AutoRegeneration());

    }
}
