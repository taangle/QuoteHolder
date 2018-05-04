import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Entry implements Comparable<Entry>, Serializable {
    private String author;
    private String text;
    private Set<String> tags;
    private static final long serialVersionUID = 1L;

    // Constructor initializes all state-containing fields
    Entry(String _text, String _author, Set<String> _tags) {
        text = _text;
        author = _author;
        tags = _tags;
    }

    // Calls the Set<> version of the constructor
    Entry(String _text, String _author, String[] _tags) {
        this(_text, _author, new HashSet<>(Arrays.asList(_tags)));
    }

    // Returns a short preview version of the entry
    String preview() {
        //todo Make previewLength mutable by user
        int previewLength = 20;
        if (getText().length() >= previewLength) {
            return String.format("%s - \"%s...\"", getAuthor(), getText().substring(0, previewLength));
        }
        return String.format("%s - \"%s...\"", getAuthor(), getText().substring(0, getText().length()));
    }

    @Override
    // Compares an Entry to another Entry first by author, then by text
    public int compareTo(Entry otherEntry) {
        int result;
        // if they do not have the same author, just compare by author
        if ((result = getAuthor().compareTo(otherEntry.getAuthor())) != 0) {
            return result;
        }
        // if they do have the same author, compare by text
        else {
            return getText().compareTo(otherEntry.getText());
        }
    }

    String getAuthor() {
        return author;
    }

    String getText() {
        return text;
    }

    Set<String> getTags() {
        return tags;
    }

    @Override
    // Returns a string representation of all the Entry's contents
    public String toString() {
        return String.format("Entry{%n\t" +
                "author='" + author + "'%n\t" +
                "text='" + text + "'%n\t" +
                "tags=" + tags + "%n" +
                '}');
    }
}
