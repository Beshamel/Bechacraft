package bapt.bechacraft.screen;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

import bapt.bechacraft.Bechacraft;
import bapt.bechacraft.vocation.VocationDisplay;
import bapt.bechacraft.vocation.Vocations;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class VocationMenu {
    public static final int WIDTH = 234;
    public static final int HEIGHT = 113;
    private double originX;
    private double originY;
    private int minPanX = Integer.MAX_VALUE;
    private int minPanY = Integer.MAX_VALUE;
    private int maxPanX = Integer.MIN_VALUE;
    private int maxPanY = Integer.MIN_VALUE;
    public static final Identifier BACKGROUND_TEXTURE = new Identifier(Bechacraft.MOD_ID, "textures/gui/vocation/background.png");
    private boolean initialized;
    public final VocationScreen screen;
    public HashMap<VocationDisplay, VocationWidget> widgets;
    private float alpha;
    private VocationDisplay lastSelected = null;

    public VocationMenu(VocationScreen screen) {
        this.initialized = false;
        this.screen = screen;
        this.widgets = new HashMap<>();
        this.alpha = 0f;
    }

    public void render(MatrixStack matrices, int x, int y, int mouseX, int mouseY, ClientPlayerEntity player) {
        if(!this.initialized)
            initialize(x, y);
        Screen.enableScissor(x, y, x + WIDTH, y + HEIGHT);
        this.drawBackground(matrices, x, y);
        VocationDisplay voc = this.selectedVocation(matrices, mouseX, mouseY, x, y);
        if(voc != null)
            lastSelected = voc;
        this.drawVocationWidgets(matrices, x, y, voc, player);
        Screen.fill(matrices, x, y, x + WIDTH, y + HEIGHT, 0, MathHelper.floor(this.alpha * 255.0f) << 24);
        this.alpha = voc == null ? MathHelper.clamp(this.alpha - 0.04f, 0.0f, 1.0f) : MathHelper.clamp(this.alpha + 0.02f, 0.0f, 0.3f);
        if(lastSelected != null)
            widgets.get(lastSelected).render(matrices, x + MathHelper.floor(originX), y + MathHelper.floor(originY), lastSelected, player);
        Screen.disableScissor();
    }

    private void initialize(int x, int y) {
        this.originX = WIDTH / 2;
        this.originY = HEIGHT / 2;
        for(VocationDisplay display : Vocations.all().stream().map(voc -> voc.getDisplay()).filter(dis -> dis.isVisible()).toList()) {
            widgets.put(display, new VocationWidget(this, display).setPos(x + MathHelper.floor(originX) + display.getX(), y + MathHelper.floor(originY) + display.getY()));
            int i = display.getX();
            int j = display.getY();
            this.minPanX = Math.min(this.minPanX, i);
            this.maxPanX = Math.max(this.maxPanX, i);
            this.minPanY = Math.min(this.minPanY, j);
            this.maxPanY = Math.max(this.maxPanY, j);
        }
        this.initialized = true;
    }

    public void drawBackground(MatrixStack matrices, int x, int y) {
        matrices.push();
        matrices.translate(x, y, 0.0f);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int i = MathHelper.floor(this.originX);
        int j = MathHelper.floor(this.originY);
        int k = i % 16;
        int l = j % 16;
        for (int m = -1; m <= WIDTH / 16 + 1; ++m) {
            for (int n = -1; n <= HEIGHT / 16 + 1; ++n) {
                Screen.drawTexture(matrices, k + 16 * m, l + 16 * n, 0.0f, 0.0f, 16, 16, 16, 16);
            }
        }
        matrices.pop();
    }

    public void drawVocationWidgets(MatrixStack matrices, int x, int y, VocationDisplay selected, ClientPlayerEntity player) {
        int x_ = MathHelper.floor(this.originX);
        int y_ = MathHelper.floor(this.originY);
        for(VocationDisplay display : widgets.keySet()) {
            int i = display.getX();
            int j = display.getY();
            this.minPanX = Math.min(this.minPanX, i);
            this.maxPanX = Math.max(this.maxPanX, i);
            this.minPanY = Math.min(this.minPanY, j);
            this.maxPanY = Math.max(this.maxPanY, j);
            widgets.get(display).render(matrices, x + x_, y + y_, display, player);
        }
    }

    public VocationDisplay selectedVocation(MatrixStack matrices, int mouseX, int mouseY, int x, int y) {
        RenderSystem.enableDepthTest();
        for (VocationDisplay display : widgets.keySet()) {
            if (widgets.get(display).isSelected(mouseX, mouseY))
                return display;
        }
        RenderSystem.disableDepthTest();
        return null;
    }

    public void move(double offsetX, double offsetY) {
        this.originX = MathHelper.clamp(this.originX + offsetX, WIDTH / 2 - maxPanX, WIDTH / 2 - minPanX);
        this.originY = MathHelper.clamp(this.originY + offsetY, HEIGHT / 2 - maxPanY, HEIGHT / 2 - minPanY);
    }
}
