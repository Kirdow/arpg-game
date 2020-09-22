package com.kirdow.arpgg;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> argsA = Arrays.asList(args);
        Iterator<String> it = argsA.iterator();
        while (it.hasNext()) {
            String arg = it.next();

            if ("-s".equals(arg)) {
                if (it.hasNext()) {
                    arg = it.next();

                    if (!arg.contains(":")) {
                        System.err.println("Invalid argument after '-s'!");
                        System.exit(0);
                    }

                    String[] sizeParts = arg.split("\\:");
                    if (sizeParts.length != 2) {
                        System.err.println("Invalid argument after '-s'!");
                        System.exit(0);
                    }

                    int w = 800, h = 600;
                    try {
                        w = Integer.parseInt(sizeParts[0]);
                        h = Integer.parseInt(sizeParts[1]);
                    } catch (NumberFormatException ignored) {
                        System.err.println("Invalid argument after '-s'!");
                        System.exit(0);
                    }

                    if (w < 200 || h < 150) {
                        System.err.println("Minimum window size is 200:150!");
                        System.exit(0);
                    }

                    System.out.println(String.format("Creating display of custom size %d:%d!", w, h));
                    Display.createDisplay(w, h);
                } else {
                    System.err.println("Unexpected EOF after '-s'!");
                    System.exit(0);
                }
            }
        }

        Display display = Display.getDisplay();
        if (display == null) {
            System.err.println("Unexpected display error!");
            System.exit(0);
        }

        // Starts the game
        display.start();
    }

}
