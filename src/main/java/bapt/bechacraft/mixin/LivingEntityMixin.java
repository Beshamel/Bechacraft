package bapt.bechacraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import bapt.bechacraft.damage.ModDamageTypeTags;
import bapt.bechacraft.vocation.Vocation;
import bapt.bechacraft.vocation.Vocations;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
 
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(at = @At("HEAD"), method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", ordinal = 0)
    public float modifyDamageAmount(float damageAmount, DamageSource source, float amount) {
        LivingEntity thisEntity = (LivingEntity) (Object) this;
        if(thisEntity instanceof PlayerEntity) {
            if(Vocation.get((PlayerEntity) thisEntity).inherits(Vocations.BRUTE))
                return damageAmount - 2f;
            if(Vocation.get((PlayerEntity) thisEntity).inherits(Vocations.WARRIOR))
                return damageAmount - 1f;
            if(Vocation.get((PlayerEntity) thisEntity).inherits(Vocations.FIGHTER) && !source.isIn(ModDamageTypeTags.IGNORES_FIGHTER))
                return damageAmount - 1f;
        }
        return damageAmount;
    }
}