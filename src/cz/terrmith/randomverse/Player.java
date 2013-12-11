package cz.terrmith.randomverse;

import cz.terrmith.randomverse.core.input.Command;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.abilitiy.Damage;
import cz.terrmith.randomverse.core.sprite.abilitiy.Loot;
import cz.terrmith.randomverse.inventory.ShipPartFactory;
import cz.terrmith.randomverse.loot.LootType;
import cz.terrmith.randomverse.sprite.Ship;

/**
 * Wraps player sprite
 * Handles input to control player sprite
 * Holds stats and loot (money, items, ...)
 *
 *
 * @author jiri.kus
 */
public class Player {

	private final SpriteCollection spriteCollection;
	private Ship playerSprite;
    private Command command;
	public static final int STEP = 6;
	private int money = 0;

	public Player(Command command, SpriteCollection sc) {
		this.command = command;
		this.spriteCollection = sc;
		this.playerSprite = createPlayerSprite();
	}

	/**
	 * Processes user input and updates player sprite
	 */
	public void update() {
		double dx = 0;
		double dy = 0;
		double step = getSprite().getSpeed();
		if (Command.State.RELEASED_PRESSED.equals(command.getUp())
		  || Command.State.PRESSED.equals(command.getUp())) {
			dy -= step;
		}
		if (Command.State.RELEASED_PRESSED.equals(command.getDown())
		  || Command.State.PRESSED.equals(command.getDown())) {
			dy += step;
		}
		if (Command.State.RELEASED_PRESSED.equals(command.getLeft())
		  || Command.State.PRESSED.equals(command.getLeft())) {
			dx -= step;
		}
		if (Command.State.RELEASED_PRESSED.equals(command.getRight())
		  || Command.State.PRESSED.equals(command.getRight())) {
			dx += step;
		}

		if (Command.State.RELEASED_PRESSED.equals(command.getAction1())
		  || Command.State.PRESSED.equals(command.getAction1())) {
			playerSprite.attack();
		}

		playerSprite.setStep(dx, dy);
		playerSprite.updateSprite();

	}

	/**
	 * Creates new player sprite
	 */
	public Ship createPlayerSprite() {
		Ship ship = new Ship(300, 300);
		ShipPartFactory factory = new ShipPartFactory(spriteCollection, Damage.DamageType.NPC);

		ship.addTile(-1, 0, factory.create(ShipPartFactory.Item.GUN_1.ordinal()));
		SimpleSprite gun2 = factory.create(ShipPartFactory.Item.GUN_1.ordinal());
		gun2.flipHorizontal();
		ship.addTile(1, 0, gun2);

		ship.addTile(0, -1, factory.create(ShipPartFactory.Item.COCKPIT_1.ordinal()));
		ship.addTile(0, 0, factory.create(ShipPartFactory.Item.MID_1.ordinal()));
		ship.addTile(0, 1, factory.create(ShipPartFactory.Item.ENGINE_1.ordinal()));

		return ship;
	}

	/**
	 * Resets all player assets (sprite, loot, stats...)
	 */
	public void reset() {
		this.playerSprite = createPlayerSprite();
		this.money = 0;
	}

	public Ship getSprite() {
		return playerSprite;
	}

	public int getCurrentHealth() {
		return playerSprite.getCurrentHealth();
	}

	public int getTotalHealth() {
		return playerSprite.getTotalHealth();
	}

	public void addLoot(Loot loot) {
		if (loot.getType().equals(LootType.MONEY.name())) {
			money += loot.getAmount();
		}
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
}
