package bapt.bechacraft.block.entity;

import java.util.List;
import java.util.Optional;

import bapt.bechacraft.item.ModItems;
import bapt.bechacraft.item.custom.SapItem;
import bapt.bechacraft.property.ModProperties;
import bapt.bechacraft.recipe.SapExtractingRecipe;
import bapt.bechacraft.screen.SapExtractorScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SapExtractorEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;

    private int progress = 0;
    private int maxProgress = 400;
    
    private int fuel = 0;
    private int maxFuel = 500;

    private int sapType = 0;
    private int tank = 0;
    private int tankCapacity = 100;
    
    private static final List<Item> extractableItems = List.of(
        Items.OAK_LOG,
        Items.BIRCH_LOG,
        Items.ACACIA_LOG,
        Items.JUNGLE_LOG,
        Items.SPRUCE_LOG,
        Items.DARK_OAK_LOG,
        Items.MANGROVE_LOG,
        Items.OAK_WOOD,
        Items.BIRCH_WOOD,
        Items.ACACIA_WOOD,
        Items.JUNGLE_WOOD,
        Items.SPRUCE_WOOD,
        Items.DARK_OAK_WOOD,
        Items.MANGROVE_WOOD,
        Items.CRIMSON_STEM,
        Items.CRIMSON_HYPHAE,
        Items.WARPED_STEM,
        Items.WARPED_HYPHAE,
        Items.CHORUS_FLOWER
    );

    public SapExtractorEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SAP_EXTRACTOR, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch(index) {
                    case 0: return SapExtractorEntity.this.progress;
                    case 1: return SapExtractorEntity.this.maxProgress;
                    case 2: return SapExtractorEntity.this.fuel;
                    case 3: return SapExtractorEntity.this.maxFuel;
                    case 4: return SapExtractorEntity.this.sapType;
                    case 5: return SapExtractorEntity.this.tank;
                    case 6: return SapExtractorEntity.this.tankCapacity;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: SapExtractorEntity.this.progress = value; break;
                    case 1: SapExtractorEntity.this.maxProgress = value; break;
                    case 2: SapExtractorEntity.this.fuel = value; break;
                    case 3: SapExtractorEntity.this.maxFuel = value; break;
                    case 4: SapExtractorEntity.this.sapType = value; break;
                    case 5: SapExtractorEntity.this.tank = value; break;
                    case 6: SapExtractorEntity.this.tankCapacity = value; break;
                }
            }

            public int size() {
                return 7;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.bechacraft.sap_extractor");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new SapExtractorScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("sap_extractor.progress", progress);
        nbt.putInt("sap_extractor.fuel", fuel);
        nbt.putInt("sap_extractor.sap_type", sapType);
        nbt.putInt("sap_extractor.sap", tank);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("sap_extractor.progress");
        fuel = nbt.getInt("sap_extractor.fuel");
        sapType = nbt.getInt("sap_extractor.sap_type");
        tank = nbt.getInt("sap_extractor.sap");
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, SapExtractorEntity entity) {
        if(world.isClient())
            return;

        if(entity.canAddFuel()) {
            entity.addFuel();
        }
        
        if(entity.canExtract()) {
            entity.progress++;
            entity.fuel--;
            if(entity.progress >= entity.maxProgress) {
                entity.processIngredient();
                entity.progress = 0;
            }
        } else {
            entity.progress = 0;
        }

        if(entity.canFill()) {
            entity.fill();
        }
        
        markDirty(world, blockPos, state);

        state = state.with(Properties.LIT, entity.canExtract());
        state = state.with(ModProperties.SAP_TYPE, entity.sapType);
        state = state.with(ModProperties.FILLING, entity.tank == 0 ? 0 : (entity.tank >= entity.tankCapacity ? 2 : 1));
        world.setBlockState(blockPos, state, Block.NOTIFY_LISTENERS);
    }

    private boolean canAddFuel() {
        ItemStack stack = getStack(0);
        return fuel == 0 && stack.getItem() == Items.BLAZE_POWDER && stack.getCount() > 0;
    }

    private void addFuel() {
        fuel = maxFuel;
        removeStack(0, 1);
    }

    private void processIngredient() {
        SimpleInventory inventory = new SimpleInventory(size());
        for(int i = 0; i < size(); i++)
            inventory.setStack(i, getStack(i));

        Optional<SapExtractingRecipe> match = getWorld().getRecipeManager().getFirstMatch(SapExtractingRecipe.Type.INSTANCE, inventory, getWorld());
        
        SapExtractingRecipe recipe = match.get();
        int fill = recipe.getFill();
        
        removeStack(1, 1);
        sapType = getExtractionType(recipe.getOutput(null));
        tank = Math.min(tank + fill, tankCapacity);
    }

    private boolean canExtract() {
        SimpleInventory inventory = new SimpleInventory(size());
        for(int i = 0; i < size(); i++)
            inventory.setStack(i, getStack(i));

        Optional<SapExtractingRecipe> match = getWorld().getRecipeManager().getFirstMatch(SapExtractingRecipe.Type.INSTANCE, inventory, getWorld());

        if(!match.isPresent())
            return false;
        
        SapExtractingRecipe recipe = match.get();
        int extractionType = getExtractionType(recipe.getOutput(null));

        return fuel > 0
            && (sapType == 0 || extractionType == sapType)
            && tank < tankCapacity;
    }

    private void fill() {
        setStack(2, new ItemStack(SapItem.fromExtractionType(sapType)));
        tank = 0;
        sapType = 0;
    }

    private boolean canFill() {
        ItemStack stack = getStack(2);
        return tank >= tankCapacity && stack.getItem() == Items.GLASS_BOTTLE && stack.getCount() == 1;
    }
    
    private static int getExtractionType(ItemStack stack) {
        if(stack.isOf(ModItems.WOOD_SAP))
            return 1;
        if(stack.isOf(ModItems.RED_SAP))
            return 2;
        if(stack.isOf(ModItems.BLUE_SAP))
            return 3;
        if(stack.isOf(ModItems.CHORUS_SAP))
            return 4;
        return 0;
    }

    public static boolean canExtract(ItemStack stack) {
        return extractableItems.contains(stack.getItem());
    }
}
