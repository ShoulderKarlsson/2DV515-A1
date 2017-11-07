import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

class Scraper {
    private PageProcessor pp;
    private FileHandler fh;
    private ArrayList<Page> chunks;
    private HashSet<String> links;
    private final int CHUNK_SIZE = 250;
    private final int MAX_PAGES = 3500;
    private final int MAX_LEVEL = 2;
    private final String BASE_URL = "https://en.wikipedia.org";


    private int totalPages = 0;
    private int currentLevel = 0;
    private String startCategory;
    private HashSet<String> levelLinks;

    Scraper(String sc) {
        fh = new FileHandler(extractPageName(sc));
        pp = new PageProcessor();
        links = new HashSet<>();
        levelLinks = new HashSet<>();
        chunks = new ArrayList<>();
        startCategory = sc;
        links.add(startCategory);
    }

    void run() {
        Logger.displayStartingScrape(startCategory);
        while (totalPages < MAX_PAGES || currentLevel <= MAX_LEVEL) {
            collectPages();
            currentLevel++;
        }

        Logger.displayFinishedScrape(totalPages, startCategory);
    }

    private void collectPages() {
        // Holds all links found on that specific level.
        for (String link : links) {
            handlePage(
                    extractPageName(link),
                    fetchPage(BASE_URL + link)
            );
            if (chunks.size() == CHUNK_SIZE || chunks.size() + totalPages == MAX_PAGES) {
                writeChunks();
            }
            if (totalPages == MAX_PAGES) {
                break;
            }
        }

        links = levelLinks;
        levelLinks = new HashSet<>();
    }

    private void handlePage(String pageName, String rawHtml) {
        HashSet<String> pageLinks = pp.getPageLinks(rawHtml);
        String noTags = pp.cleanHTMLContent(rawHtml);
        String words = pp.findWords(noTags);
        // Adding all links for the current level.
        levelLinks.addAll(pageLinks);
        // Storing information about current page in object -> written later.
        chunks.add(new Page(pageName, rawHtml, words, noTags, pageLinks));
    }

    private String fetchPage(String url) {
        StringBuilder content = new StringBuilder();
        try {
            URL wikiPage = new URL(url);
            BufferedReader pageStream = new BufferedReader(new InputStreamReader(wikiPage.openStream()));
            String line;
            while ((line = pageStream.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        return content.toString();
    }

    private void writeChunks() {
        chunks.forEach(page -> {
            totalPages = fh.storeContent(page.pageName, page.links, page.raw, page.noTags, page.words);
        });
        Logger.displayChunkingProgress(totalPages, MAX_PAGES, currentLevel);
        chunks.clear();
    }

    /**
     * Collects the actual name of a /wiki/ page.
     * Uses this name for the folder structure
     * so for example, if a page is /wiki/Food,
     * I'm only interested in the last part (Food).
     *
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
        //
        private String pageName;
        private HashSet<String> links;
        private String raw;
        private String words;
        private String noTags;

        Page(String pageName, String raw, String words, String noTags, HashSet<String> links) {
            this.pageName = pageName;
            this.raw = raw;
            this.words = words;
            this.noTags = noTags;
            this.links = links;
        }
    }
}
