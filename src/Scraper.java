import java.util.HashSet;

class Scraper {
    void run() {
        String pageOne = "/wiki/Programming_Language";
        String pageTwo = "/wiki/Games";
        
        Superman s1 = new Superman(pageOne);
        s1.visitPages();

//        Superman s2 = new Superman(pageTwo);
//        s2.visitPages();
    }


    private class Superman {
        private PageProcessor pp = new PageProcessor();
        private FileHandler fh;
        private String startPage = "";
        private final String BASE_URL = "https://en.wikipedia.org";
        private final int MAX_LEVEL = 2;
        private int counter = 0;
        private final int MAX_PAGES = 1000;
        private HashSet<String> visited = new HashSet<>();


        Superman(String base) {
            fh = new FileHandler(base.split("/")[2]);
            this.startPage = base;
        }

        void visitPages() {
            HashSet<String> set = new HashSet<>();
            set.add(startPage);
            visitPages(set, 0);
        }

        private void visitPages(HashSet<String> pages, int currentLevel) {
            try {
                HashSet<String> pageLinks;
                for (String page : pages) {
                    if (visited.contains(page)) continue;

                    visited.add(page);
                    String rawPage = pp.readPage(BASE_URL + page);
                    pageLinks = pp.getPageLinks(rawPage);
                    fh.addLinksToFile(pageLinks, page.split("/")[2]);
                    if (currentLevel < 1) {
                        visitPages(pageLinks, currentLevel + 1);
                    }
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        
    }
}
