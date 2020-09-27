package com.kirdow.arpgg.game.entity;

import com.kirdow.arpgg.GameTimer;
import com.kirdow.arpgg.game.level.Level;
import com.kirdow.arpgg.gfx.Screen;
import com.kirdow.arpgg.gfx.Textures;
import com.kirdow.arpgg.input.Input;
import com.kirdow.arpgg.input.KeyBinding;
import com.kirdow.arpgg.input.KeyBindings;
import com.kirdow.arpgg.util.*;

import java.awt.event.KeyEvent;

public class EntityPlayer extends Entity {

    private boolean moving;
    private boolean movingX, movingY;
    private TickerTimer moveTicker;
    private TickerTimer moveTickerRun;
    private boolean movedLeft;
    private boolean movedUp;
    private Timeout attackCooldown;
    private Timeout globalCooldown;
    private Timeout slashAnimation;

    public EntityPlayer(Level level, int x, int y) {
        super(0, level, x, y);
        lastX = x;
        lastY = y;
        setBounds(new Box(5, 11, 6, 5));

        moveTicker = new TickerTimer(3, this::playerMovementInput);
        moveTickerRun = new TickerTimer(2, this::playerMovementInput);
        attackCooldown = new Timeout(300L);
        globalCooldown = new Timeout(500L);
        slashAnimation = new Timeout(80L);
    }

    public EntityPlayer(Level level) {
        this(level, 0, 0);
    }

    public void findSpawn() {
        for (int y = 0; y < level.h; y++) {
            for (int x = 0; x < level.w; x++) {
                if (!level.getTile(x, y).isSolid()) {
                    this.lastX = this.x = x * 16 + 8;
                    this.lastY = this.y = y * 16 + 8;
                }
            }
        }
    }

    @Override
    public void tick() {
        if (this == level.thePlayer) {
            playerInput();
        }
    }

    int lastX, lastY;
    @Override
    public void draw(Screen fb) {
        if (attackCooldown.active()) {
            int frame = (int)attackCooldown.instancePassed(attackCooldown.length() / 4);
            if (frame == 3 && slashAnimation.ready()) {
                slashAnimation.set();
            }
            fb.drawAnimationFrame(this.x, this.y, 16, 16, 0, 64, frame, Textures.ENTITYMAP, movedLeft);
        } else if (!moving) {
            fb.drawTexture(this.x, this.y, 16, 16, movedUp ? 16 : 0, 0, Textures.ENTITYMAP, movedLeft ^ movedUp);
        } else {
            fb.drawAnimation(this.x, this.y, 16, 16, 0, movedUp ? 48 : 16, movingX ? 150 : 225, movingX || movedUp ? 4 : 3, Textures.ENTITYMAP, movedLeft ^ movedUp);
        }
        if (slashAnimation.active()) {
            int frame = (int)slashAnimation.instancePassed(slashAnimation.length() / 5);
            fb.drawAnimationFrame(this.x + (movedLeft ? -12 : 12), this.y, 16, 16, 0, 16, frame, Textures.ATTACKANIMATIONS, movedLeft);
        }
    }

    private void playerInput() {
        (KeyBindings.isMoveFast() ? moveTickerRun : moveTicker).exec();
        attackInput();
    }

    private void playerMovementInput() {
        int moveX = 0, moveY = 0;

        if (!KeyBindings.isMoveStop()) {
            if (KeyBindings.isMoveDown()) {
                moveY++;
            }
            if (KeyBindings.isMoveUp()) {
                moveY--;
            }
            if (KeyBindings.isMoveLeft()) {
                moveX--;
            }
            if (KeyBindings.isMoveRight()) {
                moveX++;
            }
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

    private void attackInput() {
        if (KeyBindings.MAIN_ATTACK.isKeyDown()) {
            if (activateCooldown(attackCooldown)) {
                attack();
            }
        }
    }

    private void attack() {

    }

    private boolean activateCooldown(Timeout cooldown) {
        if (globalCooldown.active() || cooldown.active())
            return false;

        globalCooldown.set();
        cooldown.set();
        return true;
    }
}
