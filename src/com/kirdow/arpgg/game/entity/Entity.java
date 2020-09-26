package com.kirdow.arpgg.game.entity;

import com.kirdow.arpgg.game.level.Level;
import com.kirdow.arpgg.game.level.tile.Tile;
import com.kirdow.arpgg.gfx.Screen;
import com.kirdow.arpgg.util.Box;
import com.kirdow.arpgg.util.Vectori;

public class Entity {

    public static final Box DEFAULT_BOUNDS = new Box(0, 0, 16, 16);

    public final int id;
    public int x, y;

    protected final Level level;

    private Box bounds;

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

        Box entityBounds = bounds(x, y);

        int x0 = entityBounds.x >> 4;
        int y0 = entityBounds.y >> 4;
        int x1 = (entityBounds.x + entityBounds.w) >> 4;
        int y1 = (entityBounds.y + entityBounds.h) >> 4;

        Tile tile;
        for (int tileY = y0; tileY <= y1; tileY++) {
            for (int tileX = x0; tileX <= x1; tileX++) {
                tile = level.getTile(tileX, tileY);
                if (tile.isSolid() && entityBounds.intersect(tileX * 16, tileY * 16, 16, 16)) {
                    System.out.println(tileX + " " + tileY + " " + this.x + " " + this.y);
                    return false;
                }
            }
        }
        // TODO: Implement tile & entity collision detection

        return true;
    }

    public void setBounds(Box bounds) {
        this.bounds = new Box(bounds.x - 8, bounds.y - 8, bounds.w, bounds.h);
    }

    public Box relativeBounds() {
        if (this.bounds != null) {
            return this.bounds;
        }

        return DEFAULT_BOUNDS;
    }

    public Box bounds() {
        return bounds(0, 0);
    }

    public Box bounds(int ox, int oy) {
        Box bounds = relativeBounds();
        return new Box(bounds.x + this.x + ox, bounds.y + this.y + oy, bounds.w, bounds.h);
    }

}
