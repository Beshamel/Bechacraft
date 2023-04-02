package bapt.bechacraft.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class SapExtractingRecipe implements Recipe<SimpleInventory> {

    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> recipeItems;
    private final int fill;

    public SapExtractingRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItems, int fill) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.fill = fill;
    }

	@Override
	public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient()) {
            return false;
        }

        return recipeItems.get(0).test(inventory.getStack(1));
	}

	@Override
	public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager drm) {
        return output;
	}

	@Override
	public boolean fits(int width, int height) {
        return true;
	}

	@Override
	public ItemStack getOutput(DynamicRegistryManager drm) {
        return output.copy();
	}

    public int getFill() {
        return fill;
    }

	@Override
	public Identifier getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return Serializer.INSTANCE;
	}

	@Override
	public RecipeType<?> getType() {
        return Type.INSTANCE;
	}

    public static class Type implements RecipeType<SapExtractingRecipe> {
        private Type() { }

        public static final Type INSTANCE = new Type();
        public static final String ID = "sap_extracting";
    }

    public static class Serializer implements RecipeSerializer<SapExtractingRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "sap_extracting";

		@Override
		public SapExtractingRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));

            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(1, Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            int fill = JsonHelper.getInt(json, "fill");

            return new SapExtractingRecipe(id, output, inputs, fill);
		}
		@Override
		public SapExtractingRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }

            ItemStack output = buf.readItemStack();
            int fill = buf.readInt();

            return new SapExtractingRecipe(id, output, inputs, fill);
		}
		@Override
		public void write(PacketByteBuf buf, SapExtractingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for(Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            buf.writeItemStack(recipe.getOutput(null));
            buf.writeInt(recipe.fill);
		}
    }
}
