package cz.terrmith.randomverse.core.dialog;

import cz.terrmith.randomverse.core.image.ImageLoader;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;

/**
 * Modal window with a callback action
 *
 * @author jiri.kus
 */
public class Dialog{

	private String text;
	private int posX;
	private int posY;
	private int width;
	private int height;
	private DialogCallback callback;
	private long timer;

	/**
	 *
	 * @param text message to write into dialog
	 * @param posX x
	 * @param posY y
	 * @param width width
	 * @param height height
	 * @param callback callback that will be called at certain time like on close operation (can be null)
	 */
	public Dialog(String text, int posX, int posY, int width, int height, DialogCallback callback) {
		System.out.println("new dialog: " + text);
		this.text = text;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.callback = callback;
		timer = System.currentTimeMillis();
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
			callback.onClose();
		}
		return true;
	}


	public void drawDialog(Graphics g) {

		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect((int) getPosX(), (int) getPosY(), getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.drawRect((int) getPosX(), (int) getPosY(), getWidth(), getHeight());

		Font font = new Font("system", Font.BOLD, 40);
		g.setFont(font);
		FontMetrics metrics = g.getFontMetrics();
		int dx = (getWidth() - metrics.stringWidth(text)) / 2;
		g.setColor(Color.WHITE);
		g.drawString(getText(),
		             getPosX() + dx,
		             getPosY() + getHeight() / 2);
	}


	public String getText() {
		return text;
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
}
