package bapt.bechacraft.item;

import bapt.bechacraft.Bechacraft;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static ItemGroup BECHACRAFT;

    public static void registerItemGroups() {
        BECHACRAFT = FabricItemGroup.builder(new Identifier(Bechacraft.MOD_ID, "bechacraft"))
        .displayName(Text.literal(Bechacraft.MOD_NAME))
        .icon(() -> new ItemStack(ModItems.CITRINE))
        .build();
    }

}
