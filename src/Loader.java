import java.io.*;

class Loader {

    // Calls the File version of loadCollection()
    static EntryCollection loadCollection(String dir) throws IOException, ClassNotFoundException {
        File file = new File(dir);
        return loadCollection(file);
    }

    // Tries to load an EntryCollection from the specified file
    static EntryCollection loadCollection(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(file));
            return (EntryCollection) in.readObject();
        }
        finally {
            if (in != null)
                in.close();
        }
    }
}
