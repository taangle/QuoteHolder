import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;

class EntryCollection implements Serializable {
    private static final long serialVersionUID = 3L;
    private Tag allTag;
    private HashMap<String, Tag> taggedCollection;

    // Constructor sets up main collection as well as general tag
    EntryCollection() {
        allTag = new Tag("all");
        taggedCollection = new HashMap<>();
    }

    // Adds an entry to the collection (if it hasn't been added in the past)
    void addEntry(String text, String author, Set<String> tags) {
        Entry entry = new Entry(text, author, tags);

        // Add the entry to each provided tag
        for (String t : tags) {
            // If the tag doesn't exist yet, add it to the collection first
            if (!taggedCollection.containsKey(t)) {
                taggedCollection.put(t, new Tag(t));
            }
            taggedCollection.get(t).add(entry);
        }

        //Finally, add the entry to the general tag
        allTag.add(entry);
    }

    // Calls Set<> version of addEntry()
    void addEntry(String text, String author, String[] tags) {
        addEntry(text, author, new HashSet<>(Arrays.asList(tags)));
    }

    // Returns a Tag specified by name
    Tag getTag(String tag) {
        return taggedCollection.get(tag);
    }

    // Returns a Tag specified by name
    Tag getAllEntries() {
        return allTag;
    }

    // Returns a Tag specified by name
    Set<Tag> getAllTags() {
        return new HashSet<>(taggedCollection.values());
    }

    // Returns how many total entries are in the collection
    int entryCount() {
        return allTag.entryCount();
    }

    // Returns how many total tags are being used in the collection
    int tagCount() {
        return taggedCollection.size();
    }
}
