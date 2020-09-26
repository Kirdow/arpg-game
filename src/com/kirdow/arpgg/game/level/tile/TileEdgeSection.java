package com.kirdow.arpgg.game.level.tile;

import com.kirdow.arpgg.gfx.Screen;
import com.kirdow.arpgg.gfx.Textures;

import static com.kirdow.arpgg.util.DataUtils.bits;

public class TileEdgeSection extends Tile {

    private int u, v;

    public TileEdgeSection(int id, int texture, int u, int v) {
        super(id, texture);

        this.u = u;
        this.v = v;
    }

    @Override
    public void draw(Screen fb, int x, int y, int data) {
        if (data == 0) {
            super.draw(fb, x, y, data);
        } else {
            int textureU = getU(x, y);
            int textureV = getV(x, y);

            fb.drawTexture(x, y, 16, 16, 0, 0, Textures.TILEMAP);

            // TOP LEFT

            if (bits(data, 0b11010000, 0b10000000)) {
                fb.drawTexture(x, y, 8, 8, u + 16, v, Textures.WALLS);
            } else if (bits(data, 0b01010000, 0b01000000)) {
                fb.drawTexture(x, y, 8, 8, u + 32, v, Textures.WALLS);
            } else if (bits(data, 0b01010000, 0b00010000)) {
                fb.drawTexture(x, y, 8, 8, u + 48, v, Textures.WALLS);
            } else if (bits(data, 0b11010000, 0)) {
                fb.drawTexture(x, y, 8, 8, textureU, textureV, Textures.TILEMAP);
            } else if (bits(data, 0b01010000)) {
                fb.drawTexture(x, y, 8, 8, u, v, Textures.WALLS);
            }

            // TOP RIGHT

            if (bits(data, 0b01101000, 0b00100000)) {
                fb.drawTexture(x + 8, y, 8, 8, u + 24, v, Textures.WALLS);
            } else if (bits(data, 0b01001000, 0b01000000)) {
                fb.drawTexture(x + 8, y, 8, 8, u + 40, v, Textures.WALLS);
            } else if (bits(data, 0b01001000, 0b00001000)) {
                fb.drawTexture(x + 8, y, 8, 8, u + 56, v, Textures.WALLS);
            } else if (bits(data, 0b01101000, 0)) {
                fb.drawTexture(x + 8, y, 8, 8, textureU + 8, textureV, Textures.TILEMAP);
            } else if (bits(data, 0b01001000)) {
                fb.drawTexture(x + 8, y, 8, 8, u + 8, v, Textures.WALLS);
            }

            // BOTTOM LEFT

            if (bits(data, 0b00010110, 0b00000100)) {
                fb.drawTexture(x, y + 8, 8, 8, u + 16, v + 8, Textures.WALLS);
            } else if (bits(data, 0b00010010, 0b00010000)) {
                fb.drawTexture(x, y + 8, 8, 8, u + 48, v + 8, Textures.WALLS);
            } else if (bits(data, 0b00010010, 0b00000010)) {
                fb.drawTexture(x, y + 8, 8, 8, u + 32, v + 8, Textures.WALLS);
            } else if (bits(data, 0b00010110, 0)) {
                fb.drawTexture(x, y + 8, 8, 8, textureU, textureV + 8, Textures.TILEMAP);
            } else if (bits(data, 0b00010010)) {
                fb.drawTexture(x, y + 8, 8, 8, u, v + 8, Textures.WALLS);
            }

            // BOTTOM RIGHT

            if (bits(data, 0b00001011, 0b00000001)) {
                fb.drawTexture(x + 8, y + 8, 8, 8, u + 24, v + 8, Textures.WALLS);
            } else if (bits(data, 0b00001010, 0b00001000)) {
                fb.drawTexture(x + 8, y + 8, 8, 8, u + 56, v + 8, Textures.WALLS);
            } else if (bits(data, 0b00001010, 0b00000010)) {
                fb.drawTexture(x + 8, y + 8, 8, 8, u + 40, v + 8, Textures.WALLS);
            } else if (bits(data, 0b00001011, 0)) {
                fb.drawTexture(x + 8, y + 8, 8, 8, textureU + 8, textureV + 8, Textures.TILEMAP);
            } else if (bits(data, 0b00001010)) {
                fb.drawTexture(x + 8, y + 8, 8, 8, u + 8, v + 8, Textures.WALLS);
            }
        }
    }
}
