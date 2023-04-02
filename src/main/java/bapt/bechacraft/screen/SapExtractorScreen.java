package bapt.bechacraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import bapt.bechacraft.Bechacraft;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SapExtractorScreen extends HandledScreen<SapExtractorScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(Bechacraft.MOD_ID, "textures/gui/sap_extractor_gui.png");
    private static final int[] BUBBLE_PROGRESS = new int[]{29, 24, 20, 16, 11, 6, 0};

    public SapExtractorScreen(SapExtractorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderProgressArrow(matrices, x, y);
        renderFuelTank(matrices, x, y);
        renderSapTank(matrices, x, y);
        renderBubbles(matrices, x, y);
    }

    private void renderProgressArrow(MatrixStack matrices, int x, int y) {
        if(handler.isExtracting()) {
            drawTexture(matrices, x + 73, y + 34, 176, 29, handler.getScaledProgress(), 9);
        }
    }

    private void renderFuelTank(MatrixStack matrices, int x, int y) {
        if(handler.hasFuel()) {
            drawTexture(matrices, x + 52, y + 66, 176, 38, handler.getScaledFuel(), 4);
        }
    }

    private void renderSapTank(MatrixStack matrices, int x, int y) {
        if(handler.hasSap()) {
            drawTexture(matrices, x + 98, y + 14 + 60 - handler.getScaledSap(), 176 + (handler.getSapType() - 1)*16, 42 + 60 - handler.getScaledSap(), 16, handler.getScaledSap());
        }
    }

    private void renderBubbles(MatrixStack matrices, int x, int y) {
        int m = handler.getProgress();
        int n = BUBBLE_PROGRESS[6 - (m / 2 % 7)];
        if (m > 0 && n > 0) {
            drawTexture(matrices, x + 55, y + 36 + 29 - n, 176, 29 - n, 12, n);
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
