# QuoteHolder
A program to store and retrieve categorized quotes, lyrics, poems, or any other text you want.

This project was mostly me learning how to use the Iterable, Comparable, and Serializable interfaces. The saving and loading of a collection is done using object serialization, so the save files created are not really human-readable. I'm hoping to add more functionality in the future, including the ability to edit or remove entries.

By default, the program looks for the save file in a folder named "data" in your working directory. The data folder I provide in this repo contains a save file with a pre-built collection of entries, but you can also run the program without having a pre-existing save file. To make QuoteHolder look somewhere else for the save file, just provide a string of the directory you want it to look at as the first command line argument.
