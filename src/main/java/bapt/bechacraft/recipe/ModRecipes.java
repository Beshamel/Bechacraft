package bapt.bechacraft.recipe;

import bapt.bechacraft.Bechacraft;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Bechacraft.MOD_ID, SapExtractingRecipe.Serializer.ID), SapExtractingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Bechacraft.MOD_ID, SapExtractingRecipe.Type.ID), SapExtractingRecipe.Type.INSTANCE);
    }
}
