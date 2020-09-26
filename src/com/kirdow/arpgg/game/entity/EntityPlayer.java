package com.kirdow.arpgg.game.entity;

import com.kirdow.arpgg.GameTimer;
import com.kirdow.arpgg.game.level.Level;
import com.kirdow.arpgg.gfx.Screen;
import com.kirdow.arpgg.gfx.Textures;
import com.kirdow.arpgg.input.Input;
import com.kirdow.arpgg.util.TickerTimer;
import com.kirdow.arpgg.util.Vectorf;
import com.kirdow.arpgg.util.Vectori;

import java.awt.event.KeyEvent;

public class EntityPlayer extends Entity {

    private boolean moving;
    private boolean movingX, movingY;
    private TickerTimer inputTicker;
    private boolean movedLeft;
    private boolean movedUp;

    public EntityPlayer(Level level, int x, int y) {
        super(0, level, x, y);
        lastX = x;
        lastY = y;
        setBounds(new Vectori(16, 16));

        inputTicker = new TickerTimer(5, this::playerInput);
    }

    public EntityPlayer(Level level) {
        super(0, level);
    }

    @Override
    public void tick() {
        if (this == level.thePlayer) {
            inputTicker.exec();
        }
    }

    int lastX, lastY;
    @Override
    public void draw(Screen fb) {
        if (!moving) {
            fb.drawTexture(this.x, this.y, 16, 16, movedUp ? 16 : 0, 0, Textures.ENTITYMAP, movedLeft ^ movedUp);
        } else {
            fb.drawAnimation(this.x, this.y, 16, 16, 0, movedUp ? 48 : 16, movingX ? 150 : 225, movingX || movedUp ? 4 : 3, Textures.ENTITYMAP, movedLeft ^ movedUp);
        }
    }

    private void playerInput() {
        int moveX = 0, moveY = 0;

        if (Input.isKeyDown(KeyEvent.VK_S) || Input.isKeyDown(KeyEvent.VK_DOWN)) {
            moveY++;
        }
        if (Input.isKeyDown(KeyEvent.VK_W) || Input.isKeyDown(KeyEvent.VK_UP)) {
            moveY--;
        }
        if (Input.isKeyDown(KeyEvent.VK_A) || Input.isKeyDown(KeyEvent.VK_LEFT)) {
            moveX--;
        }
        if (Input.isKeyDown(KeyEvent.VK_D) || Input.isKeyDown(KeyEvent.VK_RIGHT)) {
            moveX++;
        }

        super.move(moveX, moveY);

        moving = lastX != x || lastY != y;
        if (movingX = lastX != x) {
            movedLeft = lastX > x;
        }

        if (movingY = lastY != y) {
            movedUp = lastY > y;
        } else if (movingX) {
            movedUp = false;
        }

        lastX = x;
        lastY = y;
    }
}
