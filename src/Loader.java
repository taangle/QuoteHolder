//CSE 205: MW 4:30
//Assignment: 6
//Author: Trevor Angle, 1213009731
//Description: A class to load a collection of entries from a location on disc

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
