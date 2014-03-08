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
        if (size < 0) {
            throw new IllegalArgumentException("Size must not be negative");
        }
        if (sequence == null || sequence.length < 1) {
            throw new IllegalArgumentException("Sequnce must not be null nor empty");
        }

        Integer[] orders = new Integer[size];
        for (int i = 0; i < size; i++) {
            orders[i] = sequence[i%sequence.length];
        }
        return new FormationOrder(orders, waitTime);
    }

    /**
     * Divides sequence into groups of same numbers like [1,1,2,2,3,3]
     * @param groupSize how many numbers in the sequence will be the same
     * @param size size of result sequence
     * @param waitTime
     * @return
     */
    public static FormationOrder groupSequence(int groupSize, int size, Long waitTime) {
        if (groupSize == 0) {
            throw new IllegalArgumentException("Group size must not be 0");
        }

        if (size < 0) {
            throw new IllegalArgumentException("Size must not be negative");
        }
        Integer[] orders = new Integer[size];
        int groupMembers = 0;
        int groupNo = 1;
        for (int i = 0; i < size; i++) {
            if(groupMembers < groupSize) {
                //group not finished increment group members
                groupMembers++;
            } else {
                //group finished, reset and increment group number
                groupMembers = 1;
                groupNo++;
            }
            orders[i] = groupNo;
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
