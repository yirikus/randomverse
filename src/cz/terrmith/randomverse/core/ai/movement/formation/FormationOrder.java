package cz.terrmith.randomverse.core.ai.movement.formation;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 27.2.14
 * Time: 20:43
 * To change this template use File | Settings | File Templates.
 */
public class FormationOrder {

    private Integer[] orders;
    private Long waitTime;

    public FormationOrder(Integer[] orders) {
        this(orders, null);
    }

    public FormationOrder(Integer[] orders, Long waitTime) {
        this.orders = orders;
        this.waitTime = waitTime;
    }

    /**
     * Creates order by repeating given sequence
     * @param sequence sequnce to repeat
     * @param size size of result sequence
     * @param waitTime
     * @return
     */
    public static FormationOrder repeatedSequence(Integer[] sequence, int size, Long waitTime) {
        Integer[] orders = new Integer[size];
        for (int i = 0; i < size; i++) {
            orders[i] = sequence[i%sequence.length];
        }
        return new FormationOrder(orders, waitTime);
    }

    /**
     * Warning: not defensive
     * @return
     */
    public Integer[] getOrders() {
        return orders;
    }

    public Long getWaitTime() {
        return waitTime;
    }
}
