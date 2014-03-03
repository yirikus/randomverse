package cz.terrmith.randomverse.core.util;

import java.util.Random;

/**
 * Random sequence generator
 */
public class RandomSequence {
    private static Random rand = new Random();

    /**
     * Generates sequence of random numbers of given size, numbers are from range [0, maxRange)
     * TODO optimization for maxRange == size
     * @param maxRange range exclusive
     * @param size size of result
     * @return random int sequence (unordered)
     */
    public static int[] randomSequence(int maxRange, int size){
        if (maxRange > 256) {
            throw new IllegalArgumentException("maximum range is 256");
        }
        boolean[] picks = new boolean[maxRange];
        int[] sequence = new int[size];
        for (int i = 0; i < sequence.length;) {
            int newNumber = rand.nextInt(maxRange);
            if (!picks[newNumber]) {
                picks[newNumber] = true;
                sequence[i] = newNumber;
                i++;
            }
        }
        return sequence;
    }
}
