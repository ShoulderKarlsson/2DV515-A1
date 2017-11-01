import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import lib.Page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scraper {
    private final String base = "https://en.wikipedia.org/";

    public void run() {
        String pageOne = base + "wiki/Programming_Language";
        String pageTwo = base + "wiki/Medicine";
        String[] pages = {pageOne, pageTwo};
        startCollect(pages);
    }

    private void startCollect(String[] pages) {
        for (String p : pages) {
            try {
                String content = getPageContent(p);
                ArrayList<String> l = getPageLinks(content);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private String getPageContent(String path) throws IOException {
        URL wikiPage = new URL(path);
        BufferedReader pageStream = new BufferedReader(new InputStreamReader(wikiPage.openStream()));
        String line;
        StringBuilder content = new StringBuilder();
        while((line = pageStream.readLine()) != null) {
            content.append(line).append("\n");
        }

        return content.toString();
    }

    private ArrayList<String> getPageLinks(String page) {
        System.out.println(page);
        Pattern p = Pattern.compile("href=\"(.*?)\"");
        Matcher m = p.matcher(page);
        ArrayList<String> links = new ArrayList<>();


        while(!m.hitEnd()) {
            if (m.find()) {
                String matchingString = page.substring(m.start(), m.end());
                String link = matchingString.substring(matchingString.indexOf("\"") + 1, matchingString.lastIndexOf("\""));
                if (isValidLink(link) && !link.contains(":") && !link.contains("#")) {
                    links.add(link);
                }
            }
        }

        return links;
    }

    private boolean isValidLink(String link) {
        Pattern p = Pattern.compile("^\\/wiki\\/[a-zA-z]*");
        Matcher m = p.matcher(link);
        return m.find();
    }
}
