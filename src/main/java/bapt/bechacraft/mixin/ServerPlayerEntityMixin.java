package bapt.bechacraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import bapt.bechacraft.vocation.Vocation;
import bapt.bechacraft.vocation.Vocations;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    
    @Inject(at = @At("TAIL"), method = "copyFrom(Lnet/minecraft/server/network/ServerPlayerEntity;Z)V")
    public void injectCopyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        Vocation.set(player, Vocation.get(oldPlayer));
    }
    
    @Inject(at = @At("HEAD"), method = "tick()V")
    public void injectCopyFrom(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        LivingEntityAccessor accessor = (LivingEntityAccessor) (Object) this;
        if(Vocation.get(player).inherits(Vocations.BRUTE)) {
            if(player.world.getTime() - accessor.getLastDamageTime() >= 200L && player.getAbsorptionAmount() < 4f) {
                player.setAbsorptionAmount(4f);
            }
        }
    }
}
