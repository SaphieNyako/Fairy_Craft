package com.saphienyako.fairy_craft.block.entity;

import net.minecraftforge.event.TickEvent;

public class ClientTickHandler {
    private static int ticksInGame = 0;

    public ClientTickHandler() {
    }

    public static int ticksInGame() {
        return ticksInGame;
    }

    public static void tick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            ++ticksInGame;
        }

    }
}
