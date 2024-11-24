package com.saphienyako.fairy_craft.network;

import com.saphienyako.fairy_craft.FairyCraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class FairyCraftNetwork {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(FairyCraft.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ParticleMessage.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ParticleMessage::decode)
                .encoder(ParticleMessage::encode)
                .consumerMainThread(ParticleMessage::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static void sendParticles(Level level, ParticleMessage.Type type, BlockPos chunk) {
        if (level instanceof ServerLevel) {
           INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(chunk)), new ParticleMessage(type, chunk));
        }
    }
}
