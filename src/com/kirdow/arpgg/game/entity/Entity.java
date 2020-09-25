package com.kirdow.arpgg.game.entity;

import com.kirdow.arpgg.game.level.Level;
import com.kirdow.arpgg.gfx.Screen;
import com.kirdow.arpgg.util.Vectori;

public class Entity {

    public final int id;
    public float x, y;

    protected final Level level;

    private Vectori bounds;

    public Entity(int id, Level level, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.level = level;
    }

    public Entity(int id, Level level) {
        this(id, level, 0.0f, 0.0f);
    }

    public Level getLevel() {
        return level;
    }

    public void tick() {}
    public void draw(Screen fb) {}

    public void move(float x, float y) {
        if (x != 0 && move2(x, 0))
            this.x += x;
        if (y != 0 && move2(0, y))
            this.y += y;
    }

    public boolean move2(float x, float y) {
        if (x != 0 && y != 0)
            return false;

        // TODO: Implement tile & entity collision detection

        return true;
    }

    public void setBounds(Vectori bounds) {
        this.bounds = bounds;
    }

    public Vectori bounds() {
        return this.bounds;
    }

}
