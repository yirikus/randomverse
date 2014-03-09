package cz.terrmith.randomverse.core.dialog;

/**
 * Callback for navigable text
 *
 * User: TERRMITh
 * Date: 4.3.14
 * Time: 16:33
 * @param <E>
 */
public interface NavigableTextCallback<E> {
    /**
     * Will be called when event is concluded
     * @param event additional information about the results
     */
    void onSelection(E event);
}
