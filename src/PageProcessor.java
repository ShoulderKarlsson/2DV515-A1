
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PageProcessor {
    HashSet<String> getPageLinks(String page) {
        Pattern p = Pattern.compile("href=\"(.*?)\"");
        Matcher m = p.matcher(page);
        HashSet<String> links = new HashSet<>();
        while (!m.hitEnd()) {
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


    String readPage(String url) {
        StringBuilder content = new StringBuilder();
        try {
            URL wikiPage = new URL(url);
            BufferedReader pageStream = new BufferedReader(new InputStreamReader(wikiPage.openStream()));
            String line;
            while ((line = pageStream.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("readPage error");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return content.toString();
    }


    String getPageContent(String pageContent) {
        return "";
    }


    private boolean isValidLink(String link) {
        Pattern p = Pattern.compile("^\\/wiki\\/[a-zA-z1-9]*\\-*");
        Matcher m = p.matcher(link);
        return m.find();
    }

}
