import java.io.*;

class Saver {

    // Calls the File version of saveCollection
    static void saveCollection(String dir, EntryCollection collection) throws IOException {
        File file = new File(dir);
        saveCollection(file, collection);
    }

    // Attempts to save an EntryCollection to disc (it could be used to save any Object to disc, really)
    static void saveCollection(File file, EntryCollection collection) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(file);
        //BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOut);
        //ObjectOutputStream out = new ObjectOutputStream(bufferedOut);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(collection);
    }
}
