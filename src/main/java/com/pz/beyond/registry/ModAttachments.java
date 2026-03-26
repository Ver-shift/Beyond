package com.pz.beyond.registry;

import com.pz.beyond.Beyond;
import com.pz.beyond.data.PlayerStateData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Beyond.MODID);

    public static final Supplier<AttachmentType<PlayerStateData.PlayerState>> PLAYER_STATE = ATTACHMENT_TYPES.register("player_state",()->
            AttachmentType.<PlayerStateData.PlayerState>builder(()-> PlayerStateData.PlayerState.INSIDE_SAFETY_ZONE)
                    .serialize(PlayerStateData.PlayerState.CODEC)
                    .copyOnDeath()
                    .build());
}
