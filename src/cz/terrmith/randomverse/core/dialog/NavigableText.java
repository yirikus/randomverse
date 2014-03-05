package cz.terrmith.randomverse.core.dialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Message with options
 *
 * User: TERRMITh
 * Date: 4.3.14
 * Time: 14:00
 *
 */
public class NavigableText {

    private final NavigableTextCallback callback;
    private String message;
    private List<NavigableTextOption> options;

    public NavigableText(String message, NavigableTextOption[] options) {
        if (options == null) {
            throw new IllegalArgumentException("options must not be null, did you wanted to use overloaded constructor with callback?");
        }

        this.message = message;
        this.options = Arrays.asList(options);
        this.callback = null;
    }

    public NavigableText(String message, NavigableTextCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("options must not be null, did you wanted to use overloaded constructor with options?");
        }

        this.message = message;
        this.callback = callback;
        this.options = new ArrayList<NavigableTextOption>();
    }

    public String getMessage() {
        return message;
    }

    public List<NavigableTextOption> getOptions() {
        return Collections.unmodifiableList(options);
    }

    public boolean containsOption(String key) {
        for (NavigableTextOption nto : options) {
            if (key.equals(nto.getKey())) {
                return true;
            }
        }
        return false;
    }

    public String printOptions() {
        StringBuilder sb = new StringBuilder();
        for (NavigableTextOption nto : options) {
            sb.append(nto.getKey() + ", ");
        }
        return sb.toString();
    }

    /**
     * Returns true if successful
     * @return
     */
    public boolean callback() {
        if (callback != null) {
            callback.onSelection();
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NavigableText that = (NavigableText) o;

        if (message != null ? !message.equals(that.message) : that.message != null) {
            return false;
        }
        if (options != null ? !options.equals(that.options) : that.options != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "NavigableText{" +
                "message='" + message + '\'' +
                ", options=" + options +
                '}';
    }
}
