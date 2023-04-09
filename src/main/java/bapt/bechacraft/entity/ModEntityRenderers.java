package bapt.bechacraft.entity;

import bapt.bechacraft.mixin.EntityRenderersInvoker;

public class ModEntityRenderers {
    
    public static void registerRenderers() {
        EntityRenderersInvoker.callRegister(ModEntityTypes.SHURIKEN, ShurikenEntityRenderer::new);
    }
}
