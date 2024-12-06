package com.saphienyako.fairy_craft.block.entity;

import com.saphienyako.fairy_craft.FairyCraft;
import com.saphienyako.fairy_craft.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FairyCraft.MOD_ID);

    public static final RegistryObject<BlockEntityType<FairyAltarBlockEntity>> FAIRY_ALTAR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fairy_altar_block_entity", ()->
            BlockEntityType.Builder.of(FairyAltarBlockEntity::new,ModBlocks.FAIRY_ALTAR.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
