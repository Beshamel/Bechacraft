package bapt.bechacraft.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {

    public static final FoodComponent WOOD_SAP = new FoodComponent.Builder().alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 3600, 1), 1f).build();
    public static final FoodComponent RED_SAP = new FoodComponent.Builder().alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 3600, 0), 1f).build();
    public static final FoodComponent BLUE_SAP = new FoodComponent.Builder().alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 3600, 1), 1f).build();
    public static final FoodComponent CHORUS_SAP = new FoodComponent.Builder().alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 3600, 1), 1f).build();
    
}