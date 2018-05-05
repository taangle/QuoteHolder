import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class QuoteHolder {
    private static PrintStream out = System.out;
    private static Scanner in = new Scanner(System.in);
    private static String saveLocation = "data/save.col";
    private static EntryCollection collection;

    // Main commands
    private static final char ADD = 'a';
    private static final char FIND = 'f';
    private static final char INFO = 'i';
    private static final char SAVE = 's';
    private static final char RESET = 'r';
    private static final char QUIT = 'q';

    // Additional commands
    private static final char ENTRY_COUNT = 'e';
    private static final char ALL_TAGS = 'a';
    private static final char TAG_COUNT = 't';
    private static final char YES = 'y';
    private static final char NO = 'n';

    private static final char NONE = '~';
    private static final long WAIT_TIME = 800;
    private static boolean finished = false;

    // The main method of the program, receives user input and calls methods to act on it
    public static void main(String[] args) throws InterruptedException {
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
            // A fake pause that makes the program feel a bit nicer, in my opinion
            Thread.sleep(WAIT_TIME);
        }
    }

    // Starts up the program by attempting to load a collection from disc
    private static void initialize(String[] args) {
        out.println();
        if (args.length > 0) {
            saveLocation = args[0].concat("\\save.col");
        }
        try {
            // Try to load a pre-existing collection from the save location
            collection = Loader.loadCollection(saveLocation);
            out.println("A collection was successfully loaded from a save file!");
        }
        catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
            out.printf(
                        "I was unable to load a collection from a save file!%n" +
                        "That's OK though, a new collection will be made that you can add entries to (and then save).%n" +
                        "If that isn't what you want, you'll need to try running the program again.%n" +
                        "Just so you know, you can pass a directory as an argument when running QuoteHolder from the command%n" +
                        "line, in order to use a file somewhere other than the default location (e.g. C:\\Documents\\wherever).%n"
            );
            // If a collection was not loaded, a new one is instantiated
            collection = new EntryCollection();
        }
    }

    // Asks user for new entry to attempt to add to collection
    private static void add() {
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

    // Asks user for a tag to find entries under
    private static void find() {
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
            else {
                out.printf("Index out of range%n");
            }
        }
        else {
            out.printf("There are no entries tagged \"%s\"%n", tagName);
        }
    }

    // Provides a few options for user to find more information about the collection
    private static void getInfo() {
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

    // Prints an unordered list of all the tags used in the collection
    private static void getAllTags() {
        Set<Tag> allTags = collection.getAllTags();
        out.println();
        for(Tag t : allTags) {
            out.printf("%s - %d%n", t.toString(), t.entryCount());
        }
    }

    // Prints a menu for getting info about the collection
    private static void printInfoMenu() {
        out.println();

        out.format("%s: All tags%n", ALL_TAGS);
        out.format("%s: Number of tags%n", TAG_COUNT);
        out.format("%s: Number of entries%n", ENTRY_COUNT);

        out.println();
    }

    // Asks user if they want to reset to a new collection (replaces the collection in main memory with a new one)
    private static void reset() {
        out.println("Are you sure you want to reset to a new collection? (y/n)");
        char command = in.next().charAt(0);
        in.nextLine();

        switch (command) {
            case YES:
                collection = new EntryCollection();
                out.printf(
                            "You are now operating on an empty collection.%n" +
                            "The old collection will be available on disc until you save this new collection.%n"
                );
                break;
        }
    }

    // Asks if user really wants to quit
    private static void quit() {
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

    // Tries to save the collection to disc
    private static void save() {
        try {
            Saver.saveCollection(saveLocation, collection);
            out.println("The collection was successfully saved!");
        }
        catch (IOException e) {
            out.println("The collection was NOT successfully saved!");
        }
    }

    // Prints the main menu
    private static void printMenu() {
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
