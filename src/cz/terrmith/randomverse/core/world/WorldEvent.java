package cz.terrmith.randomverse.core.world;

import cz.terrmith.randomverse.core.dialog.Dialog;
import cz.terrmith.randomverse.core.dialog.DynamicText;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.input.Command;

import java.awt.Graphics;

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
    public enum Progress {IN_PROGRESS, CONCLUDED}

    public static class WorldEventCallback implements NavigableTextCallback {

        @Override
        public void onSelection() {

        }
    }

    private Progress progress = Progress.IN_PROGRESS;

    public WorldEvent(DynamicText dynamicText) {
        this.dynamicText = dynamicText;
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
        dialog.update(command);
    }

    public Progress getProgress() {
        return this.progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public void drawEvent(Graphics g) {
        if (dialog != null) {
            dialog.drawDialog(g);
        }
    }
}
