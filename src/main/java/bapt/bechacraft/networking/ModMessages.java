package bapt.bechacraft.networking;

import bapt.bechacraft.Bechacraft;
import bapt.bechacraft.networking.packet.VocationSyncS2CPacket;
import bapt.bechacraft.networking.packet.VocationSeekC2SPacket;
import bapt.bechacraft.networking.packet.VocationSetC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier VOCATION_SYNC_S2C = new Identifier(Bechacraft.MOD_ID, "vocation_sync_s2c");

    public static final Identifier VOCATION_SEEK_C2S = new Identifier(Bechacraft.MOD_ID, "vocation_seek_c2s");
    public static final Identifier VOCATION_SET_C2S = new Identifier(Bechacraft.MOD_ID, "vocation_set_c2s");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(VOCATION_SEEK_C2S, VocationSeekC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(VOCATION_SET_C2S, VocationSetC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(VOCATION_SYNC_S2C, VocationSyncS2CPacket::receive);
    }
}
