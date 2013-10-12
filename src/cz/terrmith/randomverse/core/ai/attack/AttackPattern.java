package cz.terrmith.randomverse.core.ai.attack;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 11.10.13
 * Time: 22:26
 * To change this template use File | Settings | File Templates.
 */
public interface AttackPattern {

    /**
     * Returns true if NPC should attack
     * @return
     */
     boolean shouldAttack();
}
