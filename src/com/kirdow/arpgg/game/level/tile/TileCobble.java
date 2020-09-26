package com.kirdow.arpgg.game.level.tile;

import com.kirdow.arpgg.gfx.Screen;
import com.kirdow.arpgg.gfx.Textures;

import static com.kirdow.arpgg.util.DataUtils.bits;

public class TileCobble extends Tile {
    public TileCobble() {
        super(1, 1);
    }

    @Override
    public void draw(Screen fb, int x, int y, int data) {
        if (data == 0) {
            super.draw(fb, x, y, data);
        } else {
            fb.drawTexture(x, y, 16, 16, 0, 0, Textures.TILEMAP);

            // TOP LEFT

            if (bits(data, 0b11010000, 0b10000000)) {
                fb.drawTexture(x, y, 8, 8, 48, 0, Textures.COBBLEWALLS);
            } else if (bits(data, 0b01010000, 0b01000000)) {
                fb.drawTexture(x, y, 8, 8, 80, 0, Textures.COBBLEWALLS);
            } else if (bits(data, 0b01010000, 0b00010000)) {
                fb.drawTexture(x, y, 8, 8, 80, 16, Textures.COBBLEWALLS);
            } else if (bits(data, 0b11010000, 0)) {
                fb.drawTexture(x, y, 8, 8, 16, 0, Textures.TILEMAP);
            } else if (bits(data, 0b01010000)) {
                fb.drawTexture(x, y, 8, 8, 0, 0, Textures.COBBLEWALLS);
            }

            // TOP RIGHT

            if (bits(data, 0b01101000, 0b00100000)) {
                fb.drawTexture(x + 8, y, 8, 8, 72, 0, Textures.COBBLEWALLS);
            } else if (bits(data, 0b01001000, 0b01000000)) {
                fb.drawTexture(x + 8, y, 8, 8, 88, 0, Textures.COBBLEWALLS);
            } else if (bits(data, 0b01001000, 0b00001000)) {
                fb.drawTexture(x + 8, y, 8, 8, 88, 16, Textures.COBBLEWALLS);
            } else if (bits(data, 0b01101000, 0)) {
                fb.drawTexture(x + 8, y, 8, 8, 24, 0, Textures.TILEMAP);
            } else if (bits(data, 0b01001000)) {
                fb.drawTexture(x + 8, y, 8, 8, 40, 0, Textures.COBBLEWALLS);
            }

            // BOTTOM LEFT

            if (bits(data, 0b00010110, 0b00000100)) {
                fb.drawTexture(x, y + 8, 8, 8, 48, 24, Textures.COBBLEWALLS);
            } else if (bits(data, 0b00010010, 0b00010000)) {
                fb.drawTexture(x, y + 8, 8, 8, 80, 24, Textures.COBBLEWALLS);
            } else if (bits(data, 0b00010010, 0b00000010)) {
                fb.drawTexture(x, y + 8, 8, 8, 80, 8, Textures.COBBLEWALLS);
            } else if (bits(data, 0b00010110, 0)) {
                fb.drawTexture(x, y + 8, 8, 8, 16, 8, Textures.TILEMAP);
            } else if (bits(data, 0b00010010)) {
                fb.drawTexture(x, y + 8, 8, 8, 0, 40, Textures.COBBLEWALLS);
            }

            // BOTTOM RIGHT

            if (bits(data, 0b00001011, 0b00000001)) {
                fb.drawTexture(x + 8, y + 8, 8, 8, 72, 24, Textures.COBBLEWALLS);
            } else if (bits(data, 0b00001010, 0b00001000)) {
                fb.drawTexture(x + 8, y + 8, 8, 8, 88, 24, Textures.COBBLEWALLS);
            } else if (bits(data, 0b00001010, 0b00000010)) {
                fb.drawTexture(x + 8, y + 8, 8, 8, 88, 8, Textures.COBBLEWALLS);
            } else if (bits(data, 0b00001011, 0)) {
                fb.drawTexture(x + 8, y + 8, 8, 8, 24, 8, Textures.TILEMAP);
            } else if (bits(data, 0b00001010)) {
                fb.drawTexture(x + 8, y + 8, 8, 8, 40, 40, Textures.COBBLEWALLS);
            }
        }
    }
}
