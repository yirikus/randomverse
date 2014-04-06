package cz.terrmith.randomverse.world.events;

import cz.terrmith.randomverse.core.dialog.Dialog;
import cz.terrmith.randomverse.core.dialog.DynamicText;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.input.Command;
import cz.terrmith.randomverse.core.util.StringUtils;
import cz.terrmith.randomverse.graphics.SpaceBackground;

import java.awt.*;
import java.util.List;

/**
 * WorldEvent of the world
 *
 * User: TERRMITh
 * Date: 4.3.14
 * Time: 11:40
 */
public class WorldEvent {
    private final DynamicText dynamicText;
    private Dialog dialog;
    private List<ScannerInfo> scannerInfo;
    private final String variation;
    private final SpaceBackground background = new SpaceBackground(10);
//    public enum Progress {IN_PROGRESS, CONCLUDED}

//    private Progress progress = Progress.IN_PROGRESS;

    /**
     *
     * @param dynamicText text that will appear in dialog
     * @param scannerInfo text that will appear on scanner
     * @param variation //todo remove
     */
    public WorldEvent(DynamicText dynamicText, List<ScannerInfo> scannerInfo, String variation) {
        this.dynamicText = dynamicText;
        this.scannerInfo = scannerInfo;
        this.variation = variation;
    }

    public Dialog getDialog() {
        return dialog;
    }

    /**
     * Handle user input and stuff
     * @param command
     */
    public void updateEvent(Command command) {
        if (this.dialog == null) {
            this.dialog = new cz.terrmith.randomverse.core.dialog.Dialog(dynamicText, 200, 200, 400, 200, null);
        }
        boolean callbacked = dialog.update(command);
        if (callbacked) {
            dialog.close();
            dialog = null;
        }
    }

//    public Progress getProgress() {
//        return this.progress;
//    }
//
//    public void setProgress(Progress progress) {
//        this.progress = progress;
//    }

    public void drawEvent(Graphics g) {
        if (dialog != null) {
            dialog.drawDialog(g);
        }
    }

    public String getScannerInfo(int scannerStrength) {
        int highestStrengthFound = -100;
        String message = ScannerInfo.DEFAULT_MESSAGE;
        for (ScannerInfo info : this.scannerInfo) {
            if (info.getScannerStrength() <= scannerStrength
                && info.getScannerStrength() > highestStrengthFound) {
                highestStrengthFound = info.getScannerStrength();
                message = info.getMessage();
            }
        }

        return message;
    }

    public String getVariation() {
        return variation;
    }


    //todo scannerStrength in draw method is probably ugly
    public void drawScannerInfo(Graphics g, Position position, int scannerStrenght) {
        String scannerInfo = getScannerInfo(scannerStrenght);
        StringUtils.drawString(g, "SCANNER[" + scannerStrenght + "]: " + scannerInfo, (int) position.getX(), (int) position.getY(), 300);
    }

    public void drawMapIcon(Graphics g, Position position, int size) {

        //background
        g.setColor(Color.BLACK);
        g.fillRect((int) position.getX(),
                (int) position.getY(),
                size, size);
        //stars
        background.drawBackground(g, position, size);
    }
}
