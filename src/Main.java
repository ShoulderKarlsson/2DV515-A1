public class Main {
    public static void main(String[] args) {

        String categoryOne = "/wiki/Programming_Language";
        Scraper is1 = new Scraper(categoryOne, 2, 100);
        is1.run();

        String categoryTwo = "/wiki/Television";
        Scraper is2 = new Scraper(categoryTwo, 2, 500);
        is2.run();
    }
}
