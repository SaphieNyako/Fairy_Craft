package com.saphienyako.fairy_craft.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.saphienyako.fairy_craft.block.entity.ClientTickHandler;
import com.saphienyako.fairy_craft.block.entity.FairyAltarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
@OnlyIn(Dist.CLIENT)
public class FairyAltarBlockRenderer<T extends FairyAltarBlockEntity> implements BlockEntityRenderer<FairyAltarBlockEntity> {


    public FairyAltarBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(FairyAltarBlockEntity altar, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light, int overlay) {

        double progressScaled = altar.getProgress() / (double) altar.getMaxProgress();

        List<ItemStack> stacks = new ArrayList<>();
        for (int slot = 0; slot < altar.getInventory().getSlots(); slot++) {
            ItemStack stack = altar.getInventory().getStackInSlot(slot);
            if (!stack.isEmpty()) stacks.add(stack);
        }

        if (!stacks.isEmpty()) {
            double anglePerStack = (2 * Math.PI) / stacks.size();
            for (int idx = 0; idx < stacks.size(); idx++) {
                //noinspection ConstantConditions
                double shiftX = Math.cos((((double) altar.getLevel().getGameTime() + partialTick) / 8) + (idx * anglePerStack)) * (1 - progressScaled);
                double shiftZ = Math.sin((((double) altar.getLevel().getGameTime() + partialTick) / 8) + (idx * anglePerStack)) * (1 - progressScaled);
                poseStack.pushPose();
                poseStack.translate(0.5 + shiftX, 1 + progressScaled, 0.5 + shiftZ);
                poseStack.mulPose(Axis.YP.rotation((ClientTickHandler.ticksInGame() + partialTick) / 20));
                poseStack.scale(0.85f, 0.85f, 0.85f);
                Minecraft.getInstance().getItemRenderer().renderStatic(stacks.get(idx), ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, poseStack, buffer, altar.getLevel(), 0);
                poseStack.popPose();
            }
        }
    }
}
