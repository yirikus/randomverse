package cz.terrmith.randomverse.core.ai.movement.pattern;

import cz.terrmith.randomverse.core.geometry.Position;

/**
 * Movement behaves like sine function
 *
 * @author jiri.kus
 */
public class WaveMovement implements MovementPattern {

	private final int amplitude;
	private final int frequency;

	/**
	 * @param amplitude x range, greater value will result in higher wave, negative value reverses direction
	 *                  (usually from left corner, with negative from right corner)
	 * @param frequency length of wave, lower the number, the more wave will be tracked
	 */
	public WaveMovement(int amplitude, int frequency) {
		this.amplitude = amplitude;
		this.frequency = frequency;
	}

	@Override
	public Position nextPosition(Position position, int speed) {
		double prevSinx = amplitude * Math.sin(position.getY() / frequency);
		double sinx = amplitude * Math.sin((position.getY() + speed)/ frequency);

		return new Position(position.getX() - prevSinx + sinx, position.getY() + speed);
	}
}
