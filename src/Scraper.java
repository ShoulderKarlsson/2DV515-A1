import lib.Collector;

class Scraper {
    private Collector c = new Collector();

    void run() {
        String pageOne = "/wiki/Programming_Language";
        String pageTwo = "/wiki/Medicine";
        String[] pages = {pageOne, pageTwo};
        startCollect(pages);
    }

    private void startCollect(String[] pages) {
        for (String p : pages) {
            try {
                c.visitPage(p, 0);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
