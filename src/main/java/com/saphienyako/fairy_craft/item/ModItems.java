package com.saphienyako.fairy_craft.item;

import com.saphienyako.fairy_craft.FairyCraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    //TODO for verion 1: Add Flower Seeds, and Mandrake Crops
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FairyCraft.MOD_ID);
    public static final RegistryObject<Item> FAIRY_CRAFT_LEXICON = ITEMS.register("fairy_craft_lexicon", () -> new Item(new Item.Properties()));
    //TODO add patchouli
    public static final RegistryObject<Item> LESSER_FAIRY_GEM = ITEMS.register("lesser_fairy_gem", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GREATER_FAIRY_GEM = ITEMS.register("greater_fairy_gem", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SHINY_FAIRY_GEM = ITEMS.register("shiny_fairy_gem", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRILLIANT_FAIRY_GEM = ITEMS.register("brilliant_fairy_gem", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FAIRY_INK_BOTTLE = ITEMS.register("fairy_ink_bottle", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FAIRY_CRAFT_MUSIC_DISC = ITEMS.register("fairy_craft_music_disc", () -> new Item(new Item.Properties()));
    //TODO add fairy altar recipe
    public static final RegistryObject<Item> EMPTY_SUMMONING_SCROLL = ITEMS.register("empty_summoning_scroll", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SUMMONING_SCROLL_SPRING_PIXIE = ITEMS.register("summoning_scroll_spring_pixie", () -> new Item(new Item.Properties()));
    //TODO add fairy altar recipe
    public static final RegistryObject<Item> SUMMONING_SCROLL_SUMMER_PIXIE = ITEMS.register("summoning_scroll_summer_pixie", () -> new Item(new Item.Properties()));
    //TODO add fairy altar recipe
    public static final RegistryObject<Item> SUMMONING_SCROLL_AUTUMN_PIXIE = ITEMS.register("summoning_scroll_autumn_pixie", () -> new Item(new Item.Properties()));
    //TODO add fairy altar recipe
    public static final RegistryObject<Item> SUMMONING_SCROLL_WINTER_PIXIE = ITEMS.register("summoning_scroll_winter_pixie", () -> new Item(new Item.Properties()));
    //TODO add fairy altar recipe
    public static final RegistryObject<Item> PIXIE_DUST = ITEMS.register("pixie_dust", () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 30, 1), 1).build())));
    //TODO Configurations

    public static final RegistryObject<Item> MANDRAKE = ITEMS.register("mandrake", () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationMod(1.2f)
                    .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 200, 0), 1)
                    .build())));
    //TODO Configurations

    /* TODO SPAWN EGGS

    public static final Item spawnEggSpringPixie = new ForgeSpawnEggItem(() -> ModEntities.springPixie, 0xf085a9, 0xa1db67, new Item.Properties());
    public static final Item spawnEggSummerPixie = new ForgeSpawnEggItem(() -> ModEntities.summerPixie, 0xf38807, 0xfedc5a, new Item.Properties());
    public static final Item spawnEggAutumnPixie = new ForgeSpawnEggItem(() -> ModEntities.autumnPixie, 0xb73737, 0xa56259, new Item.Properties());
    public static final Item spawnEggWinterPixie = new ForgeSpawnEggItem(() -> ModEntities.winterPixie, 0x84b4be, 0x323c81, new Item.Properties());
         */

    public static void register(IEventBus eventBus) {ITEMS.register(eventBus);}
}
