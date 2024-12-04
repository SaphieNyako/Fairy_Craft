package com.saphienyako.fairy_craft.block.entity;

import com.saphienyako.fairy_craft.item.ModItems;
import com.saphienyako.fairy_craft.screen.FairyAltarMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FairyAltarBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(6);

    private static final int INPUT_SLOT_00 = 0;
    private static final int INPUT_SLOT_01 = 1;
    private static final int INPUT_SLOT_02 = 2;
    private static final int INPUT_SLOT_03 = 3;
    private static final int INPUT_SLOT_04 = 4;
    private static final int OUTPUT_SLOT = 5;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 40;


    public FairyAltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FAIRY_ALTAR_BLOCK_ENTITY.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> FairyAltarBlockEntity.this.progress;
                    case 1 -> FairyAltarBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> FairyAltarBlockEntity.this.progress = pValue;
                    case 1 -> FairyAltarBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    public int getProgress() {
        return this.progress;
    }

    public void drops() {
        //Drop Items
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("progress", this.progress);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        this.progress = nbt.getInt("progress");
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.fairy_craft.fairy_altar");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new FairyAltarMenu(id,inventory,this,this.data);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if(hasRecipe()) {
            increaseCraftingProgress();
            setChanged(level, pos, state);

            if(hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private boolean hasRecipe() {
        //TODO loop over or create recipe
        boolean slot_01 = this.itemHandler.getStackInSlot(INPUT_SLOT_00).getItem() == Items.OAK_SAPLING;
        boolean slot_02 = this.itemHandler.getStackInSlot(INPUT_SLOT_01).getItem() == Items.OXEYE_DAISY;
        boolean slot_03 = this.itemHandler.getStackInSlot(INPUT_SLOT_02).getItem() == Items.WHEAT_SEEDS;
        boolean slot_04 = this.itemHandler.getStackInSlot(INPUT_SLOT_03).getItem() == Items.EGG;
        boolean slot_05 = this.itemHandler.getStackInSlot(INPUT_SLOT_04).getItem() == ModItems.EMPTY_SUMMONING_SCROLL.get();
        ItemStack result = new ItemStack(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get());
        return slot_01 && slot_02 && slot_03 && slot_04 && slot_05  && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private void craftItem() {
        ItemStack result = new ItemStack(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get(), 1);
        this.itemHandler.extractItem(INPUT_SLOT_00, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_01, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_02, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_03, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_04, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }
    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }
}
