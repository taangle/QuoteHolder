//CSE 205: MW 4:30
//Assignment: 6
//Author: Trevor Angle, 1213009731
//Description: The class containing the main method to interface with QuoteHolder

/*My project consists of a system to store categorized quotes, poems, or any other text you want in a convenient manner.
* The quotes that you add are persistent between sessions, that is, when you call the "save" command, a "save.col" file
* is made that contains a serialized version of all the objects necessary to restore the state of your collection the
* next time you run QuoteHolder. The default location for "save.col" is within a folder called "data" within your current
* working directory. If you have this folder, whether or not "save.col" is already in it, the program will run fine. If
* you do not have the "data" directory, you will not be able to save your collection unless you pass a string of some
* other directory as the first argument to this program when running it from the command line (or any context where you
* can add arguments).*/

/*I will submit this project with a default "save.col" that you can check out by placing it within a folder called "data"
* within your working directory, but it is by no means necessary for the program to work.*/

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class QuoteHolder {
    private static PrintStream out = System.out;
    private static Scanner in = new Scanner(System.in);
    private static String saveLocation = "data/save.col";
    private static EntryCollection collection;

    private static boolean finished = false;

    private static final char ADD = 'a';
    private static final char FIND = 'f';
    private static final char INFO = 'i';
    private static final char SAVE = 's';
    private static final char RESET = 'r';
    private static final char QUIT = 'q';

    private static final char ENTRY_COUNT = 'e';
    private static final char ALL_TAGS = 'a';
    private static final char TAG_COUNT = 't';

    private static final char YES = 'y';
    private static final char NO = 'n';

    private static final char NONE = '~';
    private static final long WAIT_TIME = 800;

    public static void main(String[] args) throws InterruptedException {
        /*The main method of the program, receives user input and calls methods to act on it*/
        out.println("Welcome to QuoteHolder, your personal collection of quotes, poems, or any other text you want to store!");
        initialize(args);
        while (!finished) {
            printMenu();
            char command;
            try {
                command = in.nextLine().toLowerCase().charAt(0);
            } catch (StringIndexOutOfBoundsException e) {
                out.println("You didn't enter a command!");
                command = NONE;
            }
            out.println();
            switch (command) {
                case ADD:
                    add();
                    break;
                case FIND:
                    find();
                    break;
                case INFO:
                    getInfo();
                    break;
                case SAVE:
                    save();
                    break;
                case RESET:
                    reset();
                    break;
                case QUIT:
                    quit();
                    break;
                case NONE:
                    break;
                default:
                    out.println("Command not recognized");
                    break;
            }
            Thread.sleep(WAIT_TIME);
        }
    }

    private static void initialize(String[] args) {
        /*Starts up the program by attempting to load a collection from disc*/
        out.println();
        if (args.length > 0) {
            saveLocation = args[0].concat("\\save.col");
        }
        try {
            collection = Loader.loadCollection(saveLocation);
            out.println("A collection was successfully loaded from a save file!");
        }
        catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
            out.printf(
                    "I was unable to load a collection from a save file!%n" +
                            "That's OK though, a new collection will be made that you can add entries to (and then save).%n" +
                            "If that isn't what you want, you'll need to try running the program again.%n" +
                            "Just so you know, you can pass a directory as an argument when running QuoteHolder from%n" +
                            "the command line, to use a file somewhere other than the default location (e.g. C:\\Documents\\wherever)."
            );
            collection = new EntryCollection();
        }
        finally {
            out.println();
        }
    }

    private static void add() {
        /*Asks user for new entry to attempt to add to collection*/
        int oldCount = collection.entryCount();
        String text;
        String author;
        Set<String> tags = new HashSet<>();
        out.println("Enter the text:");
        text = in.nextLine();
        out.println("Enter the author:");
        author = in.nextLine();
        out.println("Enter the tags as a space-separated list:");
        Scanner tagScanner = new Scanner(in.nextLine());
        while (tagScanner.hasNext()) {
            tags.add(tagScanner.next());
        }
        collection.addEntry(text, author, tags);
        if (collection.entryCount() > oldCount) {
            out.println("Your entry was successfully added!");
        }
        else {
            out.println("It seems like your entry might have already been in the collection. Why not search for it?");
        }
    }

    private static void find() {
        /*Asks user for a tag to find entries under*/
        out.println("Enter the tag to find entries under (enter \"all\" to see all entries):");
        String tagName = in.next();
        in.nextLine();
        out.println();
        Tag tag;
        if (tagName.equals("all")) {
            tag = collection.getAllEntries();
        }
        else {
            tag = collection.getTag(tagName);
        }
        if (tag != null) {
            Map<Integer, Entry> indexedEntries = new HashMap<>();
            out.println("Here are indexed previews of each entry under that tag:");
            int index = 1;
            for (Entry e : tag) {
                indexedEntries.put(index, e);
                out.printf("%d: %s%n", index, e.preview());
                index++;
            }
            out.println();
            out.println("Enter the index of the entry you want to see in full:");
            int selectedIndex = in.nextInt();
            in.nextLine();
            if (selectedIndex > 0 && selectedIndex <= tag.entryCount()) {
                Entry selectedEntry = indexedEntries.get(selectedIndex);
                out.println();
                out.printf("\"%s\"%n", selectedEntry.getText());
                out.printf("    - %s%n", selectedEntry.getAuthor());
            }
        }
        else {
            out.println("It would appear that that tag does not exist");
        }
    }

    private static void getInfo() {
        /*Provides a few options for user to find more information about the collection*/
        out.println("What kind of info do you want?");
        printInfoMenu();
        char command = in.next().charAt(0);
        in.nextLine();
        switch (command) {
            case ALL_TAGS:
                getAllTags();
                break;
            case TAG_COUNT:
                out.printf("Number of tags: %d%n", collection.tagCount());
                out.println();
                break;
            case ENTRY_COUNT:
                out.printf("Number of entries: %d%n", collection.entryCount());
                out.println();
                break;
        }
    }

    private static void getAllTags() {
        /*Prints a list of all the tags used in the collection*/
        Set<Tag> allTags = collection.getAllTags();
        out.println();
        out.println(allTags);
    }

    private static void printInfoMenu() {
        /*Prints a menu for getting info about the collection*/
        out.println();

        out.format("%s: All tags%n", ALL_TAGS);
        out.format("%s: Number of tags%n", TAG_COUNT);
        out.format("%s: Number of entries%n", ENTRY_COUNT);

        out.println();
    }

    private static void reset() {
        /*Asks user if they want to reset to a new collection (replaces the collection in main memory with a new one)*/
        out.println("Are you sure you want to reset to a new collection? (y/n)");
        char command = in.next().charAt(0);
        in.nextLine();
        switch (command) {
            case YES:
                collection = new EntryCollection();
                out.println("You are now operating on an empty collection");
                break;
        }
    }

    private static void quit() {
        /*Asks if user really wants to quit*/
        out.printf("Your collection is not automatically saved. Are you sure you want to quit? (%s/%s)%n", YES, NO);
        char input = in.nextLine().charAt(0);
        switch (input) {
            case YES:
                out.println("Quitting...");
                finished = true;
                break;
            default:
                out.println("Not quitting");
                break;
        }
    }

    private static void save() {
        /*Tries to save the collection to disc*/
        try {
            Saver.saveCollection(saveLocation, collection);
            out.println("The collection was successfully saved!");
        }
        catch (IOException e) {
            out.println("The collection was NOT successfully saved!");
        }
    }

    private static void printMenu() {
        /*Prints the main menu*/
        out.println();
        printSeparator();

        out.format("Enter a command from below:%n");
        out.format("%s: Add a new entry%n", ADD);
        out.format("%s: Find entries by tag%n", FIND);
        out.format("%s: Get information about your collection%n", INFO);
        out.format("%s: Save your collection%n", SAVE);
        out.format("%s: Reset entry collection%n", RESET);
        out.format("%s: Quit%n", QUIT);

        printSeparator();
        out.println();
    }

    private static void printSeparator() {
        out.println("********************");
    }
}
