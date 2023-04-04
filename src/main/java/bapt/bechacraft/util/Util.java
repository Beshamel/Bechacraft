package bapt.bechacraft.util;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class Util {

    public static String createTranslationKey(String type, @Nullable Identifier id) {
        if (id == null) {
            return type + ".unregistered_sadface";
        }
        return type + "." + id.getNamespace() + "." + id.getPath().replace('/', '.');
    }

    public static void applyModifier(PlayerEntity player, EntityAttribute attribute, EntityAttributeModifier modifier, String name) {
        EntityAttributeInstance entityAttributeInstance = player.getAttributes().getCustomInstance(attribute);
        if (entityAttributeInstance == null) return;
        EntityAttributeModifier entityAttributeModifier = modifier;
        entityAttributeInstance.removeModifier(entityAttributeModifier);
        entityAttributeInstance.addPersistentModifier(new EntityAttributeModifier(entityAttributeModifier.getId(), name, modifier.getValue(), entityAttributeModifier.getOperation()));
    }

    public static void removeModifier(PlayerEntity player, EntityAttribute attribute, EntityAttributeModifier modifier) {
        EntityAttributeInstance entityAttributeInstance = player.getAttributes().getCustomInstance(attribute);
        if (entityAttributeInstance == null) return;
        entityAttributeInstance.removeModifier(modifier);
    }
}
