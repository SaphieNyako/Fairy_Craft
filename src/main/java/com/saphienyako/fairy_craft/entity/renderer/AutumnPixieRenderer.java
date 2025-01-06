package com.saphienyako.fairy_craft.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.fairy_craft.FairyCraft;
import com.saphienyako.fairy_craft.entity.AutumnPixieEntity;
import com.saphienyako.fairy_craft.entity.model.AutumnPixieModel;
import com.saphienyako.fairy_craft.entity.model.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AutumnPixieRenderer extends MobRenderer<AutumnPixieEntity, AutumnPixieModel<AutumnPixieEntity>> {

    public AutumnPixieRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AutumnPixieModel<>(pContext.bakeLayer(ModModelLayers.AUTUMN_PIXIE_LAYER)),  0.50f);
    }

    @Override
    public void render(@NotNull AutumnPixieEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AutumnPixieEntity autumnPixieEntity) {
        return new ResourceLocation(FairyCraft.MOD_ID, "textures/entity/autumn_pixie.png");
    }
}
