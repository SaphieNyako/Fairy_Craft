package com.saphienyako.fairy_craft.item;

import com.saphienyako.fairy_craft.FairyCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FairyCraft.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FAIRY_CRAFT_TAB = CREATIVE_MODE_TAB.register("tutorial_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get()))
                    .title(Component.translatable("creative_tab.fairy_craft_creative_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.FAIRY_CRAFT_LEXICON.get());
                        pOutput.accept(ModItems.LESSER_FAIRY_GEM.get());
                        pOutput.accept(ModItems.GREATER_FAIRY_GEM.get());
                        pOutput.accept(ModItems.SHINY_FAIRY_GEM.get());
                        pOutput.accept(ModItems.BRILLIANT_FAIRY_GEM.get());
                        pOutput.accept(ModItems.FAIRY_INK_BOTTLE.get());
                        pOutput.accept(ModItems.FAIRY_CRAFT_MUSIC_DISC.get());
                        pOutput.accept(ModItems.EMPTY_SUMMONING_SCROLL.get());
                        pOutput.accept(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get());
                        pOutput.accept(ModItems.SUMMONING_SCROLL_SUMMER_PIXIE.get());
                        pOutput.accept(ModItems.SUMMONING_SCROLL_AUTUMN_PIXIE.get());
                        pOutput.accept(ModItems.SUMMONING_SCROLL_WINTER_PIXIE.get());
                        pOutput.accept(ModItems.PIXIE_DUST.get());
                        pOutput.accept(ModItems.MANDRAKE.get());
                    })
                    .build());


    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}