package cz.terrmith.randomverse.core.dialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 7.3.14
 * Time: 10:40
 * To change this template use File | Settings | File Templates.
 */
public class NavigableTextLeaf implements NavigableText {

    private final NavigableTextCallback callback;
    private final String message;
    private final String description;

    public NavigableTextLeaf(String description, NavigableTextCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        this.callback = callback;
        this.message = description;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean navigate() {
        callback.onSelection();
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
