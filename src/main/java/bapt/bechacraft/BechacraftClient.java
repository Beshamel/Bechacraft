package bapt.bechacraft;

import bapt.bechacraft.client.VocationHudOverlay;
import bapt.bechacraft.event.KeyInputHandler;
import bapt.bechacraft.networking.ModMessages;
import bapt.bechacraft.screen.ModScreenHandlers;
import bapt.bechacraft.screen.SapExtractorScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class BechacraftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.SAP_EXTRACTOR_SCREEN_HANDLER, SapExtractorScreen::new);
		KeyInputHandler.register();
        ModMessages.registerS2CPackets();

        HudRenderCallback.EVENT.register(new VocationHudOverlay());
        
    }
}
