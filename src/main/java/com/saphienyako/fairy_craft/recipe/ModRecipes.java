package com.saphienyako.fairy_craft.recipe;

import com.saphienyako.fairy_craft.FairyCraft;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FairyCraft.MOD_ID);

    public static final RegistryObject<RecipeSerializer<FairyAltarRecipe>> FAIRY_ALTAR_SERIALIZER =
            SERIALIZERS.register("fairy_altar", () -> FairyAltarRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }

}
