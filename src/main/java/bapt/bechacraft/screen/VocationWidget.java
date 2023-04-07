package bapt.bechacraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import bapt.bechacraft.Bechacraft;
import bapt.bechacraft.util.Util;
import bapt.bechacraft.vocation.Vocation;
import bapt.bechacraft.vocation.VocationDisplay;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class VocationWidget {
    public final VocationMenu menu;
    public final VocationDisplay display;
    public static final Identifier WOOD_BORDER = new Identifier(Bechacraft.MOD_ID, "textures/gui/vocation/border/wood_border.png");
    public static final Identifier COPPER_BORDER = new Identifier(Bechacraft.MOD_ID, "textures/gui/vocation/border/copper_border.png");
    public static final Identifier IRON_BORDER = new Identifier(Bechacraft.MOD_ID, "textures/gui/vocation/border/iron_border.png");
    public static final Identifier DIAMOND_BORDER = new Identifier(Bechacraft.MOD_ID, "textures/gui/vocation/border/diamond_border.png");
    public static final Identifier FRAME_BACKGROUND = new Identifier(Bechacraft.MOD_ID, "textures/gui/vocation/border/background.png");
    public static final Identifier BLANK_FRAME = new Identifier(Bechacraft.MOD_ID, "textures/gui/vocation/border/blank.png");
    public static final int BORDER_WIDTH = 30;
    public static final int BORDER_HEIGHT = 22;
    public static final int WIDGET_WIDTH = 24;
    public static final int WIDGET_HEIGHT = 16;
    private int x = 0;
    private int y = 0;

    public VocationWidget(VocationMenu menu, VocationDisplay display) {
        this.menu = menu;
        this.display = display;
    }

    public VocationWidget setPos(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public void render(MatrixStack matrices, int x, int y, VocationDisplay display, ClientPlayerEntity player) {
        this.x = x;
        this.y = y;
        int i = display.getX() + x;
        int j = display.getY() + y;
        RenderSystem.setShaderTexture(0, FRAME_BACKGROUND);
        Screen.drawTexture(matrices, i - (BORDER_WIDTH / 2), j - (BORDER_HEIGHT / 2), 0, 0, BORDER_WIDTH, BORDER_HEIGHT, BORDER_WIDTH, BORDER_HEIGHT);
        if(Vocation.get(player).getFamily().contains(display.getVocation())) {
            RenderSystem.setShaderTexture(0, display.getIcon());
            Screen.drawTexture(matrices, i - (WIDGET_WIDTH / 2), j - (WIDGET_HEIGHT / 2), 0, 0, WIDGET_WIDTH, WIDGET_HEIGHT, WIDGET_WIDTH, WIDGET_HEIGHT);    
        } else {
            RenderSystem.setShaderTexture(0, BLANK_FRAME);
            Screen.drawTexture(matrices, i - (BORDER_WIDTH / 2), j - (BORDER_HEIGHT / 2), 0, 0, BORDER_WIDTH, BORDER_HEIGHT, BORDER_WIDTH, BORDER_HEIGHT);
        }
        RenderSystem.setShaderTexture(0, getBorder(display.getVocation().getTier()));
        Screen.drawTexture(matrices, i - (BORDER_WIDTH / 2), j - (BORDER_HEIGHT / 2), 0, 0, BORDER_WIDTH, BORDER_HEIGHT, BORDER_WIDTH, BORDER_HEIGHT);
    }

    private Identifier getBorder(int tier) {
        switch(tier) {
            case 1: return COPPER_BORDER;
            case 2: return IRON_BORDER;
            case 3: return DIAMOND_BORDER;
            default: return WOOD_BORDER;
        }
    }
    
    public boolean isSelected(int mouseX, int mouseY) {
        int x_ = display.getX() + x - BORDER_WIDTH / 2;
        int y_ = display.getY() + y - BORDER_HEIGHT / 2;
        return Util.isMouseInRect(mouseX, mouseY, x_, y_, BORDER_WIDTH, BORDER_HEIGHT);
    }
}
