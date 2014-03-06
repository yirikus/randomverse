package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.GameWindow;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.ai.movement.formation.Formation;
import cz.terrmith.randomverse.core.ai.movement.formation.FormationMovement;
import cz.terrmith.randomverse.core.ai.movement.formation.FormationOrder;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.core.world.WorldEvent;
import cz.terrmith.randomverse.graphics.SpaceBackground;
import cz.terrmith.randomverse.sprite.enemy.SimpleEnemy;
import cz.terrmith.randomverse.world.events.EventResult;
import cz.terrmith.randomverse.world.events.LevelInvadersEvents;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Testing level
 *
 * todo refactor formation movement creation - abstract method
 */
public class LevelInvaders extends World {
    private final ArtificialIntelligence ai;
    private final SpaceBackground background;

    public LevelInvaders(final SpriteCollection spriteCollection, ArtificialIntelligence ai, Map<EventResult, NavigableTextCallback> callbacks) {
        super(spriteCollection, 7, 1);
        this.ai = ai;

        this.background = new SpaceBackground(8);

        setWorldEvent(randomEvent(callbacks));
    }

    private WorldEvent randomEvent(Map<EventResult, NavigableTextCallback > callbacks) {
        switch (random.nextInt(5)) {
            default: return LevelInvadersEvents.invaders(callbacks);
        }

    }

    @Override
    protected void createSprites() {
        if (getUpdateCount() == 1) {
            boxLeftRightFormation("activationKey");
        }
    }

    @Override
    public void drawMapIcon(Graphics g, Position position, int size) {

        //background
        g.setColor(Color.BLACK);
        g.fillRect((int) position.getX(),
                (int) position.getY(),
                size, size);
        //stars
        background.drawBackground(g, position, size);

    }

    private void boxLeftRightFormation(String name) {
        waitForInactivation(name);
        int enemySize = SimpleEnemy.SIZE;
        int border = enemySize * 2;
        int columns = 3;
        int rows = 4;
        int formationSize = rows * columns;
        int colMargin = Formation.marginToFitSize(rows, GameWindow.SCREEN_W - border * 2);
        int rowMargin = enemySize * 2;
        Formation formation1 = Formation.boxFormation(rows, columns, new Position(border,0), colMargin, rowMargin);
        Formation formation2 = Formation.boxFormation(rows, columns, new Position(border, formation1.getHeight()), colMargin, rowMargin);
        Formation formation3 = Formation.boxFormation(rows, columns, new Position(border * 2, formation1.getHeight()), colMargin, rowMargin);
        Formation formation4 = Formation.boxFormation(rows, columns, new Position(0, formation1.getHeight()), colMargin, rowMargin);

        List<Formation> formations = new ArrayList<Formation>();
        formations.add(formation1);
        formations.add(formation2);
        formations.add(formation3);
        formations.add(formation4);

        List<Sprite> enemies = createSprites(formationSize);

        List<FormationOrder> orders = new ArrayList<FormationOrder>();
        orders.add(FormationOrder.repeatedSequence(new Integer[]{1}, formationSize, null));
        orders.add(FormationOrder.repeatedSequence(new Integer[]{1}, formationSize, null));
        orders.add(FormationOrder.repeatedSequence(new Integer[]{1}, formationSize, null));
        orders.add(FormationOrder.repeatedSequence(new Integer[]{1}, formationSize, null));

        FormationMovement formationMovement = new FormationMovement(enemies, formations, orders, null, 2);
        formationMovement.registerObserver(this, name);
        ai.registerSpriteContainer(formationMovement);
    }

}
