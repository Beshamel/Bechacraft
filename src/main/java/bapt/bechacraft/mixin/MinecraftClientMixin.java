package bapt.bechacraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import bapt.bechacraft.event.KeyInputHandler;
import bapt.bechacraft.screen.VocationScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.util.thread.ReentrantThreadExecutor;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin extends ReentrantThreadExecutor<Runnable> implements WindowEventHandler {

    public MinecraftClientMixin(String string) {
        super(string);
    }

    @Inject(at = @At("HEAD"), method = "handleInputEvents()V")
    private void injectHandleInputEvents(CallbackInfo ci) {
        while (KeyInputHandler.vocationMenuKey.wasPressed()) {
            MinecraftClient client = (MinecraftClient) (Object) this;
            client.setScreen(new VocationScreen(client.player));
        }
    }
}
