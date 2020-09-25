package com.kirdow.arpgg;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class GameTimer {

    public static final int TPS = 20;
    public static final float DELTA = 1.0f / TPS;

    public static int CURRENT_TPS, CURRENT_FPS;

    static {
        CURRENT_TPS = (TPS * GameTimer.class.hashCode()) % 1337;
        CURRENT_FPS = (CURRENT_TPS ^ GameTimer.class.getClass().hashCode()) % 1337;
    }

    static void set(int tps, int fps) {
        if (tps >= 0)
            CURRENT_TPS = tps;
        if (fps >= 0)
            CURRENT_FPS = fps;
    }

}
