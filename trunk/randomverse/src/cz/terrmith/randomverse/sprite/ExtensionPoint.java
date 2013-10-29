package cz.terrmith.randomverse.sprite;

import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * @author jiri.kus
 */
public enum ExtensionPoint {
	TOP,
	LEFT,
	RIGHT,
	BOTTOM;

	public static ExtensionPoint flip(ExtensionPoint ex) {
		if (ex == null) {
			return null;
		}

		switch(ex) {
			case TOP:
				return BOTTOM;
			case BOTTOM:
				return TOP;
			case LEFT:
				return RIGHT;
			case RIGHT:
				return LEFT;
			default:
				return null;
		}
	}
}
