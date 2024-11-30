package com.saphienyako.fairy_craft;

import com.mojang.logging.LogUtils;
import com.saphienyako.fairy_craft.block.ModBlocks;
import com.saphienyako.fairy_craft.effect.ModEffects;
import com.saphienyako.fairy_craft.entity.*;
import com.saphienyako.fairy_craft.entity.model.*;
import com.saphienyako.fairy_craft.entity.renderer.AutumnPixieRenderer;
import com.saphienyako.fairy_craft.entity.renderer.SpringPixieRenderer;
import com.saphienyako.fairy_craft.entity.renderer.SummerPixieRenderer;
import com.saphienyako.fairy_craft.entity.renderer.WinterPixieRenderer;
import com.saphienyako.fairy_craft.item.ModCreativeModeTab;
import com.saphienyako.fairy_craft.item.ModItems;
import com.saphienyako.fairy_craft.network.FairyCraftNetwork;
import com.saphienyako.fairy_craft.particle.LeafParticle;
import com.saphienyako.fairy_craft.particle.ModParticles;
import com.saphienyako.fairy_craft.particle.SparkleParticle;
import com.saphienyako.fairy_craft.sound.ModSounds;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;
import org.slf4j.Logger;

import javax.annotation.Nonnull;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FairyCraft.MOD_ID)
public class FairyCraft
{
    public static final String MOD_ID = "fairy_craft";
    private static final Logger LOGGER = LogUtils.getLogger();

    public FairyCraft() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::entityAttributes);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerLayer);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerParticles));

        ModCreativeModeTab.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);
        ModEffects.register(modEventBus);
        ModParticles.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        //modEventBus.addListener(this::addCreative);
    }

    private void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SPRING_PIXIE.get(), SpringPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.AUTUMN_PIXIE.get(), AutumnPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.SUMMER_PIXIE.get(), SummerPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.WINTER_PIXIE.get(), WinterPixieEntity.getDefaultAttributes().build());
    }

    @OnlyIn(Dist.CLIENT)
    private void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.SPRING_PIXIE_LAYER, SpringPixieModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.AUTUMN_PIXIE_LAYER, AutumnPixieModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SUMMER_PIXIE_LAYER, SummerPixieModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.WINTER_PIXIE_LAYER, WinterPixieModel::createBodyLayer);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(FairyCraftNetwork::register);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        //Added ModCreativeModeTab for the mod itself
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.SPRING_PIXIE.get(), SpringPixieRenderer::new);
            EntityRenderers.register(ModEntities.AUTUMN_PIXIE.get(), AutumnPixieRenderer::new);
            EntityRenderers.register(ModEntities.SUMMER_PIXIE.get(), SummerPixieRenderer::new);
            EntityRenderers.register(ModEntities.WINTER_PIXIE.get(), WinterPixieRenderer::new);
        }
    }

    public void registerParticles(RegisterParticleProvidersEvent event) {
        /* TODO TO ADD
        event.registerSpriteSet(ModParticles.springLeafParticle, LeafParticle.Factory::new);
        event.registerSpriteSet(ModParticles.summerLeafParticle, LeafParticle.Factory::new);
        event.registerSpriteSet(ModParticles.winterLeafParticle, LeafParticle.Factory::new);
        event.registerSpriteSet(ModParticles.hexenLeafParticle, LeafParticle.Factory::new);
        event.registerSpriteSet(ModParticles.blossomLeafParticle, LeafParticle.Factory::new);
        event.registerSpriteSet(ModParticles.frostWalkParticle, LeafParticle.Factory::new);

         */
        event.registerSpriteSet(ModParticles.AUTUMN_LEAF_PARTICLE.get(), LeafParticle.Factory::new);
        event.registerSpriteSet(ModParticles.SPRING_SPARKLE_PARTICLE.get(), SparkleParticle.provider(0, 1, 0));
        event.registerSpriteSet(ModParticles.SUMMER_SPARKLE_PARTICLE.get(), SparkleParticle.provider(1, 0.8f, 0));
        event.registerSpriteSet(ModParticles.AUTUMN_SPARKLE_PARTICLE.get(), SparkleParticle.provider(1, 0.4f, 0));
        event.registerSpriteSet(ModParticles.WINTER_SPARKLE_PARTICLE.get(), SparkleParticle.provider(0.2f, 0.8f, 0.9f));
    }
}
