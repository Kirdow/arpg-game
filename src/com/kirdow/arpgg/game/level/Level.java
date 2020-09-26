package com.kirdow.arpgg.game.level;

import com.kirdow.arpgg.GameTimer;
import com.kirdow.arpgg.game.entity.Entity;
import com.kirdow.arpgg.game.entity.EntityPlayer;
import com.kirdow.arpgg.game.level.tile.Tile;
import com.kirdow.arpgg.gfx.Screen;
import com.kirdow.arpgg.util.Box;
import com.kirdow.arpgg.util.Vectori;

import java.util.*;

public class Level {

    public final int w, h;
    private short[] tiles;
    private byte[] data;

    private List<Entity> entityList;
    public final Comparator<Entity> entitySorter = (o1, o2) -> {
        if (o1.y < o2.y) return -1;
        if (o1.y > o2.y) return +1;
        return 0;
    };
    public EntityPlayer thePlayer;

    public Level(int w, int h) {
        this.w = w;
        this.h = h;

        tiles = new short[w*h];
        data = new byte[w*h];
        entityList = new ArrayList<>();

        addEntity(new EntityPlayer(this, 0, 0));

        for (int i = 0; i < w*h; i++) {
            tiles[i] = (short)1;
        }
        Random r = new Random();
        for (int i = 0; i < w*h; i++) {
            if (r.nextInt(2) == 0)
                tiles[i] = (short)0;
        }
        tiles[0] = (short)1;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= w || y >= h) {
            return Tile.tileSand;
        }

        return Tile.TILES[tiles[x + y * w]];
    }

    public void setTile(int x, int y, Tile tile, int data) {
        if (x < 0 || y < 0 || x >= w || y >= h) return;
        tiles[x + y * w] = tile.id;
        this.data[x + y * w] = (byte)data;
    }

    public int getData(int x, int y) {
        if (x < 0 || y < 0 || x >= w || y >= h) return 0;
        return data[x + y * w] & 0xFF;
    }

    public void setData(int x, int y, int data) {
        if (x < 0 || y < 0 || x >= w || y >= h) return;
        this.data[x + y * w] = (byte)data;
    }

    public void addEntity(Entity entity) {
        this.entityList.add(entity);
    }

    private void createPlayer() {
        EntityPlayer player = new EntityPlayer(this);
        player.findSpawn();
        this.addEntity(player);
        thePlayer = player;
    }

    public void tick() {
        if (thePlayer == null) {
            createPlayer();
        }

        Entity entity = null;
        for (int i = 0; i < entityList.size(); i++) {
            entity = entityList.get(i);
            if (entity == thePlayer)
                continue;
            entity.tick();
        }

        thePlayer.tick();

        Tile tile;
        boolean up, down, left, right;
        boolean ul, ur, dl, dr;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                tile = getTile(x, y);
                if (tile == Tile.tileCobble) {
                    up = getTile(x, y - 1) != Tile.tileCobble;
                    down = getTile(x, y + 1) != Tile.tileCobble;
                    left = getTile(x - 1, y) != Tile.tileCobble;
                    right = getTile(x + 1, y) != Tile.tileCobble;
                    ul = getTile(x - 1, y - 1) != Tile.tileCobble;
                    ur = getTile(x + 1, y - 1) != Tile.tileCobble;
                    dl = getTile(x - 1, y + 1) != Tile.tileCobble;
                    dr = getTile(x + 1, y + 1) != Tile.tileCobble;

                    int data = 0;
                    if (ul)
                        data |= 1 << 7;
                    if (up)
                        data |= 1 << 6;
                    if (ur)
                        data |= 1 << 5;
                    if (left)
                        data |= 1 << 4;
                    if (right)
                        data |= 1 << 3;
                    if (dl)
                        data |= 1 << 2;
                    if (down)
                        data |= 1 << 1;
                    if (dr)
                        data |= 1 << 0;

                    setData(x, y, data);
                }
            }
        }
    }

    public void draw(Screen fb) {
        int x = (int)((thePlayer != null ? thePlayer.x : 0.0f) - fb.w / 2);
        int y = (int)((thePlayer != null ? thePlayer.y : 0.0f) - (fb.h - 8) / 2);
        Vectori drawPosition = new Vectori(x, y);

        drawTiles(fb, drawPosition);
        drawEntities(fb, drawPosition);
    }

    public void drawTiles(Screen fb, Vectori drawPos) {
        int xOffset = drawPos.ix % 16;
        int yOffset = drawPos.iy % 16;
        int xTileOffset = drawPos.ix / 16;
        int yTileOffset = drawPos.iy / 16;

        int wTiles = (int)Math.ceil(fb.w / 16.0) + 2;
        int hTiles = (int)Math.ceil(fb.h / 16.0) + 2;

        fb.translate(drawPos.ix, drawPos.iy);
        for (int ry = yTileOffset - 1; ry <= yTileOffset + hTiles; ry++) {
            for (int rx = xTileOffset - 1; rx <= xTileOffset + wTiles; rx++) {
                Tile tile = getTile(rx, ry);
                int data = getData(rx, ry);
                tile.draw(fb, rx * 16, ry * 16, data);
            }
        }
        fb.resetTranslation();
    }

    public void drawEntities(Screen fb, Vectori drawPos) {
        Entity entity;
        Box b;

        entityList.sort(entitySorter);

        fb.translate(drawPos.ix, drawPos.iy);
        for (int i = 0; i < entityList.size(); i++) {
            entity = entityList.get(i);
            if (entity != null) {
                b = Entity.DEFAULT_BOUNDS;
                if (b == null) continue;

                fb.translate(b.w / 2, b.h / 2);
                entity.draw(fb);
                fb.translate(-b.w / 2, -b.h / 2);
            }
        }
        fb.resetTranslation();
    }


}
