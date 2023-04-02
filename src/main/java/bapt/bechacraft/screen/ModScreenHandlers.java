package bapt.bechacraft.screen;

import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    
    public static ScreenHandlerType<SapExtractorScreenHandler> SAP_EXTRACTOR_SCREEN_HANDLER;

    public static void registerScreenHandlers() {
        SAP_EXTRACTOR_SCREEN_HANDLER = new ScreenHandlerType<SapExtractorScreenHandler>(SapExtractorScreenHandler::new, null);
    }

}
