package com.kirdow.arpgg;

import com.kirdow.arpgg.game.Game;
import com.kirdow.arpgg.gfx.Screen;
import com.kirdow.arpgg.input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

public class Display extends Canvas {

    private static int width, height;
    private static Display display;

    private JFrame frame;
    private BufferedImage image;
    private int[] pixels;
    private Screen fbScreen;

    private Thread gameThread;
    private boolean running;

    private Game game;
    private Input input;

    private Display(int width, int height) {
        Display.width = width;
        Display.height = height;
        Display.display = this;
    }

    public void start() {
        if (gameThread != null || running) return;

        initDisplay();

        gameThread = new Thread(this::run, "ARPGG");
        running = true;
        gameThread.start();
    }

    public void stop() {
        if (gameThread == null || !running) return;

        running = false;
    }

    private void initDisplay() {
        Dimension dim = new Dimension(width, height);
        this.setMinimumSize(dim);
        this.setMaximumSize(dim);
        this.setPreferredSize(dim);

        input = new Input();
        this.addKeyListener(input);
        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        frame = new JFrame("ARPGG");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void init() {
        image = new BufferedImage(width / 4, height / 4, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        fbScreen = new Screen(width / 4, height / 4);

        game = Game.getGame();
    }

    private void run() {
        init();

        final long SEC = 1_000_000_000;
        final long tickDelay = SEC / GameTimer.TPS;
        long ticks = 0;
        long frames = 0;
        long frameTimer = System.nanoTime();
        long tickTimer = System.nanoTime();
        long lastTick = System.nanoTime();
        while (running) {
            long currentTick = System.nanoTime();
            long tickDiff = currentTick - lastTick;
            if (tickDiff > tickDelay) {
                ticks++;
                if ((currentTick - tickTimer) >= SEC) {
                    GameTimer.set((int)ticks, -1);
                    ticks = 0;
                    tickTimer = currentTick;
                }
                lastTick = currentTick;
                tick();
            }

            long currentFrame = System.nanoTime();
            long frameDiff = currentFrame - frameTimer;
            frames++;
            if (frameDiff > SEC) {
                GameTimer.set(-1, (int)frames);
                System.out.printf("FPS: %d\n", frames);
                frames = 0;
                frameTimer = currentFrame;
            }
            render();
        }
    }

    private void tick() {
        game.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            this.requestFocus();
            return;
        }

        fbScreen.clear(0);

        game.draw(fbScreen);

        fbScreen.flush(pixels);

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        bs.show();
    }

    public static Display createDisplay(int width, int height) {
        if (display != null) return display;

        return new Display(width, height);
    }

    public static Display getDisplay() {
        if (display != null) return display;

        return new Display(800, 600);
    }


}
