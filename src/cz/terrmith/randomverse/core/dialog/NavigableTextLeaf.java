package cz.terrmith.randomverse.core.dialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 7.3.14
 * Time: 10:40
 *
 * @param <E> Object that holds results of NavigableTextLeaf selection
 *
 */
public class NavigableTextLeaf<E> implements NavigableText {

    private final NavigableTextCallback<E> callback;
    private final String message;
    private final String description;
    private final E result;

    public NavigableTextLeaf(String description, NavigableTextCallback<E> callback, E result) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        this.callback = callback;
        this.message = description;
        this.description = description;
        this.result = result;
    }

    public NavigableTextLeaf(String description, NavigableTextCallback<E> callback) {
        this(description, callback, null);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean navigate() {
        callback.onSelection(result);
        return true;
    }

    @Override
    public List<NavigableText> getOptions() {
        return new ArrayList<NavigableText>();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NavigableTextLeaf that = (NavigableTextLeaf) o;

        if (callback != null ? !callback.equals(that.callback) : that.callback != null) {
            return false;
        }
        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (message != null ? !message.equals(that.message) : that.message != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = callback != null ? callback.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
