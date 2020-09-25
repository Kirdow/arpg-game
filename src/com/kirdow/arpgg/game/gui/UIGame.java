package com.kirdow.arpgg.game.gui;

import com.kirdow.arpgg.GameTimer;
import com.kirdow.arpgg.game.level.Level;
import com.kirdow.arpgg.gfx.Screen;
import com.kirdow.arpgg.input.Input;

import java.awt.event.KeyEvent;

public class UIGame extends UIBase {

    private UIPauseMenu pauseMenu;

    private float playerX;
    private float playerY;

    private Level level;

    public UIGame() {
        super("Game");
        pauseMenu = null;
    }

    @Override
    public void init() {
        super.init();

        playerX = 0;
        playerY = 0;

        level = new Level(64, 64);
    }

    @Override
    public void tick() {
        if (pauseMenu != null) {
            pauseMenu.tick();
            return;
        }

        if (level != null) {
            level.tick();
        }
        int xMove = 0, yMove = 0;
        float speed = 12f;

        if (Input.isKeyDown(KeyEvent.VK_A)) {
            --xMove;
        }
        if (Input.isKeyDown(KeyEvent.VK_D)) {
            ++xMove;
        }

        if (Input.isKeyDown(KeyEvent.VK_W)) {
            --yMove;
        }
        if (Input.isKeyDown(KeyEvent.VK_S)) {
            ++yMove;
        }

        if (xMove != 0)
            playerX += GameTimer.DELTA * xMove * speed;
        if (yMove != 0)
            playerY += GameTimer.DELTA * yMove * speed;
    }

    @Override
    public void draw(Screen fb) {
        if (level != null) {
            level.draw(fb);
        }

        if (pauseMenu != null) {
            pauseMenu.draw(fb);
        }
    }

    @Override
    public void keyTyped(char typedChar, int charCode) {
        if (this.pauseMenu != null) {
            this.pauseMenu.keyTyped(typedChar, charCode);
            return;
        } else if (typedChar == KeyEvent.VK_ESCAPE) {
            this.pauseMenu = new UIPauseMenu(this);
            this.pauseMenu.init();
            return;
        }

        super.keyTyped(typedChar, charCode);
    }

    public void unpause() {
        if (pauseMenu != null) {
            pauseMenu.dispose();
            pauseMenu = null;
        }
    }

    public int getTileFromPixel(int x, int y) {
        x = (int)((x  + this.playerX * 4.0f) / 64.0f);
        y = (int)((y  + this.playerY * 4.0f) / 64.0f);

        return getTile(x, y);
    }

    public int getTile(int x, int y) {
        int p = (x + y) % 2;
        return p < 0 ? (p + 2) : p;
    }
}
