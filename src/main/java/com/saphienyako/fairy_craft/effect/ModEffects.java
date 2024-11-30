package com.saphienyako.fairy_craft.effect;

import com.saphienyako.fairy_craft.FairyCraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FairyCraft.MOD_ID);

    public static final RegistryObject<MobEffect> WINTER_BLESSING = MOB_EFFECTS.register("winter_blessing", WinterBlessingEffect::new);
    public static final RegistryObject<MobEffect> AUTUMN_BLESSING = MOB_EFFECTS.register("autumn_blessing", AutumnBlessingEffect::new);

    public static final RegistryObject<MobEffect> SUMMER_BLESSING = MOB_EFFECTS.register("summer_blessing", SummerBlessingEffect::new);

    public static final RegistryObject<MobEffect> SPRING_BLESSING = MOB_EFFECTS.register("spring_blessing", SpringBlessingEffect::new);

    public static final RegistryObject<MobEffect> FAIRY_FLYING = MOB_EFFECTS.register("fairy_flying", FairyFlyingEffect::new);

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
