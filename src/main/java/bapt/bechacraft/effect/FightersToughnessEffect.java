package bapt.bechacraft.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FightersToughnessEffect extends StatusEffect {

    protected FightersToughnessEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0);
        EntityAttributeModifier damageModifier = new EntityAttributeModifier("damage_modifier", 1, Operation.ADDITION);
        this.getAttributeModifiers().put(EntityAttributes.GENERIC_ATTACK_DAMAGE, damageModifier);
    }
}
