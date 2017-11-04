import javax.management.modelmbean.InvalidTargetObjectTypeException;

public class Main {
    public static void main(String[] args) {
//        Scraper s = new Scraper();
//        s.run();


        IttScraper is = new IttScraper("/wiki/Programming_Language");
        is.run();
    }
}
