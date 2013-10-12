package cz.terrmith.randomverse.core.ai.attack;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 11.10.13
 * Time: 22:31
 * To change this template use File | Settings | File Templates.
 */
public class RandomAttackPattern implements AttackPattern {


    private double attackProbability;
    private int maxWaitTime;
    private int timeWaited;
    private Random random;

    /**
     * Constructor
     *
     * @param maxWaitTime
     */
    public RandomAttackPattern(int maxWaitTime){
        if (attackProbability > 1 || attackProbability  < 0) {
            throw new IllegalArgumentException("attack probability must be in [0,1], but was: " + attackProbability);
        }

        this.attackProbability = attackProbability;
        this.maxWaitTime = maxWaitTime;
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public boolean shouldAttack() {
        if (random.nextBoolean() && timeWaited >= maxWaitTime) {
            timeWaited = 0;
            return true;
        }
        timeWaited++;
        return false;
    }
}
