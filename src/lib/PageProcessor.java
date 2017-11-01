package lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PageProcessor {
    ArrayList<String> getPageLinks(String page) {
        Pattern p = Pattern.compile("href=\"(.*?)\"");
        Matcher m = p.matcher(page);
        ArrayList<String> links = new ArrayList<>();

        while(!m.hitEnd()) {
            if (m.find()) {
                String matchingString = page.substring(m.start(), m.end());
                String link = matchingString.substring(matchingString.indexOf("\"") + 1, matchingString.lastIndexOf("\""));
                if (isValidLink(link) && !link.contains(":") && !link.contains("#") && !link.contains("//")) {
                    links.add(link);
                }
            }
        }

        return links;
    }


    String readPage(String url) throws IOException {
        URL wikiPage = new URL(url);
        BufferedReader pageStream = new BufferedReader(new InputStreamReader(wikiPage.openStream()));
        String line;
        StringBuilder content = new StringBuilder();
        while((line = pageStream.readLine()) != null) {
            content.append(line).append("\n");
        }

        return content.toString();
    }

    /**
     * Will fetch all words that are on the page
     * @param pageContent all content on a page
     * @return
     */
    String getPageContent(String pageContent) {
        return "";
    }


    private boolean isValidLink(String link) {
        Pattern p = Pattern.compile("\\/wiki\\/(\\w*\\%*\\(*\\)*\\_*\\-*\\.*)*");
        Matcher m = p.matcher(link);
        return m.find();
    }

}
