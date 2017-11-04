import javax.management.modelmbean.InvalidTargetObjectTypeException;

public class Main {
    public static void main(String[] args) {

        String categoryOne = "/wiki/Programming_Language";
        IttScraper is1 = new IttScraper(categoryOne, 2, 100);
        is1.run();

        String categoryTwo = "/wiki/Television";
        IttScraper is2 = new IttScraper(categoryTwo, 2, 500);
        is2.run();
    }
}
