package cz.terrmith.randomverse.ladder;

/**
 * DTO for storing ladder entries
 *
 * User: TERRMITh
 * Date: 9.3.14
 * Time: 14:50
 */
public class LadderEntry {
    private String name;
    private int money;

    public LadderEntry(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
