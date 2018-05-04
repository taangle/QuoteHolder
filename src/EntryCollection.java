//CSE 205: MW 4:30
//Assignment: 6
//Author: Trevor Angle, 1213009731
//Description: A class to manage all the tagged entries

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;

class EntryCollection implements Serializable {
    private static final long serialVersionUID = 3L;
    private Tag allTag;
    private HashMap<String, Tag> taggedCollection;

    EntryCollection() {
        /*Constructor sets up main collection as well as general tag*/
        allTag = new Tag("all");
        taggedCollection = new HashMap<>();
    }

    void addEntry(String text, String author, Set<String> tags) {
        /*Adds a collection if it hasn't been added yet*/
        Entry entry = new Entry(text, author, tags);
        for (String t : tags) {
            if (!taggedCollection.containsKey(t)) {
                taggedCollection.put(t, new Tag(t));
            }
            taggedCollection.get(t).add(entry);
        }
        allTag.add(entry);
    }

    void addEntry(String text, String author, String[] tags) {
        /*Calls Set<> version of addEntry()*/
        addEntry(text, author, new HashSet<>(Arrays.asList(tags)));
    }

    Tag getTag(String tag) {
        /*Returns a Tag specified by name*/
        return taggedCollection.get(tag);
    }

    Tag getAllEntries() {
        /*Returns a tag that contains every entry*/
        return allTag;
    }

    Set<Tag> getAllTags() {
        /*Returns a Set<> of all the Tags in use*/
        return new HashSet<>(taggedCollection.values());
    }

    int entryCount() {
        /*Returns how many total entries are in the collection*/
        return allTag.entryCount();
    }

    int tagCount() {
        /*Returns how many total tags are being used in the collection*/
        return taggedCollection.size();
    }
}
