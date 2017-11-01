import java.util.ArrayList;

public class Collector {
    private final int MAX_RUNS = 2;
    private String base = "https://en.wikipedia.org";

    private PageProcessor pP = new PageProcessor();
    private FileHandler fH = new FileHandler();

    // represents where all of the pages are coming from
    private String basePage = "";


    Collector(String bP) {
        this.basePage = bP;

        // only interested in last part of /wiki/SomeStartPage
        fH.createBaseStructureFor(bP.split("/")[2]);
    }

    /**
     *
     * @param url url to a wiki page
     * @param level how deep are we?
     */
    public void visitPage(String url, int level) {
        System.out.println("Fetching page content for: " + base +  url);
        System.out.println("Current level: " + level);
        // All levels are visited, do something here.
        if (level == MAX_RUNS) { return; }

        try {

            // 1. Collect the page for the specific url
            String pageContent = pP.readPage(base + url);

            // 2. Extract the links for that page
            ArrayList<String> pageLinks = pP.getPageLinks(pageContent);

            // 3. Store all the links for that page to a file.
            fH.addLinksToFile(pageLinks, url);





            // Entire content of the url

            // all URLS for that page


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
