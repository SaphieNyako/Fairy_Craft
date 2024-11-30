package com.saphienyako.fairy_craft.entity;

import com.saphienyako.fairy_craft.entity.base.PixieBase;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class WinterPixieEntity extends PixieBase {
    protected WinterPixieEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
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

    //TODO Custom Particles

     /*  TODO Ability
    @Override
    protected Ability<?> getDefaultAbility() {
        return ModAbilities.flowerWalk;
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.springSparkleParticle;
    }

    public Ability<?> getAbility() {
        if (this.ability == null) this.ability = this.getDefaultAbility();
        return this.ability;
    }

    public void setAbility(Ability<?> ability) {
        this.ability = ability;
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        NbtX.putResource(nbt, "PixieAbility", this.getAbility().id());
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setAbility(Ability.get(NbtX.getResource(nbt, "PixieAbility"), this.getDefaultAbility()));
    } */
}
