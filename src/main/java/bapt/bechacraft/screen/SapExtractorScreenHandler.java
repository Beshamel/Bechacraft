package bapt.bechacraft.screen;

import bapt.bechacraft.block.entity.SapExtractorEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class SapExtractorScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public SapExtractorScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, new SimpleInventory(3), new ArrayPropertyDelegate(7));
    }

    public SapExtractorScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandlers.SAP_EXTRACTOR_SCREEN_HANDLER, syncId);
        checkSize(inventory, 3);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = delegate;

        this.addSlot(new FuelSlot(inventory, 0, 17, 39));
        this.addSlot(new IngredientSlot(this, inventory, 1, 53, 18));
        this.addSlot(new BottleSlot(inventory, 2, 134, 58));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(delegate);
    }

    public boolean isExtracting() {
        return propertyDelegate.get(0) > 0;
    }

    public boolean hasFuel() {
        return propertyDelegate.get(3) > 0;
    }

    public boolean hasSap() {
        return propertyDelegate.get(5) > 0 && propertyDelegate.get(6) > 0;
    }

    public int getProgress() {
        return propertyDelegate.get(0);
    }

    public int getScaledProgress() {
        int progress = propertyDelegate.get(0);
        int maxProgress = propertyDelegate.get(1);
        int progressArrowSize = 20;
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getScaledFuel() {
        int fuel = propertyDelegate.get(2);
        int maxFuel = propertyDelegate.get(3);
        int fuelTankSize = 18;
        return maxFuel != 0 && fuel != 0 ? fuel * fuelTankSize / maxFuel : 0;
    }

    public int getScaledSap() {
        int tank = propertyDelegate.get(5);
        int tankCapacity = propertyDelegate.get(6);
        int tankSize = 60;
        return tankCapacity != 0 && tank != 0 ? tank * tankSize / tankCapacity : 0;
    }

    public int getSapType() {
        return propertyDelegate.get(4);
    }

	public boolean isExtractable(ItemStack stack) {
		return SapExtractorEntity.canExtract(stack);
	}

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if(invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if(!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if(originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for(int i = 0; i < 3; ++i) {
            for(int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i*9 + 9, 8 + l*18, 84 + i*18));
            }
        }
    }
    
    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i*18, 142));
        }
    }

    static class FuelSlot
    extends Slot {
        public FuelSlot(Inventory inventory, int i, int j, int k) {
            super(inventory, i, j, k);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return FuelSlot.matches(stack);
        }

        public static boolean matches(ItemStack stack) {
            return stack.isOf(Items.BLAZE_POWDER);
        }

        @Override
        public int getMaxItemCount() {
            return 64;
        }
    }

    static class IngredientSlot
    extends Slot {
        private final SapExtractorScreenHandler handler;

        public IngredientSlot(SapExtractorScreenHandler handler, Inventory inventory, int i, int j, int k) {
            super(inventory, i, j, k);
            this.handler = handler;
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return this.handler.isExtractable(stack);
        }

        @Override
        public int getMaxItemCount() {
            return 64;
        }
    }

    static class BottleSlot
    extends Slot {
        public BottleSlot(Inventory inventory, int i, int j, int k) {
            super(inventory, i, j, k);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return BottleSlot.matches(stack);
        }

        public static boolean matches(ItemStack stack) {
            return stack.isOf(Items.GLASS_BOTTLE);
        }

        @Override
        public int getMaxItemCount() {
            return 1;
        }
    }
}
