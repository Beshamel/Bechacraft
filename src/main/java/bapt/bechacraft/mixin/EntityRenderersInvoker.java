package bapt.bechacraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

@Mixin(EntityRenderers.class)
public interface EntityRenderersInvoker {
    
    @Invoker("register")
    static <T extends Entity> void callRegister(EntityType<? extends T> type, EntityRendererFactory<T> factory) {
        throw new AssertionError();
    }
}
