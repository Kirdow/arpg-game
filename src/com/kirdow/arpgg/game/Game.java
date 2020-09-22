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

    public void draw(Screen fb) {
        
    }

    public static final Game getGame() {
        return game != null ? game : new Game();
    }

}
