package com.kirdow.arpgg.game.level;

import com.kirdow.arpgg.game.level.tile.Tile;
import com.kirdow.arpgg.gfx.Screen;

public class Level {

    private int w, h;
    private short[] tiles;
    private byte[] data;

    public Level(int w, int h) {
        this.w = w;
        this.h = h;

        tiles = new short[w*h];
        data = new byte[w*h];

        for (int i = 0; i < w*h; i++) {
            tiles[i] = (short)1;
        }
        for (int i = 0; i < w*h; i++) {
            if (i < 2)
                tiles[i] = (short)0;
            else {
                for (int j = 2; j < (w*h) / i; j++) {
                    int k = i * j;
                    if (k >= 0 && k < w*h)
                        tiles[k] = (short)0;
                }
            }
        }
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

    public void tick() {
        x += 1.0f;
        y += 1.5f;
    }

    private float x;
    private float y;

    public void draw(Screen fb) {
        int xOffset = (int)x % 16;
        int yOffset = (int)y % 16;
        int xTileOffset = (int)x / 16;
        int yTileOffset = (int)y / 16;

        int wTiles = (int)Math.ceil(fb.w / 16.0) + 2;
        int hTiles = (int)Math.ceil(fb.h / 16.0) + 2;

        for (int ry = -1; ry < hTiles; ry++) {
            for (int rx = -1; rx < wTiles; rx++) {
                Tile tile = getTile(rx + xTileOffset, ry + yTileOffset);
                tile.draw(fb, rx * 16 - xOffset, ry * 16 - yOffset, getData(rx + xTileOffset, ry + yTileOffset));
            }
        }
    }


}
