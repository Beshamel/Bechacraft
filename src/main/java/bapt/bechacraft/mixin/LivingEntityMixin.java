package bapt.bechacraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import bapt.bechacraft.effect.ModStatusEffects;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
 
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(at = @At("HEAD"), method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", ordinal = 0)
    public float modifyDamageAmount(float damageAmount) {
        LivingEntity thisEntity = (LivingEntity) (Object) this;
        if(thisEntity.hasStatusEffect(ModStatusEffects.FIGHTERS_TOUGHNESS))
            return damageAmount - (float) (thisEntity.getStatusEffect(ModStatusEffects.FIGHTERS_TOUGHNESS).getAmplifier() + 1);
        return damageAmount;
    }
}