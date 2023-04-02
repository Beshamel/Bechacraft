package bapt.bechacraft.event;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

public class KeyInputHandler {
    
    public static final String KEY_CATERGORY_BECHACRAFT = "key.category.bechacraft";
    public static final String KEY_OPEN_VOCATION_MENU = "key.bechacraft.open_vocation_menu";

    public static KeyBinding vocationMenuKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if((vocationMenuKey).wasPressed()) {
                client.player.sendMessage(Text.literal("Menu vocation"));
            }
        });
    }

    public static void register() {
        vocationMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KEY_OPEN_VOCATION_MENU,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            KEY_CATERGORY_BECHACRAFT
        ));

        registerKeyInputs();
    }
}
