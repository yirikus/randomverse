package cz.terrmith.randomverse.util;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 3.10.13
 * Time: 1:22
 * To change this template use File | Settings | File Templates.
 */
public class ConverterUtil {

    public static long nanosToMilis(long ns){
        return ns / 1000000L;
    }
    public static long milisToNanos(long ms){
        return ms * 1000000L;
    }
}
