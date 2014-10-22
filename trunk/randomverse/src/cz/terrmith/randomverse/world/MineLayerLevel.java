package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.GameWindow;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.ai.movement.formation.Formation;
import cz.terrmith.randomverse.core.ai.movement.formation.FormationMovement;
import cz.terrmith.randomverse.core.ai.movement.formation.FormationOrder;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.factory.SpriteFactory;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.sprite.enemy.SimpleEnemy;

import java.util.ArrayList;
import java.util.List;

/**
 * Testing level
 *
 * todo refactor formation movement creation - abstract method
 */
public class MineLayerLevel extends World {
    public static final String ACTIVATION_KEY = "activationKey";
    private final ArtificialIntelligence ai;

    //TODO obtain this from world event
    public enum Variant {CLASSIC, AQUABELLE}
    private Variant variant = Variant.CLASSIC;

    public MineLayerLevel(final SpriteCollection spriteCollection, ArtificialIntelligence ai, long period, long wavesToDefeat) {
        super(spriteCollection, period, wavesToDefeat);
        this.ai = ai;
    }

    @Override
    protected void updateWorld() {
        switch (variant) {
            case CLASSIC:
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
                break;

            case AQUABELLE:
                if (getUpdateCount() == 1) {
                    aquabelleFormation(16, 200, ACTIVATION_KEY, SimpleEnemy.EnemyType.INVADER_1, true);
                } else if (getUpdateCount() == 2) {
                    aquabelleFormation(8, 130, ACTIVATION_KEY, SimpleEnemy.EnemyType.INVADER_2, false);
                } else if (getUpdateCount() == 3) {
                    aquabelleFormation(4, 70, ACTIVATION_KEY, SimpleEnemy.EnemyType.INVADER_3, true);
                }
                break;
        }
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
        formations.add(Formation.rectangle(rows, columns, new Position(border - dx, 0), colMargin, rowMargin));
        formations.add(Formation.rectangle(rows, columns, new Position(border - dx, enemySize + formations.get(0).getHeight()), colMargin, rowMargin));
        formations.add(Formation.rectangle(rows, columns, new Position(border * 2 + dx, enemySize + formations.get(0).getHeight()), colMargin, rowMargin));
        formations.add(Formation.rectangle(rows, columns, new Position(0 - dx, enemySize + formations.get(0).getHeight()), colMargin, rowMargin));

        FormationOrder e = randomOrder(columns, formationSize);

        List<FormationOrder> orders = new ArrayList<FormationOrder>();
        for (int i = 0; i < formations.size(); i++) {
            orders.add(e);
        }

        FormationMovement formationMovement = new FormationMovement(enemies, formations, orders, null, 2);
        formationMovement.registerObserver(this, name);
        ai.registerSpriteContainer(formationMovement);
    }

    private void aquabelleFormation(int formationSize, int radius, String name, final SimpleEnemy.EnemyType enemyType, boolean clockwise) {
        List<Sprite> enemies = createSprites(formationSize, new SpriteFactory() {
            @Override
            public Sprite newSprite(int x, int y) {
                return new SimpleEnemy(x, y, enemyType, getSpriteCollection());
            }
        });

        List<Formation> formations = new ArrayList<Formation>();
        formations.add(Formation.circle(new Position(random.nextInt(GameWindow.SCREEN_W), 0), radius, formationSize, formationSize / 2));
        Position center = new Position(400, 250);
        if (clockwise) {
            for (int i = 0; i < formationSize; i++) {
                formations.add(Formation.circle(center, radius, formationSize, i));
            }
        } else {
            for (int i = formationSize; i > 0; i--) {
                formations.add(Formation.circle(center, radius, formationSize, i));
            }
        }

        FormationOrder e = FormationOrder.repeatedSequence(new Integer[]{1}, formationSize, null);

        List<FormationOrder> orders = new ArrayList<FormationOrder>();
        for (Formation formation : formations) {
            orders.add(e);
        }

        FormationMovement formationMovement = new FormationMovement(enemies, formations, orders, null, 1);
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
