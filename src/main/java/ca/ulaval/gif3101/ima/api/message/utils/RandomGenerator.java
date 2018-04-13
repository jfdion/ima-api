package ca.ulaval.gif3101.ima.api.message.utils;

import java.util.Random;

public class RandomGenerator {

    public static final int SEED = 2017;

    public Random random() {
        Random random = new Random();
        random.setSeed(SEED);
        return random;
    }
}
