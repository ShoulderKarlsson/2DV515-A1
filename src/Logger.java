class Logger {

    static void displayFinishedScrape(int totalAmount, String category) {
        System.out.println("Done with " + category);
        System.out.println("Saved " + totalAmount + " pages.");
    }


    static void displayStartingScrape(String page) {
        System.out.println();
        System.out.println("=============================");
        System.out.println("Crawling from " + page);
        System.out.println("=============================");
    }

    static void displayChunkingProgress(int totalPages, int maxPages) {
        int left = maxPages - totalPages;
        System.out.println("Have currently stored " + totalPages + " out of " + maxPages
                + ". (" + left + " pages left)");
    }
}
