import java.io.*;

class Loader {

    // Calls the File version of loadCollection()
    static EntryCollection loadCollection(String dir) throws IOException, ClassNotFoundException {
        File file = new File(dir);
        return loadCollection(file);
    }

    // Tries to load an EntryCollection from the specified file
    static EntryCollection loadCollection(File file) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(file);
        //BufferedInputStream bufferedIn = new BufferedInputStream(fileIn);
        //ObjectInputStream in = new ObjectInputStream(bufferedIn);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        return (EntryCollection)in.readObject();
    }
}
