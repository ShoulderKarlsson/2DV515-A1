import java.io.IOException;

class Logger {

    static void displayLevelProgress(int currentChunk, int total, int level) {
        System.out.println("Storing chunk #" + currentChunk + " of " + total + " on " + "level " + level + ".");

//        int progress = Math.floorDiv(current * 100, total);
//        System.out.println("Currently done with " + progress + "% of links on level " + level);
    }

    static void displayLevelInformation(int levelAmount, int level) {
        System.out.println("Starting on level " + level);
        System.out.println("Pages on current level is: " + levelAmount);
    }

    static void displayFinishedScrape(int totalAmount, String category) {
        System.out.println("Done with " + category);
        System.out.println("Saved " + totalAmount + " pages.");
        System.out.println();
    }

    static void displayDoneWithLevelInformaiton(int amount, int level) {
        System.out.println("Done with level " + level);
        System.out.println("Have stored a total of " + amount + " pages so far.");
    }

    static void displayStartingScrape(String page) {
        System.out.println("Starting scrape on page " + page);
    }
}
