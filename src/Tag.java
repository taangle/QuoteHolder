import java.io.Serializable;
import java.util.Iterator;

// I honestly shouldn't have named this class Tag, I should have named it TaggedEntries or something like that
public class Tag implements Iterable<Entry>, Serializable {
    private Tree<Entry> entryTree;
    private String tag;
    private static final long serialVersionUID = 6L;

    // Constructor sets up Tree of Entries and tag's name
    Tag(String _tag) {
        entryTree = new Tree<>();
        tag = _tag;
    }

    // Inserts Entry in tree
    void add(Entry entry) {
        entryTree.insert(entry);
    }

    // Returns number of entries under this tag
    int entryCount() {
        return entryTree.getSize();
    }

    @Override
    public String toString() {
        return tag;
    }

    @Override
    // Returns number of entries under this tag
    public Iterator<Entry> iterator() {
        return entryTree.iterator();
    }

    @Override
    // Returns true if the other Object is a Tag with the same tag String
    public boolean equals(Object o) {
        return o instanceof Tag && o.toString().equals(toString());
    }
}
