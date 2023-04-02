package bapt.bechacraft.block.entity;

import bapt.bechacraft.Bechacraft;
import bapt.bechacraft.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    
    public static BlockEntityType<SapExtractorEntity> SAP_EXTRACTOR;

    public static void registerBlockEntities() {
        SAP_EXTRACTOR = Registry.register(Registries.BLOCK_ENTITY_TYPE,
        new Identifier(Bechacraft.MOD_ID, "sap_extractor"),
        FabricBlockEntityTypeBuilder.create(SapExtractorEntity::new, ModBlocks.SAP_EXTRACTOR).build());
    }

}
