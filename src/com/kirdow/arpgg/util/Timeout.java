package com.kirdow.arpgg.util;

public class Timeout {

    private long cooldown;
    private long activated;

    public Timeout(long cooldown) {
        this.cooldown = cooldown;
    }

    public long length() {
        return cooldown;
    }

    public void set() {
        activated = System.currentTimeMillis();
    }

    public void reset() {
        activated -= cooldown;
    }

    public long timeLeft() {
        long now = System.currentTimeMillis();
        if (activated + cooldown <= now) return 0L;
        return activated + cooldown - now;
    }

    public long timePassed() {
        return System.currentTimeMillis() - activated;
    }

    public long instancePassed(long instanceLength) {
        return timePassed() / instanceLength;
    }

    public boolean ready() {
        return activated + cooldown <= System.currentTimeMillis();
    }

    public boolean active() {
        return !ready();
    }

}
