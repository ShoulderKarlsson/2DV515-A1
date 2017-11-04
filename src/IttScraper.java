import java.io.IOException;
import java.util.HashSet;

class IttScraper {
    private final int MAX_LEVEL = 2;
    private int currentLevel = 0;
    private int totalCounter = 0;
    private PageProcessor pp = new PageProcessor();
    private FileHandler fh;
    private final String BASE_URL = "https://en.wikipedia.org";
    private String startCategory;


    // Not sure if this will be a hashset in the future...
    private HashSet<String> links;

    IttScraper(String startCategory) {
        this.startCategory = startCategory;
        this.fh = new FileHandler(extractPageName(startCategory));
        links = new HashSet<>();
        links.add(startCategory);
    }

    void run() {
        try {
            while (currentLevel < MAX_LEVEL) {
                Logger.displayLevelInformation(links.size(), currentLevel);
                collectPages();

                // Increasing the current depth of the scraper.
                currentLevel++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        Logger.displayInformatinoWhenDone(totalCounter, startCategory);
    }

    private void collectPages() throws IOException {
        int superCounter = 0;
        // Holds all links found on that specific level.
        HashSet<String> levelLinks = new HashSet<>();
        for (String link : links) {

            if (superCounter == 100 || links.size() == 1) {
                // Logging process during development...
                Logger.displayLevelProgress(totalCounter, links.size(), currentLevel);
                superCounter = 0;
            }

            String pageName = extractPageName(link);
            String page = pp.readPage(BASE_URL + link);
            HashSet<String> pageLinks = pp.getPageLinks(page);
            fh.addLinksToFile(pageLinks, pageName);
            fh.addHTMLToFile(page, pageName);

            // Only adding links if we are to go one more level.
            if (currentLevel + 1 < MAX_LEVEL) {
                levelLinks.addAll(pageLinks);
            }
            totalCounter++;
            superCounter++;
        }


        Logger.displayDoneWithLevelInformaiton(totalCounter, currentLevel);
        // updating links to the next level.
        links = levelLinks;
    }

    /**
     * Collects the actual name of a /wiki/ page.
     * Uses this name for the folder structure
     * so for example, if a page is /wiki/Food,
     * I'm only interested in the last part (Food).
     * @param page wikipedia page name such as /wiki/food
     * @return String which is only the page name
     */
    private String extractPageName(String page) {
        return page.split("/")[2];
    }
}
