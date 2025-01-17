package com.saphienyako.fairy_craft.entity.base.intereface;

public interface ITameable {

    boolean isTamed();

    // Tries to set tamed state. May not always work. For example
    // ownables are only tamed if owned, so passing `true` here
    // will do nothing.
    // Returns whether it was successful.
    boolean trySetTamed(boolean tamed);
}
