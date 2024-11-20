package com.saphienyako.fairy_craft.block;

import com.saphienyako.fairy_craft.FairyCraft;
import com.saphienyako.fairy_craft.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FairyCraft.MOD_ID);

    public static final RegistryObject<Block> FAIRY_GEM_ORE = registerBlockAndItem("fairy_gem_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> FAIRY_GEM_ORE_DEEP_SLATE = registerBlockAndItem("fairy_gem_ore_deep_slate",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    /* BLOCKS TO BE ADDED IN THIS VERSION

    public static final GiantFlowerBlock sunflower = new SunflowerBlock(FeywildMod.getInstance());
    public static final GiantFlowerBlock dandelion = new DandelionBlock(FeywildMod.getInstance());
    public static final GiantFlowerBlock crocus = new CrocusBlock(FeywildMod.getInstance());

    public static final FeyAltarBlock summerFeyAltar = new FeyAltarBlock(FeywildMod.getInstance(), Alignment.SUMMER);
    public static final FeyAltarBlock winterFeyAltar = new FeyAltarBlock(FeywildMod.getInstance(), Alignment.WINTER);
    public static final FeyAltarBlock autumnFeyAltar = new FeyAltarBlock(FeywildMod.getInstance(), Alignment.AUTUMN);

    public static final MandrakeCrop mandrakeCrop = new MandrakeCrop();
     */

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static<T extends Block> RegistryObject<T> registerBlockAndItem(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
