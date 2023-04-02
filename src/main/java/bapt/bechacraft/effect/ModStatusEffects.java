package bapt.bechacraft.effect;

import bapt.bechacraft.Bechacraft;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {
    
    public static final StatusEffect TRAVELERS_SWIFTNESS = new TravelersSwifntessEffect();
    public static final StatusEffect FIGHTERS_TOUGHNESS = new FightersToughnessEffect();

    private static StatusEffect register(StatusEffect effect, String name) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(Bechacraft.MOD_ID, name), effect);
    }

    public static void registerEffects() {
        register(TRAVELERS_SWIFTNESS, "travelers_swiftness");
        register(FIGHTERS_TOUGHNESS, "fighters_toughness");
    }
}
