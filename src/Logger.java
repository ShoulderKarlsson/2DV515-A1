class Logger {

    static void displayLevelProgress(int currentChunk, int total, int level) {
        System.out.println("Storing chunk #" + currentChunk + " of " + total + " on " + "level " + level + ".");
    }

    static void displayLevelInformation(int levelAmount, int level) {
        System.out.println();
        System.out.println("Pages on level " + level + " is " + levelAmount);
    }

    static void displayFinishedScrape(int totalAmount, String category) {
        System.out.println("Done with " + category);
        System.out.println("Saved " + totalAmount + " pages.");
    }

    static void displayDoneWithLevelInformaiton(int amount, int level) {
        System.out.println("Done with level " + level);
        System.out.println("Have stored a total of " + amount + " pages so far.");
    }

    static void displayStartingScrape(String page, int chunkSize, int maxDepth) {
        System.out.println();
        System.out.println("=============================");
        System.out.println("Crawling from " + page);
        System.out.println("Chunk size: " + chunkSize);
        System.out.println("Max level depth: " + maxDepth);
        System.out.println("=============================");
    }
}
