class Logger {

    /**
     * Visual feedback about results when a cateogory is done
     * @param totalAmount total amount of pages added.
     * @param category which category was scraped
     */
    static void displayFinishedScrape(int totalAmount, String category) {
        System.out.println("Done with " + category);
        System.out.println("Saved " + totalAmount + " pages.");
    }


    /**
     * Informs user when a scrape is started and from what page
     * @param page page the scrape starts from
     */
    static void displayStartingScrape(String page) {
        System.out.println();
        System.out.println("=============================");
        System.out.println("Crawling from " + page);
        System.out.println("=============================");
    }
    
    /**
     * Visual feedback during the scrape regarding level,
     * total pages and how much have been written in the current state.
     * @param chunksWritten amount of chunks written to files.
     * @param maxPages max pages that are to be fetched
     * @param level which depth we currently is working on
     */
    static void displayChunkingProgress(int chunksWritten, int maxPages, int level) {
        int left = maxPages - chunksWritten;
        System.out.println("Have currently stored "
                + chunksWritten + " out of " + maxPages
                + ". (" + left + " pages left) "
                + "level: " + level);
    }
}
