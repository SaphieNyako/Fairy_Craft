package com.saphienyako.fairy_craft.recipe;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.saphienyako.fairy_craft.FairyCraft;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FairyAltarRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final List<Ingredient> inputs;
    private final NonNullList<Ingredient> inputList;

    public FairyAltarRecipe(ResourceLocation id, ItemStack output, List<Ingredient> inputs) {
        this.id = id;
        this.output = output;
        this.inputs = ImmutableList.copyOf(inputs);
        this.inputList = NonNullList.withSize(this.inputs.size(), Ingredient.EMPTY);
        for (int i = 0; i < this.inputs.size(); i++) this.inputList.set(i, this.inputs.get(i));
    }

    @Override
    public boolean matches(@NotNull SimpleContainer inventory, Level level) {
        List<ItemStack> stacks = new ArrayList<>();
        for(int i = 0; i < inventory.getContainerSize()-1; i++) {
            stacks.add(i, inventory.getItem(i));
        }
        //Match list with all existing ingredient list  of recipes of FairyAltarRecipes.
        return matchesLists(this.inputs, stacks);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer simpleContainer, @NotNull RegistryAccess registries) {
        return this.getResultItem(registries);
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registries) {
        return this.output.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    public static boolean matchesLists(List<Ingredient> ingredients, List<ItemStack> stacks) {
        if (ingredients.size() != stacks.size()) return false;
        List<ItemStack> left = new ArrayList<>(stacks);
        ingredients:
        for (Ingredient ingredient : ingredients) {
            Iterator<ItemStack> itr = left.iterator();
            while (itr.hasNext()) {
                if (ingredient.test(itr.next())) {
                    itr.remove();
                    continue ingredients;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<FairyAltarRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "fairy_altar";
    }

    public static class Serializer implements RecipeSerializer<FairyAltarRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(FairyCraft.MOD_ID, "fairy_altar");

        @Nonnull
        @Override
        public FairyAltarRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            ImmutableList.Builder<Ingredient> inputs = ImmutableList.builder();
            for (JsonElement jsonElement : ingredients) {
                inputs.add(Ingredient.fromJson(jsonElement));
            }
            ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
            return new FairyAltarRecipe(recipeId, output, inputs.build());
        }

        @Nullable
        @Override
        public FairyAltarRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
            int inputSize = buffer.readVarInt();
            ImmutableList.Builder<Ingredient> inputs = ImmutableList.builder();
            for (int i = 0; i < inputSize; i++) {
                inputs.add(Ingredient.fromNetwork(buffer));
            }
            ItemStack output = buffer.readItem();
            return new FairyAltarRecipe(recipeId, output, inputs.build());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, FairyAltarRecipe recipe) {
            buffer.writeVarInt(recipe.getIngredients().size());
            recipe.inputs.forEach(i -> i.toNetwork(buffer));
            buffer.writeItemStack(recipe.output, false);
        }
    }
}
