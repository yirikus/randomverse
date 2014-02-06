package cz.terrmith.randomverse.core;

import cz.terrmith.randomverse.core.dialog.Dialog;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.state.StateMachine;

import java.awt.Graphics2D;

/**
 * Game engine interface
 */
public abstract class GameEngine extends StateMachine {

	private Dialog dialog;
	private boolean paused;

	/**
     * Updates games status
     */
    public abstract void update();

	/**
	 * Does everything that needs to be done before pausing the game
	 */
	public void pause() {
		paused = true;
	}

	/**
	 * Does everything that needs to be done before resuming the game
	 */
	public void unpause() {
		paused = false;
	}

	public boolean isPaused() {
		return paused;
	}

	/**
	 * Returns sprite collection
	 * @return
	 */
	public abstract SpriteCollection getSpriteCollection();

	/**
	 * Draws head up display (shows various game information to the user, like health, ammo, etc.)
	 * @param g2
	 * @param iml
	 */
	public abstract void drawHUD(Graphics2D g2, ImageLoader iml);

	/**
	 *
	 * @return
	 */
	public Dialog getDialog(){
		return this.dialog;
	}

	/**
	 * closes old dialog window and shows the one that was given
	 * Showing a dialog stops game update
	 *
	 * @param dialog dialog to show
	 */
	public void showDialog(Dialog dialog){
		pause();
		if (this.dialog != null) {
			this.dialog.close();
		}
		this.dialog = dialog;
	}

	/**
	 * Closes dialog window
	 */
	public boolean closeDialog() {
		boolean closed = this.dialog.close();
		if (closed) {
			this.dialog = null;
		}
		return closed;
	}

	/**
	 * If game is paused, this method is called to check if the game should be unpaused
	 */
	public abstract void waitForUnpause();

    public abstract void resetGame();
}
