package bapt.bechacraft.damage;

import bapt.bechacraft.Bechacraft;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModDamageTypeTags {
    
    public static final TagKey<DamageType> IGNORES_FIGHTER = ModDamageTypeTags.of("ignores_fighter");

    private static TagKey<DamageType> of(String id) {
        return TagKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(Bechacraft.MOD_ID, id));
    }
}
