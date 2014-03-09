package cz.terrmith.randomverse.core.dialog;

import cz.terrmith.randomverse.core.input.Command;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Modal window with a callback action
 *
 * @author jiri.kus
 */
public class Dialog{

    private final DynamicText dynamicText;

	private int posX;
	private int posY;
	private int width;
	private int height;
	private DialogCallback callback;
	private long timer;
    private boolean input;
    private String inputText = "";

    /**
	 *
	 * @param text message to writeToFile into dialog
	 * @param posX x
	 * @param posY y
	 * @param width width
	 * @param height height
	 * @param callback callback that will be called at certain time like on close operation (can be null)
	 */
	public Dialog(String text, int posX, int posY, int width, int height, DialogCallback callback) {
		this(new DynamicText(text), posX, posY, width, height, callback);
	}

    /**
     *
     * @param text message to writeToFile into dialog
     * @param posX x
     * @param posY y
     * @param width width
     * @param height height
     * @param callback callback that will be called at certain time like on close operation (can be null)
     * @param menu menu with options to choose from
     */
    public Dialog(DynamicText text, int posX, int posY, int width, int height, DialogCallback callback) {
        this.dynamicText = text;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.callback = callback;
        timer = System.currentTimeMillis();
    }

    public static Dialog inputDialog(String text, int posX, int posY, int width, int height, DialogCallback callback) {
        Dialog dialog = new Dialog(new DynamicText(text), posX, posY, width, height, callback);
        dialog.input = true;

        return dialog;
    }

	/**
	 * Closes dialog window
	 * @return true if dialog was closed, false otherwise
	 */
	public boolean close() {
		// prevents accidental close
		if (System.currentTimeMillis() - timer < 500) {
			return false;
		}
		if (callback != null) {
			callback.onClose(this);
		}
		return true;
	}

    public boolean update(Command command){
        if (Command.State.PRESSED_RELEASED.equals(command.getUp())) {
            command.setUp(false);
            dynamicText.prevOption();
        } else if (Command.State.PRESSED_RELEASED.equals(command.getDown())) {
            command.setDown(false);
            dynamicText.nextOption();
        } else if (Command.State.PRESSED_RELEASED.equals(command.getLeft())) {
            command.setLeft(false);
            dynamicText.prevOption();
        } else if (Command.State.PRESSED_RELEASED.equals(command.getRight())) {
            command.setRight(false);
            dynamicText.nextOption();
        } else if (Command.State.PRESSED_RELEASED.equals(command.getAction1())) {
            command.setAction1(false);
            return dynamicText.navigate();
        }
        if (input) {
            String typed = command.getKeyTyped();
            inputText += typed;
        }

        return false;
    }

	public void drawDialog(Graphics g) {
        //draw box
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect((int) getPosX(), (int) getPosY(), getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.drawRect((int) getPosX(), (int) getPosY(), getWidth(), getHeight());

        //draw string
		Font font = new Font("system", Font.BOLD, 15);
        g.setFont(font);

        int y = dynamicText.draw(g, getPosX(), getPosY(), getWidth());

        //draw input
        if (input) {
            g.setColor(new Color(58, 173, 226));
            g.drawString(getInputText(), getPosX() + 10, y + 15);
        }
	}


	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getPosY() {
		return posY;
	}

	public int getPosX() {
		return posX;
	}

    public boolean isInput() {
        return input;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }
}
