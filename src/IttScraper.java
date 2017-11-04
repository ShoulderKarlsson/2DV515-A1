import java.io.IOException;
import java.util.HashSet;

class IttScraper {
    private final int MAX_LEVEL = 2;
    private int currentLevel = 0;
    private PageProcessor pp = new PageProcessor();
    private FileHandler fh;
    private final String BASE_URL = "https://en.wikipedia.org";



    // Not sure if this will be a hashset in the future...
    private HashSet<String> links;

    IttScraper(String start) {
        this.fh = new FileHandler(extractPageName(start));
        links = new HashSet<>();
        links.add(start);
    }

    void run() {
        try {
            while(currentLevel < MAX_LEVEL) {
                collectPages();

                // Increasing the current depth of the scraper.
                currentLevel++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    void collectPages() throws IOException {
        int count = 0;
        // Holds all links found on that specific level.
        HashSet<String> levelLinks = new HashSet<>();
        for (String link : links) {
            Logger.displayProgress(count, links.size());
            String page = pp.readPage(BASE_URL + link);
            HashSet<String> pageLinks = pp.getPageLinks(page);
            fh.addLinksToFile(pageLinks, extractPageName(link));
            levelLinks.addAll(pageLinks);
            count++;
        }


        // updating links to the next level.
        links = levelLinks;
    }

    /**
     * Collects the actual name of a /wiki/ page.
     * Uses this name for the folder structure
     * so for example, if a page is /wiki/Food,
     * I'm only interested in Food.
     * @param page wikipedia page name such as /wiki/food
     * @return String which is only the page name
     */
    private String extractPageName(String page) {
        return page.split("/")[2];
    }
}
