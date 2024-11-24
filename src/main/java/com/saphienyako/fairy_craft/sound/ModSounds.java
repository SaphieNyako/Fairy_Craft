package com.saphienyako.fairy_craft.sound;

import com.saphienyako.fairy_craft.FairyCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FairyCraft.MOD_ID);

    public static final RegistryObject<SoundEvent> MANDRAKE_SCREAM = registerSoundEvents("mandrake_scream");
    public static final RegistryObject<SoundEvent> FAIRY_CRAFT_MUSIC_DISC = registerSoundEvents("fairy_craft_music_disc");


    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FairyCraft.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
