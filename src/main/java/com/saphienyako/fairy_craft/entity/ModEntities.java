package com.saphienyako.fairy_craft.entity;

import com.saphienyako.fairy_craft.FairyCraft;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FairyCraft.MOD_ID);

    public static final RegistryObject<EntityType<SpringPixieEntity>> SPRING_PIXIE =
            ENTITY_TYPES.register("spring_pixie", () -> EntityType.Builder.of(SpringPixieEntity::new, MobCategory.CREATURE)
                    .sized(0.7f, 1).build("spring_pixie")); //TODO check size problems with hit box in old version


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
