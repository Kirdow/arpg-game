package com.kirdow.arpgg.game.level.tile;

import java.util.Random;

public class TileWater extends TileEdgeSection {

    public TileWater() {
        super(2, 2, 0, 32);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public int getU(int x, int y) {
        Random r = new Random((x << 18) + y);
        int p = r.nextInt(13);
        if (p < 10)
            return 64;
        else if (p < 12)
            return 48;
        return 32;
    }
}
