package kvv.example.name.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    public static int getRandomInt(int min, int max) {
        var threadLocalRandom = ThreadLocalRandom.current();
        if (min > max) {
            throw new IllegalArgumentException("min должен быть <= max");
        }
        return threadLocalRandom.nextInt(min, max + 1);
    }
}
