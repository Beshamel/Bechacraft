package bapt.bechacraft.item;

import java.util.ArrayList;
import java.util.List;

import bapt.bechacraft.Bechacraft;
import bapt.bechacraft.item.custom.KatanaItem;
import bapt.bechacraft.item.custom.SapItem;
import bapt.bechacraft.item.custom.ShurikenItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item CITRINE = registerItem("citrine", new Item(new FabricItemSettings()), List.of(ModItemGroup.BECHACRAFT, ItemGroups.INGREDIENTS));
    public static final Item RAW_CITRINE = registerItem("raw_citrine", new Item(new FabricItemSettings()), List.of(ModItemGroup.BECHACRAFT, ItemGroups.INGREDIENTS));

    public static final Item WOOD_SAP = registerItem("wood_sap", new SapItem(new FabricItemSettings().recipeRemainder(Items.GLASS_BOTTLE).food(ModFoodComponents.WOOD_SAP).maxCount(16)), List.of(ModItemGroup.BECHACRAFT, ItemGroups.INGREDIENTS));
    public static final Item RED_SAP = registerItem("red_sap", new SapItem(new FabricItemSettings().recipeRemainder(Items.GLASS_BOTTLE).food(ModFoodComponents.RED_SAP).maxCount(16)), List.of(ModItemGroup.BECHACRAFT, ItemGroups.INGREDIENTS));
    public static final Item BLUE_SAP = registerItem("blue_sap", new SapItem(new FabricItemSettings().recipeRemainder(Items.GLASS_BOTTLE).food(ModFoodComponents.BLUE_SAP).maxCount(16)), List.of(ModItemGroup.BECHACRAFT, ItemGroups.INGREDIENTS));
    public static final Item CHORUS_SAP = registerItem("chorus_sap", new SapItem(new FabricItemSettings().recipeRemainder(Items.GLASS_BOTTLE).food(ModFoodComponents.CHORUS_SAP).maxCount(16)), List.of(ModItemGroup.BECHACRAFT, ItemGroups.INGREDIENTS));

    public static final Item SHURIKEN = registerItem("shuriken", new ShurikenItem(new FabricItemSettings()), List.of(ModItemGroup.BECHACRAFT, ItemGroups.COMBAT));
    public static final Item KATANA = registerItem("katana", new KatanaItem(ToolMaterials.IRON, 2, -1.5f, new FabricItemSettings()), List.of(ModItemGroup.BECHACRAFT, ItemGroups.COMBAT));

    private static Item registerItem(String name, Item item) {
        return registerItem(name, item, new ArrayList<ItemGroup>());
    }

    private static Item registerItem(String name, Item item, List<ItemGroup> groups) {
        for(ItemGroup group : groups)
            addToItemGroup(group, item);
        return Registry.register(Registries.ITEM, new Identifier(Bechacraft.MOD_ID, name), item);
    }

    /*public static void addItemsToItemGroup() {
        addToItemGroup(ModItemGroup.BECHACRAFT, CITRINE);
        addToItemGroup(ModItemGroup.BECHACRAFT, RAW_CITRINE);
        addToItemGroup(ModItemGroup.BECHACRAFT, WOOD_SAP);
        addToItemGroup(ModItemGroup.BECHACRAFT, RED_SAP);
        addToItemGroup(ModItemGroup.BECHACRAFT, BLUE_SAP);
        addToItemGroup(ModItemGroup.BECHACRAFT, CHORUS_SAP);
        addToItemGroup(ModItemGroup.BECHACRAFT, SHURIKEN);

        addToItemGroup(ItemGroups.INGREDIENTS, CITRINE);
        addToItemGroup(ItemGroups.INGREDIENTS, RAW_CITRINE);
    }*/

    private static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    public static void registerModItems() {
        Bechacraft.LOGGER.info("Registering items for " + Bechacraft.MOD_NAME);
    }

}
