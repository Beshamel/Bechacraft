package bapt.bechacraft.util;

import org.jetbrains.annotations.Nullable;

import net.minecraft.util.Identifier;

public class Util {

    public static String createTranslationKey(String type, @Nullable Identifier id) {
        if (id == null) {
            return type + ".unregistered_sadface";
        }
        return type + "." + id.getNamespace() + "." + id.getPath().replace('/', '.');
    }
}
