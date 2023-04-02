package bapt.bechacraft.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class TravelersSwifntessEffect extends StatusEffect {

    protected TravelersSwifntessEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0);
        EntityAttributeModifier speedModifier = new EntityAttributeModifier("speed_modifier", .1, Operation.MULTIPLY_TOTAL);
        this.getAttributeModifiers().put(EntityAttributes.GENERIC_MOVEMENT_SPEED, speedModifier);
    }
}
