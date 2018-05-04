import java.io.*;

class Loader {

    static EntryCollection loadCollection(String dir) throws IOException, ClassNotFoundException {
        /*Calls the File version of loadCollection()*/
        File file = new File(dir);
        return loadCollection(file);
    }

    static EntryCollection loadCollection(File file) throws IOException, ClassNotFoundException {
        /*Tries to load an EntryCollection from the specified file*/
        FileInputStream fileIn = new FileInputStream(file);
        //BufferedInputStream bufferedIn = new BufferedInputStream(fileIn);
        //ObjectInputStream in = new ObjectInputStream(bufferedIn);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        return (EntryCollection)in.readObject();
    }
}
