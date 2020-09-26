package com.kirdow.arpgg.game.entity;

import com.kirdow.arpgg.game.level.Level;
import com.kirdow.arpgg.gfx.Screen;
import com.kirdow.arpgg.util.Vectori;

public class Entity {

    public final int id;
    public int x, y;

    protected final Level level;

    private Vectori bounds;

    public Entity(int id, Level level, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.level = level;
    }

    public Entity(int id, Level level) {
        this(id, level, 0, 0);
    }

    public Level getLevel() {
        return level;
    }

    public void tick() {}
    public void draw(Screen fb) {}

    public void move(int x, int y) {
        if (x != 0 && move2(x, 0))
            this.x += x;
        if (y != 0 && move2(0, y))
            this.y += y;
    }

    public boolean move2(int x, int y) {
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
