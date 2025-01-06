package com.saphienyako.fairy_craft.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.fairy_craft.FairyCraft;
import com.saphienyako.fairy_craft.entity.SummerPixieEntity;
import com.saphienyako.fairy_craft.entity.model.ModModelLayers;
import com.saphienyako.fairy_craft.entity.model.SummerPixieModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SummerPixieRenderer extends MobRenderer<SummerPixieEntity, SummerPixieModel<SummerPixieEntity>> {
    public SummerPixieRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SummerPixieModel<>(pContext.bakeLayer(ModModelLayers.SUMMER_PIXIE_LAYER)),  0.50f);
    }

    @Override
    public void render(@NotNull SummerPixieEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull SummerPixieEntity summerPixieEntity) {
        return new ResourceLocation(FairyCraft.MOD_ID, "textures/entity/summer_pixie.png");
    }
}
