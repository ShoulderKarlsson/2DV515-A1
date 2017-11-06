import java.util.ArrayList;
import java.util.HashSet;

class Scraper {
    private PageProcessor pp;
    private FileHandler fh;
    private ArrayList<Page> chunks;
    private HashSet<String> links;
    private final int CHUNK_SIZE = 25;
    private final int MAX_PAGES = 100;
    private final String BASE_URL = "https://en.wikipedia.org";


    // Total amount of pages we've stored.
    private int totalPages = 0;
    private String startCategory;

    Scraper(String startCategory) {
        this.fh = new FileHandler(extractPageName(startCategory));
        this.pp = new PageProcessor();
        links = new HashSet<>();
        chunks = new ArrayList<>();
        this.startCategory = startCategory;
        links.add(startCategory);
    }

    void run() {
        Logger.displayStartingScrape(startCategory);
        while (totalPages < MAX_PAGES) {
            collectPages();
        }

        Logger.displayFinishedScrape(totalPages, startCategory);
    }

    private void collectPages() {
        // Holds all links found on that specific level.
        HashSet<String> levelLinks = new HashSet<>();
        for (String link : links) {
            String pageName = extractPageName(link);
            String html = pp.readPage(BASE_URL + link);
            HashSet<String> pageLinks = pp.getPageLinks(html);
            levelLinks.addAll(pageLinks);

            chunks.add(new Page(pageName, html, pageLinks));
            if (chunks.size() == CHUNK_SIZE ||
                chunks.size() + totalPages == MAX_PAGES) {
                writeChunks();
            }


            // If we have as many pages as requested, stop.
            if (totalPages == MAX_PAGES) {
                break;
            }

        }

        links = levelLinks;
    }


    private void writeChunks() {
        chunks.forEach(page -> {
            String noHtml = pp.cleanHTMLContent(page.pageContent);
            totalPages = fh.storeContent(page.pageName, page.pageLinks, page.pageContent, noHtml, "");
        });
//        chunks.forEach(page -> {
//
//                }
//                totalPages = fh.storeContent(page.pageName, page.pageLinks, page.pageContent));

        Logger.displayChunkingProgress(totalPages, MAX_PAGES);
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
     * Placeholder class for the scraper that stores the pages while
     * the page is stored as a chunk
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
