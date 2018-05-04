import java.io.*;

class Saver {

    static void saveCollection(String dir, EntryCollection collection) throws IOException {
        /*Calls the File version of saveCollection*/
        File file = new File(dir);
        saveCollection(file, collection);
    }

    static void saveCollection(File file, EntryCollection collection) throws IOException {
        /*Attempts to save an EntryCollection to disc (it could probably be used to save any Object to disc, really)*/
        FileOutputStream fileOut = new FileOutputStream(file);
        //BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOut);
        //ObjectOutputStream out = new ObjectOutputStream(bufferedOut);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(collection);
    }
}
