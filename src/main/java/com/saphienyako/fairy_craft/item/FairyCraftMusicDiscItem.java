package com.saphienyako.fairy_craft.item;

import com.saphienyako.fairy_craft.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class FairyCraftMusicDiscItem extends RecordItem {

    public FairyCraftMusicDiscItem() {
        super(1, ModSounds.FAIRY_CRAFT_MUSIC_DISC.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 1880);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        if (level != null) {
            tooltip.add(Component.translatable("message.fairy_craft.music_disc").withStyle(ChatFormatting.GOLD));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }

}
