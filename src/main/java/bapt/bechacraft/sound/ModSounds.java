package bapt.bechacraft.sound;

import bapt.bechacraft.Bechacraft;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static SoundEvent ENTITY_SHURIKEN_THROW;
    public static SoundEvent ENTITY_SHURIKEN_HIT;

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(Bechacraft.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSoundEvents() {
        ENTITY_SHURIKEN_THROW = registerSoundEvent("entity_shuriken_throw");
        ENTITY_SHURIKEN_HIT = registerSoundEvent("entity_shuriken_hit");
    }
}
