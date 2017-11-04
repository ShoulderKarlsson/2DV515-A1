import javax.management.modelmbean.InvalidTargetObjectTypeException;

public class Main {
    public static void main(String[] args) {
//        Scraper s = new Scraper();
//        s.run();


        IttScraper is1 = new IttScraper("/wiki/Programming_Language");
        is1.run();

        IttScraper is2 = new IttScraper("/wiki/Television");
        is2.run();
    }
}
