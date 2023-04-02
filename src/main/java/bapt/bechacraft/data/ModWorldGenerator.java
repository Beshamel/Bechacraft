package bapt.bechacraft.data;

import java.util.concurrent.CompletableFuture;

import bapt.bechacraft.Bechacraft;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class ModWorldGenerator extends FabricDynamicRegistryProvider{

    public ModWorldGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return Bechacraft.MOD_ID;
    }

    @Override
    protected void configure(WrapperLookup registries, Entries entries) {
    }
    
}
