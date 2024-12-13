package com.saphienyako.fairy_craft.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.fairy_craft.FairyCraft;
import com.saphienyako.fairy_craft.entity.SpringPixieEntity;
import com.saphienyako.fairy_craft.entity.model.ModModelLayers;
import com.saphienyako.fairy_craft.entity.model.SpringPixieModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SpringPixieRenderer extends MobRenderer<SpringPixieEntity, SpringPixieModel<SpringPixieEntity>> {

    public SpringPixieRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SpringPixieModel<>(pContext.bakeLayer(ModModelLayers.SPRING_PIXIE_LAYER)),  0.50f);
    }

    @Override
    public void render(@NotNull SpringPixieEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull SpringPixieEntity springPixieEntity) {
        return new ResourceLocation(FairyCraft.MOD_ID, "textures/entity/spring_pixie.png");
    }
}
