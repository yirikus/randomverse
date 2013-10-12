package cz.terrmith.randomverse.util;

/**
 * Static utility class providing methods for conversion
 */
public class ConverterUtil {

    /**
     * Not intended for instantiation
     */
    private ConverterUtil(){

    }

    public static long nanosToMilis(long ns){
        return ns / 1000000L;
    }
    public static long milisToNanos(long ms){
        return ms * 1000000L;
    }
}
