package bapt.bechacraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import bapt.bechacraft.command.argument.VocationArgumentType;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.registry.Registry;

@Mixin(ArgumentTypes.class)
public class ArgumentTypesMixin {
    
    @Inject(at = @At("TAIL"), method = "register(Lnet/minecraft/registry/Registry;)Lnet/minecraft/command/argument/serialize/ArgumentSerializer;")
    private static void register(Registry<ArgumentSerializer<?, ?>> registry, CallbackInfoReturnable<ArgumentSerializer<?, ?>> info) {
        ArgumentTypesInvoker.callRegister(registry, "vocation", VocationArgumentType.class, ConstantArgumentSerializer.of(VocationArgumentType::vocation));
    }
}
