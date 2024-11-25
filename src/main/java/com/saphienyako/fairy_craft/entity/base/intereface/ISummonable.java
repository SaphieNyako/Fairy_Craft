package com.saphienyako.fairy_craft.entity.base.intereface;

import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

public interface ISummonable {

    @Nullable
    BlockPos getSummonPos();

    void setSummonPos(@Nullable BlockPos pos);
}
