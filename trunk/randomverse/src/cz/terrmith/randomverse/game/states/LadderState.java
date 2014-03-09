package cz.terrmith.randomverse.game.states;

import cz.terrmith.randomverse.GameWindow;
import cz.terrmith.randomverse.Randomverse;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.state.State;
import cz.terrmith.randomverse.game.StateName;
import cz.terrmith.randomverse.ladder.LadderEntry;
import cz.terrmith.randomverse.ladder.LadderUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 9.3.14
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class LadderState implements State {
    private final Randomverse stateMachine;
    private List<LadderEntry> ladder;

    public LadderState(Randomverse stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
    public String getName() {
        return StateName.LADDER.name();
    }

    @Override
    public void update() {
        if (stateMachine.getCommand().isAnyKey()) {
            System.out.println("änykey bitch");
            stateMachine.setCurrentState(StateName.MAIN_MENU.name());
            stateMachine.getCommand().clear();
        }
    }

    @Override
    public void draw(Graphics2D g2, ImageLoader iml) {
        stateMachine.clearScreen(g2, new Color(0,0,0,180));
        if (ladder != null) {
            Font font = new Font("system", Font.BOLD, 25);
            g2.setFont(font);
            g2.setColor(Color.GREEN);
            int i = 0;
            for (LadderEntry entry : ladder) {
                g2.drawString(entry.getName().trim(), 100, 100 + i * 25);
                g2.drawString(String.valueOf(entry.getMoney()), GameWindow.SCREEN_W - 200, 100 + i * 25);
                System.out.println(entry.getName() + ": " + entry.getMoney());
                i++;
            }
        }
    }

    @Override
    public void activate(State prevState) {
        System.out.println("activating ladder");
        stateMachine.getCommand().clear();
        ladder = LadderUtil.readFromFile();
    }

    @Override
    public void deactivate(State nextState) {
    }
}
