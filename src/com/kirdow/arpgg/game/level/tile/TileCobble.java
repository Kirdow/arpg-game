package com.kirdow.arpgg.game.level.tile;

import com.kirdow.arpgg.gfx.Screen;
import com.kirdow.arpgg.gfx.Textures;

import static com.kirdow.arpgg.util.DataUtils.bits;

public class TileCobble extends TileEdgeSection {
    public TileCobble() {
        super(1, 1, 0, 0);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
