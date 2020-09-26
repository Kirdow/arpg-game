package com.kirdow.arpgg.util;

public class TickerTimer {

    private Runnable callback;
    private int ticks;
    private int tickCounter;

    public TickerTimer(int ticks) {
        this.ticks = ticks;
        this.tickCounter = 0;
    }

    public TickerTimer(int ticks, Runnable callback) {
        this(ticks);
        this.callback = callback;
    }

    public TickerTimer setCallback(Runnable callback) {
        this.callback = callback;

        return this;
    }

    public void exec() {
        this.exec(null);
    }

    public void exec(Runnable callback) {
        if (callback == null)
            callback = this.callback;

        if (++tickCounter % ticks == 0) {
            if (callback != null)
                callback.run();
            tickCounter = 0;
        }
    }

}
