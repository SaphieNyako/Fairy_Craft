package com.saphienyako.fairy_craft.entity;

import com.saphienyako.fairy_craft.effect.ModEffects;
import com.saphienyako.fairy_craft.entity.base.PixieBase;
import com.saphienyako.fairy_craft.entity.goals.BlessingEffectGoal;
import com.saphienyako.fairy_craft.entity.goals.BreedAbilityGoal;
import com.saphienyako.fairy_craft.entity.goals.PanicGoal;
import com.saphienyako.fairy_craft.particle.ModParticles;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public class SpringPixieEntity extends PixieBase {

    protected SpringPixieEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected Component getPixieNameMessage() {
        return  Component.translatable("message.fairy_craft.spring_pixie_name");
    }

    @Override
    protected Component getPixieCookieMessage() {
        return  Component.translatable("message.fairy_craft.spring_pixie_feed");
    }

    @Override
    protected MobEffect getMobEffect() {
        return ModEffects.SPRING_BLESSING.get();
    }

    @Nullable
    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.SPRING_SPARKLE_PARTICLE.get();
    }
}
