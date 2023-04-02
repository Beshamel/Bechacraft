package bapt.bechacraft;

import bapt.bechacraft.event.KeyInputHandler;
import bapt.bechacraft.networking.ModMessages;
import bapt.bechacraft.screen.ModScreenHandlers;
import bapt.bechacraft.screen.SapExtractorScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class BechacraftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.SAP_EXTRACTOR_SCREEN_HANDLER, SapExtractorScreen::new);
		KeyInputHandler.register();
        ModMessages.registerS2CPackets();
    }
}
