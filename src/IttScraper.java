import java.util.ArrayList;
import java.util.HashSet;

class IttScraper {
    private PageProcessor pp;
    private FileHandler fh;
    private ArrayList<Page> chunks;
    private HashSet<String> links;

    private final int LEVEL_DEPTH;
    private final int CHUNK_SIZE;
    private final String BASE_URL = "https://en.wikipedia.org";

    private int currentLevel = 0;
    private int totalCounter = 0;
    private String startCategory;

    IttScraper(String startCategory, int levelDepth, int chunkSize) {
        this.fh = new FileHandler(extractPageName(startCategory));
        this.pp = new PageProcessor();
        links = new HashSet<>();
        chunks = new ArrayList<>();
        this.startCategory = startCategory;
        this.LEVEL_DEPTH = levelDepth;
        this.CHUNK_SIZE = chunkSize;
        links.add(startCategory);
    }

    void run() {
        Logger.displayStartingScrape(this.startCategory, CHUNK_SIZE, LEVEL_DEPTH);
        while (currentLevel < LEVEL_DEPTH) {
            totalCounter = 0;
            Logger.displayLevelInformation(links.size(), currentLevel);
            collectPages();
            currentLevel++;
        }

        Logger.displayFinishedScrape(totalCounter, startCategory);
    }

    private void collectPages() {
        // Holds all links found on that specific level.
        HashSet<String> levelLinks = new HashSet<>();
        for (String link : links) {

            String pageName = extractPageName(link);
            String rawPage = pp.readPage(BASE_URL + link);
            HashSet<String> pageLinks = pp.getPageLinks(rawPage);
            levelLinks.addAll(pageLinks);
            chunks.add(new Page(pageName, rawPage, pageLinks));

            if (chunks.size() == CHUNK_SIZE) {
                totalCounter += chunks.size();
                Logger.displayLevelProgress(totalCounter, links.size(), currentLevel);
                writeChunks();
            }
        }

        // Writing remaining chunks
        if (chunks.size() != 0) {
            totalCounter += chunks.size();
            Logger.displayLevelProgress(totalCounter, links.size(), currentLevel);
            writeChunks();
        }

        links = levelLinks;
    }

    private void writeChunks() {
        chunks.forEach(page -> {
            fh.addHTMLToFile(page.pageContent, page.pageName);
            fh.addLinksToFile(page.pageLinks, page.pageName);
        });

        chunks.clear();
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


    /**
     * Simple inner class that holds information about page
     * when the page is waiting to get stored in the chunk pile.
     */
    private class Page {
        private String pageName;
        private HashSet<String> pageLinks;
        private String pageContent;

        Page(String pageName, String pageContent, HashSet<String> pageLinks) {
            this.pageName = pageName;
            this.pageLinks = pageLinks;
            this.pageContent = pageContent;
        }
    }
}
