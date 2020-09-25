package com.kirdow.arpgg.game.entity;

import com.kirdow.arpgg.GameTimer;
import com.kirdow.arpgg.game.level.Level;
import com.kirdow.arpgg.gfx.Screen;
import com.kirdow.arpgg.gfx.Textures;
import com.kirdow.arpgg.util.Vectori;

public class EntityPlayer extends Entity {

    public EntityPlayer(Level level, float x, float y) {
        super(0, level, x, y);
        setBounds(new Vectori(16, 16));
    }

    public EntityPlayer(Level level) {
        super(0, level);
    }

    @Override
    public void tick() {
        if (this == level.thePlayer)
            move(0, GameTimer.DELTA * 5.0f);
    }

    @Override
    public void draw(Screen fb) {
        boolean isPlayer = this == level.thePlayer;
        fb.drawTexture((int)this.x, (int)this.y, 16, 16, isPlayer ? 0 : 16, 0, isPlayer ? Textures.DEFAULT_ALPHA_TEXTURE : Textures.TILEMAP);
    }
}
