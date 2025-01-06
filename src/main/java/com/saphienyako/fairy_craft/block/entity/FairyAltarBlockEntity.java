package com.saphienyako.fairy_craft.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.saphienyako.fairy_craft.network.AltarParticleMessage;
import com.saphienyako.fairy_craft.network.FairyCraftNetwork;
import com.saphienyako.fairy_craft.recipe.FairyAltarRecipe;
import com.saphienyako.fairy_craft.screen.FairyAltarMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
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
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public ItemStackHandler getInventory() {
        return itemHandler;
    }

    public int getProgress() {return progress;}

    public int getMaxProgress() {return maxProgress;}

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
        if (this.level == null) return;
        if (!this.level.isClientSide) {
            if (hasRecipe()) {
                increaseCraftingProgress();
                setChanged(level, pos, state);

                if (hasProgressFinished()) {
                    craftItem();
                    resetProgress();
                }
            } else {
                resetProgress();
            }
            addParticles();
        }
    }

    private void addParticles() {
        if (this.progress > 0) {
            if (this.progress >= (maxProgress - 1)) {
                //Particles after item has been crafted
                FairyCraftNetwork.sendParticles(level, AltarParticleMessage.Type.ALTAR_01,this.worldPosition, progress, maxProgress);
            } else {
                List<ItemStack> stacks = new ArrayList<>();
                for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
                    ItemStack stack = itemHandler.getStackInSlot(slot);
                    if (!stack.isEmpty()) stacks.add(stack);
                }
                if (!stacks.isEmpty()) {
                    //Particles moving up while being crafted
                    FairyCraftNetwork.sendParticles(level, AltarParticleMessage.Type.ALTAR_02,this.worldPosition, progress, maxProgress);
                }
            }
        } else {
            //Particles on Model
            FairyCraftNetwork.sendParticles(level, AltarParticleMessage.Type.ALTAR_03,this.worldPosition, progress, maxProgress);
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private boolean hasRecipe() {
        Optional<FairyAltarRecipe> recipe = getCurrentRecipe();

        if(recipe.isEmpty()) {
            return false;
        }
        ItemStack result = recipe.get().getResultItem(Objects.requireNonNull(getLevel()).registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<FairyAltarRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        assert this.level != null;
        return this.level.getRecipeManager().getRecipeFor(FairyAltarRecipe.Type.INSTANCE, inventory, level);
    }

    private void craftItem() {
        Optional<FairyAltarRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

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

    @Override
    public AABB getRenderBoundingBox() {
        return super.getRenderBoundingBox();
    }
}
