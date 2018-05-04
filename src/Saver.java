//CSE 205: MW 4:30
//Assignment: 6
//Author: Trevor Angle, 1213009731
//Description: A class to save a collection of classes to a location on disk

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
