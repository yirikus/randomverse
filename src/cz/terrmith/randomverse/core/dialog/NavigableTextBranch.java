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
public class NavigableTextBranch implements NavigableText{

    private final String message;
    private final String description;
    private List<NavigableText> options;

    /**
     * Navigable text with empty options
     * @param message
     */
    public NavigableTextBranch(String message, String description) {

        this.message = message;
        this.description = description;
        this.options = new ArrayList<NavigableText>();
    }

    public NavigableTextBranch(String message, String description, NavigableText[] options) {
        if (options == null) {
            throw new IllegalArgumentException("options must not be null, did you wanted to use overloaded constructor with callback?");
        }

        this.message = message;
        this.description = description;
        this.options = Arrays.asList(options);
    }

    public void addOption(NavigableText option) {
        options.add(option);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public List<NavigableText> getOptions() {
        return Collections.unmodifiableList(options);
    }

//    public boolean containsOption(String key) {
//        for (NavigableTextBranch nto : options) {
//            if (key.equals(nto.getKey())) {
//                return true;
//            }
//        }
//        return false;
//    }

    public String printOptions() {
        StringBuilder sb = new StringBuilder();
        for (NavigableText nto : options) {
            sb.append(nto.getDescription() + ", ");
        }
        return sb.toString();
    }

    /**
     * Returns true if successful
     * @return
     */
    @Override
    public boolean navigate() {
        return false;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NavigableTextBranch that = (NavigableTextBranch) o;

        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
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
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NavigableTextBranch{" +
                "message='" + message + '\'' +
                ", options=" + options +
                '}';
    }
}
