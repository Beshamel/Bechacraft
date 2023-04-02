package bapt.bechacraft.sound;

import bapt.bechacraft.Bechacraft;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    private static SoundEvent registerSoundEvent(String name) {
        
        Identifier id = new Identifier(Bechacraft.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));

    }

}
