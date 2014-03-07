package cz.terrmith.randomverse.core.dialog;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 7.3.14
 * Time: 10:35
 * To change this template use File | Settings | File Templates.
 */
public interface NavigableText {

    /**
     * Returns main message
     * @return
     */
    String getMessage();

    /**
     * Short description
     * @return
     */
    String getDescription();

    /**
     * Calls callback if possible
     * @return true if callback was successfully called
     */
    boolean navigate();

    /**
     * Returns all children
     * @return
     */
    List<NavigableText> getOptions();
}
