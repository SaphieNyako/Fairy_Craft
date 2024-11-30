package com.saphienyako.fairy_craft.entity;

import com.saphienyako.fairy_craft.effect.ModEffects;
import com.saphienyako.fairy_craft.entity.base.PixieBase;
import com.saphienyako.fairy_craft.entity.goals.BlessingEffectGoal;
import com.saphienyako.fairy_craft.particle.ModParticles;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public class AutumnPixieEntity extends PixieBase {
    protected AutumnPixieEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected Component getPixieNameMessage() {
        return  Component.translatable("message.fairy_craft.autumn_pixie_name");
    }

    @Override
    protected Component getPixieCookieMessage() {
        return  Component.translatable("message.fairy_craft.autumn_pixie_feed");
    }

    @Nullable
    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.AUTUMN_SPARKLE_PARTICLE.get();
    }

    @Override
    protected MobEffect getMobEffect() {
        return ModEffects.AUTUMN_BLESSING.get();
    }
}
