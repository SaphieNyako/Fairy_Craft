package com.saphienyako.fairy_craft;

import com.mojang.logging.LogUtils;
import com.saphienyako.fairy_craft.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FairyCraft.MOD_ID)
public class FairyCraft
{
    public static final String MOD_ID = "fairy_craft";
    private static final Logger LOGGER = LogUtils.getLogger();

    public FairyCraft() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.FAIRY_CRAFT_LEXICON);
            event.accept(ModItems.LESSER_FAIRY_GEM);
            event.accept(ModItems.GREATER_FAIRY_GEM);
            event.accept(ModItems.SHINY_FAIRY_GEM);
            event.accept(ModItems.BRILLIANT_FAIRY_GEM);
            event.accept(ModItems.FAIRY_INK_BOTTLE);
            event.accept(ModItems.FAIRY_CRAFT_MUSIC_DISC);
            event.accept(ModItems.EMPTY_SUMMONING_SCROLL);
            event.accept(ModItems.SUMMONING_SCROLL_SPRING_PIXIE);
            event.accept(ModItems.SUMMONING_SCROLL_SUMMER_PIXIE);
            event.accept(ModItems.SUMMONING_SCROLL_AUTUMN_PIXIE);
            event.accept(ModItems.SUMMONING_SCROLL_WINTER_PIXIE);
            event.accept(ModItems.PIXIE_DUST);
            event.accept(ModItems.MANDRAKE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
