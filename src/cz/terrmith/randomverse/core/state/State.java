package cz.terrmith.randomverse.core.state;

import cz.terrmith.randomverse.core.image.ImageLoader;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dell
 * Date: 12/21/13
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface State {

    String getName();

    void update();

    void draw(Graphics2D g2, ImageLoader iml);

    /**
     * is called when state is set as current
     * @param prevState state that was current activate this state
     */
    void activate(State prevState);

    /**
     * is called when state is removed from current state
     * @param nextState state that will be the next current state
     */
    void deactivate(State nextState);
}
