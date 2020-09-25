package com.kirdow.arpgg;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class GameTimer {

    private static Runnable cbTick, cbDraw;

    public static final int TPS = 60;
    public static final float DELTA = 1.0f / TPS;

    public static boolean LOCK_FRAMES = true;

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

    public static void setTickCallback(Runnable cb) {
        cbTick = cb;
    }

    public static void setDrawCallback(Runnable cb) {
        cbDraw = cb;
    }

    private static long lastNano = System.nanoTime();
    private static double queuedTicks = 0;
    private static double nanosPerTick = 1000000000.0 / TPS;
    private static int frameCounter;
    private static int tickCounter;
    private static long secondsTimer = System.currentTimeMillis();

    public static void cycle() {
        long currentNano = System.nanoTime();
        queuedTicks += (currentNano - lastNano) / nanosPerTick;
        lastNano = currentNano;

        boolean frameReady = !LOCK_FRAMES;
        while (queuedTicks >= 1) {
            tickCounter++;
            if (cbTick != null)
                cbTick.run();
            queuedTicks -= 1;
            frameReady = true;
        }

        try {
            Thread.sleep(2L);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        if (frameReady) {
            frameCounter++;
            if (cbDraw != null)
                cbDraw.run();
        }

        if (System.currentTimeMillis() - secondsTimer > 1000) {
            secondsTimer += 1000;
            System.out.println("TPS: " + tickCounter + " FPS: " + frameCounter);
            set(tickCounter, frameCounter);
            frameCounter = tickCounter = 0;
        }
    }

}
