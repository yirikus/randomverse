package cz.terrmith.randomverse.inventory;

import cz.terrmith.randomverse.core.geometry.GridLocation;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 9.3.14
 * Time: 17:15
 * To change this template use File | Settings | File Templates.
 */
public class Mission {
    private String name;
    private String description;
    private GridLocation targetLocation;
    private int rewardMoney;
    private boolean completed;

    public Mission(String name, String description, GridLocation targetLocation, int rewardMoney) {
        this.name = name;
        this.description = description;
        this.targetLocation = targetLocation;
        this.rewardMoney = rewardMoney;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public GridLocation getTargetLocation() {
        return targetLocation;
    }

    public int getRewardMoney() {
        return rewardMoney;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
