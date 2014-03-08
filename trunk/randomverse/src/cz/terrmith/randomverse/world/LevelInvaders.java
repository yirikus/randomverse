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
import cz.terrmith.randomverse.core.sprite.factory.SpriteFactory;
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
    public static final String ACTIVATION_KEY = "activationKey";
    private final ArtificialIntelligence ai;
    private final SpaceBackground background;

    public LevelInvaders(final SpriteCollection spriteCollection, ArtificialIntelligence ai, Map<EventResult, NavigableTextCallback> callbacks) {
        super(spriteCollection, 4, waveCount());
        this.ai = ai;

        this.background = new SpaceBackground(8);

        setWorldEvent(randomEvent(callbacks));
    }

    private static long waveCount() {
        return 1 + random.nextInt(2);
    }

    private WorldEvent randomEvent(Map<EventResult, NavigableTextCallback > callbacks) {
        if (getWavesToDefeat() == 2) {
            switch (random.nextInt(5)) {
                default: return LevelInvadersEvents.invadersWithUfos(callbacks);
            }
        } else {
            switch (random.nextInt(5)) {
                default: return LevelInvadersEvents.invaders(callbacks);
            }
        }

    }

    @Override
    protected void updateWorld() {
        if (getUpdateCount() == 1) {
            final int columns = 2 + random.nextInt(3);
            final int rows = 2 + random.nextInt(3);
            boxLeftRightFormation(rows, columns, ACTIVATION_KEY, null, false);
            if (getWavesToDefeat() == 1) {
                waitForInactivation(ACTIVATION_KEY);
            }
        }

        if (getUpdateCount() == 2 && getWavesToDefeat() == 2) {
            waitForInactivation(ACTIVATION_KEY);
            boxLeftRightFormation(1, 1 + random.nextInt(9), ACTIVATION_KEY, SimpleEnemy.EnemyType.INVADER_3, true);
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


    private void boxLeftRightFormation(final int rows, final int columns, String name, final SimpleEnemy.EnemyType enemyType, boolean allTheWay) {
        final int formationSize = rows * columns;
        List<Sprite> enemies = createSprites(formationSize, new SpriteFactory() {
            @Override
            public Sprite newSprite(int x, int y) {
                if (enemyType != null) {
                    return new SimpleEnemy(x, y, enemyType, getSpriteCollection());
                }
                if (rows > 3 || columns > 5) {
                    return new SimpleEnemy(x, y, SimpleEnemy.EnemyType.INVADER_1, getSpriteCollection());
                } else {
                    return new SimpleEnemy(x, y, SimpleEnemy.EnemyType.SINGLE, getSpriteCollection());
                }
            }
        });

        int dx = allTheWay ? GameWindow.SCREEN_W : 0;

        final int enemySize = enemies.get(0).getWidth();
        final int border = enemySize * 2;
        int colMargin = Formation.marginToFitSize(columns, GameWindow.SCREEN_W - border * 2);
        int rowMargin = enemySize * 2;

        List<Formation> formations = new ArrayList<Formation>();
        formations.add(Formation.boxFormation(rows, columns, new Position(border - dx,0), colMargin, rowMargin));
        formations.add(Formation.boxFormation(rows, columns, new Position(border - dx, enemySize + formations.get(0).getHeight()), colMargin, rowMargin));
        formations.add(Formation.boxFormation(rows, columns, new Position(border * 2 + dx, enemySize +  formations.get(0).getHeight()), colMargin, rowMargin));
        formations.add(Formation.boxFormation(rows, columns, new Position(0 - dx, enemySize + formations.get(0).getHeight()), colMargin, rowMargin));

        FormationOrder e = randomOrder(columns, formationSize);

        List<FormationOrder> orders = new ArrayList<FormationOrder>();
        for (int i = 0; i < formations.size(); i++) {
            orders.add(e);
        }

        FormationMovement formationMovement = new FormationMovement(enemies, formations, orders, null, 2);
        formationMovement.registerObserver(this, name);
        ai.registerSpriteContainer(formationMovement);
    }

    private FormationOrder randomOrder(int columns, int formationSize) {
        switch (random.nextInt(3)) {
            case 0:
                return FormationOrder.groupSequence(columns, formationSize, null);
            default:
                return FormationOrder.repeatedSequence(new Integer[]{1}, formationSize, null);

        }
    }
}
