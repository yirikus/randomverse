package cz.terrmith.randomverse.core.dialog;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 4.3.14
 * Time: 14:03
 * To change this template use File | Settings | File Templates.
 */
public class NavigableTextOption {
    private String text;
    private String key;

    public NavigableTextOption(String text, String key) {
        this.text = text;
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NavigableTextOption that = (NavigableTextOption) o;

        if (key != null ? !key.equals(that.key) : that.key != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

    @Override
    public String toString() {
        return key + ": " + text;
    }
}
