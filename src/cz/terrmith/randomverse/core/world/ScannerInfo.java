package cz.terrmith.randomverse.core.world;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 5.3.14
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
public class ScannerInfo {

    public static final String DEFAULT_MESSAGE = "<no signals>";
    private int scannerStrength;
    private String message;

    public ScannerInfo(int scannerStrength, String message) {
        this.scannerStrength = scannerStrength;
        this.message = message;
    }

    public int getScannerStrength() {
        return scannerStrength;
    }


    public String getMessage() {
        return message;
    }

}
