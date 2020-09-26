package com.kirdow.arpgg.game.level.tile;

import com.kirdow.arpgg.gfx.Screen;

public class Tile {

    public static final int TILES_MAX = 1024;
    public static final Tile[] TILES = new Tile[TILES_MAX];

    public final short id;
    public final int textureX, textureY, textureIndex, textureId;

    public Tile(int id, int texture) {
        this.id = (short)id;
        if (id < 0 || id >= TILES_MAX)
            throw new RuntimeException(String.format("Invalid tile id %d! Valid range is 0-%d.", id, TILES_MAX-1));
        if (TILES[id] != null)
            throw new RuntimeException("Duplicate Tile ID found");
        TILES[id] = this;

        textureId = texture / 256;
        textureIndex = texture % 256;
        textureX = textureIndex % 16;
        textureY = textureIndex / 16;
    }

    public void draw(Screen fb, int x, int y, int data) {
        fb.drawTile(x, y, textureIndex);
    }

    public static final Tile tileSand = new TileSand();
    public static final Tile tileCobble = new TileCobble();

}
