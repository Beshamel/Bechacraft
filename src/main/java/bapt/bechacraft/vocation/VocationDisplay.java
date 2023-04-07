package bapt.bechacraft.vocation;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class VocationDisplay {
    
    private Vocation vocation;

    private Identifier icon;
    private Text displayName;
    private int x;
    private int y;
    private boolean visible;

    public VocationDisplay(Vocation vocation, Identifier icon, String translationKey, int x, int y, boolean visible) {
        this.vocation = vocation;
        this.icon = icon;
        this.displayName = Text.translatable(translationKey);
        this.x = x;
        this.y = y;
        this.visible = visible;
    }

    public Vocation getVocation() {
        return vocation;
    }

    public Identifier getIcon() {
        return icon;
    }

    public Text getDisplayName() {
        return displayName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }
}
