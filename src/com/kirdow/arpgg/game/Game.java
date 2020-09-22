package com.kirdow.arpgg.game;

import com.kirdow.arpgg.gfx.Screen;

import java.util.Random;

public class Game {

    private static Game game;

    private Game() {
        Game.game = this;
    }

    public void tick() {

    }

    private final Random r = new Random();
    public void draw(Screen fb) {
        for (int y = 0; y < fb.h; y++) {
            for (int x = 0; x < fb.w; x++) {
                fb.pixels[x + y * fb.w] = r.nextInt();
            }
        }
    }

    public static final Game getGame() {
        return game != null ? game : new Game();
    }

}
