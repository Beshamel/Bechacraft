package bapt.bechacraft.block;

import java.util.List;

import bapt.bechacraft.Bechacraft;
import bapt.bechacraft.block.custom.SapExtractorBlock;
import bapt.bechacraft.item.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {

    public static Block CITRINE_BLOCK = registerBlock("citrine_block",
        new Block(FabricBlockSettings.of(Material.METAL)
            .strength(5f, 6f)
            .requiresTool()),
        ModItemGroup.BECHACRAFT);

    public static Block CITRINE_ORE = registerBlock("citrine_ore",
        new ExperienceDroppingBlock(FabricBlockSettings.of(Material.STONE)
                .strength(3f, 3f)
                .requiresTool(),
            UniformIntProvider.create(3, 7)),
        List.of(ModItemGroup.BECHACRAFT, ItemGroups.NATURAL));

    public static Block DEEPSLATE_CITRINE_ORE = registerBlock("deepslate_citrine_ore",
        new ExperienceDroppingBlock(FabricBlockSettings.of(Material.STONE)
                .strength(4.5f, 3f)
                .requiresTool()
                .sounds(BlockSoundGroup.DEEPSLATE),
            UniformIntProvider.create(3, 7)),
            List.of(ModItemGroup.BECHACRAFT, ItemGroups.NATURAL));

    public static Block IRIDIUM_ORE = registerBlock("iridium_ore",
        new ExperienceDroppingBlock(FabricBlockSettings.of(Material.STONE)
                .strength(3f, 12f)
                .requiresTool(),
            UniformIntProvider.create(3, 7)),
        List.of(ModItemGroup.BECHACRAFT, ItemGroups.NATURAL));
    
    public static Block SAP_EXTRACTOR = registerBlock("sap_extractor",
        new SapExtractorBlock(FabricBlockSettings.of(Material.WOOD)
                .luminance(state -> state.get(SapExtractorBlock.LIT) ? 10 : 0)),
            List.of(ModItemGroup.BECHACRAFT, ItemGroups.FUNCTIONAL));

    private static Block registerBlock(String name, Block block, ItemGroup group) {

        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(Bechacraft.MOD_ID, name), block);

    }
    
    private static Block registerBlock(String name, Block block, List<ItemGroup> groups) {

        registerBlockItem(name, block, groups);
        return Registry.register(Registries.BLOCK, new Identifier(Bechacraft.MOD_ID, name), block);

    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {

        Item item = Registry.register(Registries.ITEM, new Identifier(Bechacraft.MOD_ID, name),
        new BlockItem(block, new FabricItemSettings()));

        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));

        return item;

    }

    private static Item registerBlockItem(String name, Block block, List<ItemGroup> groups) {

        Item item = Registry.register(Registries.ITEM, new Identifier(Bechacraft.MOD_ID, name),
        new BlockItem(block, new FabricItemSettings()));

        for(ItemGroup group : groups)
            ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));

        return item;

    }

    public static void registerModBlocks() {
        Bechacraft.LOGGER.info("Registering blocks for " + Bechacraft.MOD_NAME);
    }
}