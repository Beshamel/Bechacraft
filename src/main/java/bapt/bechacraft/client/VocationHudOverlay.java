package bapt.bechacraft.client;

import com.mojang.blaze3d.systems.RenderSystem;

import bapt.bechacraft.vocation.Vocation;
import bapt.bechacraft.vocation.Vocations;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class VocationHudOverlay implements HudRenderCallback {

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if(client != null) {
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            x = width/2;
            y = height;
        }

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        Vocation vocation = Vocation.get(client.player);
        if(vocation != Vocations.NONE) {
            RenderSystem.setShaderTexture(0, vocation.getDisplay().getIcon());
            DrawableHelper.drawTexture(matrixStack, x + 96, y - 19, 0, 0, 24, 16, 24, 16);
        }
    }

    
}
