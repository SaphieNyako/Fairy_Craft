package com.saphienyako.fairy_craft.entity.base;

import com.saphienyako.fairy_craft.entity.base.intereface.FlyingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class FlyingFairyBase extends FairyBase implements FlyingEntity {
    protected FlyingFairyBase(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 4, true);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.registerFlyingGoals(this);
    }

    @Override
    public void travel(@Nonnull Vec3 to) {
        this.flyingTravel(this, to);
    }

    @Nonnull
    @Override
    protected PathNavigation createNavigation(@Nonnull Level level) {
        return this.createFlyingNavigation(this, level);
    }
}
