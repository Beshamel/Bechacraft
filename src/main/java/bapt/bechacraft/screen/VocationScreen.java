package bapt.bechacraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import bapt.bechacraft.Bechacraft;
import bapt.bechacraft.event.KeyInputHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class VocationScreen extends Screen {
    private static final Identifier WINDOW_TEXTURE = new Identifier(Bechacraft.MOD_ID, "textures/gui/vocation/window.png");
    public static final int WIDTH = 252;
    public static final int HEIGHT = 140;
    public static final int BACKGROUND_OFFSET_X = 9;
    public static final int BACKGROUND_OFFSET_Y = 18;
    public static final Text TITLE = Text.translatable("vocation_screen.bechacraft.title");
    private ClientPlayerEntity player;
    private VocationMenu menu;

    public VocationScreen(ClientPlayerEntity player) {
        super(NarratorManager.EMPTY);
        menu = new VocationMenu(this);
        this.player = player;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int i = (this.width - WIDTH) / 2;
        int j = (this.height - HEIGHT) / 2;
        this.renderBackground(matrices);
        menu.render(matrices, i + BACKGROUND_OFFSET_X, j + BACKGROUND_OFFSET_Y, mouseX, mouseY, player);
        this.drawWindow(matrices, i, j);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (KeyInputHandler.vocationMenuKey.matchesKey(keyCode, scanCode)) {
            this.client.setScreen(null);
            this.client.mouse.lockCursor();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button != 0) {
            return false;
        }
        menu.move(deltaX, deltaY);
        return true;
    }

    public void drawWindow(MatrixStack matrices, int x, int y) {
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, WINDOW_TEXTURE);
        VocationScreen.drawTexture(matrices, x, y, 0, 0, WIDTH, HEIGHT);
        this.textRenderer.draw(matrices, TITLE, (float)(x + 8), (float)(y + 6), 0x404040);
    }
}