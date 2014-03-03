package cz.terrmith.randomverse.game.states;

import cz.terrmith.randomverse.Debug;
import cz.terrmith.randomverse.GameWindow;
import cz.terrmith.randomverse.Randomverse;
import cz.terrmith.randomverse.core.AnimationEngine;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.state.State;
import cz.terrmith.randomverse.game.StateName;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 3.3.14
 * Time: 9:01
 * To change this template use File | Settings | File Templates.
 */
public class CutSceneState implements State {
    public static final int PROGRESS_MAX = AnimationEngine.DEFAULT_FPS * 2;
    private final Randomverse stateMachine;
    private List<String> textToDisplay = new ArrayList<String>();
    private int currentIndex;
    private int progress = 0;
    private static final int ALPHA_DELTA = 255 / PROGRESS_MAX;

    public CutSceneState(Randomverse stateMachine) {
        this.stateMachine = stateMachine;
        textToDisplay.add("Get out of that ship!");
        textToDisplay.add("Why?");
        textToDisplay.add("Or.. or.. we will shoot you!");
        textToDisplay.add("You are just lab rats.");
        textToDisplay.add("This is not a joke!");
        textToDisplay.add("Since when science vessels have guns?");
        textToDisplay.add("G-G-Get him!!!");
    }

    @Override
    public String getName() {
        return StateName.CUT_SCENE.name();
    }

    @Override
    public void update() {

        if (!Debug.PLAY_INTRO || stateMachine.getCommand().isAnyKey()) {
            stateMachine.setCurrentState(StateName.GAME.name());
            return;
        }
        //TODO add timer and press any key
        if (progress > PROGRESS_MAX) {
            currentIndex++;
            progress = 0;
        } else {
            progress++;
        }

        if (currentIndex >= textToDisplay.size()) {
            stateMachine.setCurrentState(StateName.GAME.name());
            return;
        }
    }

    @Override
    public void draw(Graphics2D g2, ImageLoader iml) {

        stateMachine.clearScreen(g2, Color.BLACK);

        drawString(g2, currentIndex - 1, true);
        drawString(g2, currentIndex, false);

    }

    private void drawString(Graphics2D g2, int index, boolean fadeOut) {
        if (index < 0) {
            return;
        }

        Font font = new Font("system", Font.BOLD, 40);
        g2.setFont(font);
        FontMetrics metrics = g2.getFontMetrics();

        String text = textToDisplay.get(index);

        int x = GameWindow.SCREEN_W / 2 - (metrics.stringWidth(text) / 2);
        int y =  GameWindow.SCREEN_H / 2 + (index % 2) * 100 - 100;

        if (fadeOut) {
            g2.setColor(new Color(255, 255, 255, 255 - progress * ALPHA_DELTA));
        } else {
            g2.setColor(new Color(255, 255, 255, progress * ALPHA_DELTA));
        }
        g2.drawString(text, x, y);
    }

    @Override
    public void activate(State prevState) {
    }

    @Override
    public void deactivate(State nextState) {
    }
}
