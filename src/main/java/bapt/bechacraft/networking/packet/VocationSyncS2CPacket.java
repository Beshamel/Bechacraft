package bapt.bechacraft.networking.packet;

import bapt.bechacraft.vocation.Vocations;
import bapt.bechacraft.vocation.Vocation.VocationData;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class VocationSyncS2CPacket {
    
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        VocationData.write(client.player, Vocations.fromName(buf.readString()));
    }
}
