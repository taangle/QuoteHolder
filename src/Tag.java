import java.io.Serializable;
import java.util.Iterator;

// I honestly shouldn't have named this class Tag, I should have named it TaggedEntries or something like that
public class Tag implements Iterable<Entry>, Serializable {
    private Tree<Entry> entryTree;
    private String tag;
    private static final long serialVersionUID = 6L;

    Tag(String _tag) {
        /*Constructor sets up Tree of Entries and name*/
        entryTree = new Tree<>();
        tag = _tag;
    }

    void add(Entry entry) {
        /*Inserts Entry in tree*/
        entryTree.insert(entry);
    }

    int entryCount() {
        /*Returns number of entries under this tag*/
        return entryTree.getSize();
    }

    @Override
    public String toString() {
        return tag;
    }

    @Override
    public Iterator<Entry> iterator() {
        /*Iterator simply returns the iterator of the underlying tree*/
        return entryTree.iterator();
    }

    @Override
    public boolean equals(Object o) {
        /*Returns true if the other Object is a Tag with the same tag String*/
        return o instanceof Tag && ((Tag)o).toString().equals(toString());
    }
}
