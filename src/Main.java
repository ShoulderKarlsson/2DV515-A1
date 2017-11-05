public class Main {
    public static void main(String[] args) {

        String categoryOne = "/wiki/Programming_Language";
        Scraper s1 = new Scraper(categoryOne);
        s1.run();

        String categoryTwo = "/wiki/Television";
        Scraper s2 = new Scraper(categoryTwo);
        s2.run();

    }
}
