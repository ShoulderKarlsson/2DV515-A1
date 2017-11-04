import java.util.HashSet;

class Collector {
    private final int MAX_LEVELS = 3;
    private String base = "https://en.wikipedia.org";
    private PageProcessor pP = new PageProcessor();
//    private FileHandler fH = new FileHandler();
    private FileHandler fh;
    private HashSet<String> visited = new HashSet<>();

    Collector(String bP) {
        fh = new FileHandler(bP.split("/")[2]);
        fh.createBaseStructure();
    }

    /**
     *
     * @param url url to a wiki page
     * @param level how deep are we?
     */
    void visitPage(String url, int level) {
        // All levels are visited, do something here.
        if (level == MAX_LEVELS) { return; }

        try {
            String pageContent = pP.readPage(base + url);
            HashSet<String> pageLinks = pP.getPageLinks(pageContent);
//            fh.addLinksToFile(pageLinks, url.split("/")[2]);
            for (String link : pageLinks) {
//                if (!visited.contains(link)) {
//                    visited.add(link);
                    visitPage(link, level + 1);
//                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
