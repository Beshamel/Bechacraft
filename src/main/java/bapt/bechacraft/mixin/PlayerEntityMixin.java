package bapt.bechacraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import bapt.bechacraft.vocation.Vocation;
import bapt.bechacraft.vocation.Vocations;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "jump()V", at = @At("HEAD"), cancellable = true)
    private void injectJump(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        super.jump();
        player.incrementStat(Stats.JUMP);
        if(!Vocation.get(player).inherits(Vocations.TRAVELER)){
            if (player.isSprinting()) {
                player.addExhaustion(0.2f);
            } else {
                player.addExhaustion(0.05f);
            }
        }
        ci.cancel();
    }

    @Inject(method = "increaseTravelMotionStats(DDD)V", at = @At("HEAD"), cancellable = true)
    public void injectIncreaseTravelMotionStats(double dx, double dy, double dz, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        boolean traveler = Vocation.get(player).inherits(Vocations.TRAVELER);
        if (this.hasVehicle()) {
            return;
        }
        if (this.isSwimming()) {
            int i = Math.round((float)Math.sqrt(dx * dx + dy * dy + dz * dz) * 100.0f);
            if (i > 0) {
                player.increaseStat(Stats.SWIM_ONE_CM, i);
                player.addExhaustion(0.01f * (float)i * 0.01f);
            }
        } else if (this.isSubmergedIn(FluidTags.WATER)) {
            int i = Math.round((float)Math.sqrt(dx * dx + dy * dy + dz * dz) * 100.0f);
            if (i > 0) {
                player.increaseStat(Stats.WALK_UNDER_WATER_ONE_CM, i);
                player.addExhaustion(0.01f * (float)i * 0.01f);
            }
        } else if (this.isTouchingWater()) {
            int i = Math.round((float)Math.sqrt(dx * dx + dz * dz) * 100.0f);
            if (i > 0) {
                player.increaseStat(Stats.WALK_ON_WATER_ONE_CM, i);
                player.addExhaustion(0.01f * (float)i * 0.01f);
            }
        } else if (this.isClimbing()) {
            if (dy > 0.0) {
                player.increaseStat(Stats.CLIMB_ONE_CM, (int)Math.round(dy * 100.0));
            }
        } else if (this.onGround) {
            int i = Math.round((float)Math.sqrt(dx * dx + dz * dz) * 100.0f);
            if (i > 0) {
                if (this.isSprinting()) {
                    player.increaseStat(Stats.SPRINT_ONE_CM, i);
                    if(!traveler)
                        player.addExhaustion(0.1f * (float)i * 0.01f);
                } else if (this.isInSneakingPose()) {
                    player.increaseStat(Stats.CROUCH_ONE_CM, i);
                    player.addExhaustion(0.0f * (float)i * 0.01f);
                } else {
                    player.increaseStat(Stats.WALK_ONE_CM, i);
                    player.addExhaustion(0.0f * (float)i * 0.01f);
                }
            }
        } else if (this.isFallFlying()) {
            int i = Math.round((float)Math.sqrt(dx * dx + dy * dy + dz * dz) * 100.0f);
            player.increaseStat(Stats.AVIATE_ONE_CM, i);
        } else {
            int i = Math.round((float)Math.sqrt(dx * dx + dz * dz) * 100.0f);
            if (i > 25) {
                player.increaseStat(Stats.FLY_ONE_CM, i);
            }
        }
        
        ci.cancel();
    }
}
