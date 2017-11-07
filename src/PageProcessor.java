import net.htmlparser.jericho.*;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PageProcessor {

    /**
     * Accepts Used to extract all the links from a page.
     * @param page rawHtml page
     * @return set of links
     */
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


    private boolean isValidLink(String link) {
        Pattern p = Pattern.compile("^\\/wiki\\/[a-zA-z1-9]*\\-*");
        Matcher m = p.matcher(link);
        return m.find();
    }

    /**
     * Removes everything that is HTML on the page.
     * @param html page in raw HTML
     * @return cleaned page
     */
    String cleanHTMLContent(String html) {
        Renderer rend = new Renderer(new Source(html));
        return HTMLContentCleanerHelper(rend.toString());
    }

    /**
     * jericho sometimes misses some tags
     * performing some additional cleaning
     * reference: https://drive.google.com/open?id=0Bz0K0fRgZPH_MEc5OGt1Mlc5QTA
     * @param content page content
     * @return String
     */
    private String HTMLContentCleanerHelper(String content) {
        return content
                .toLowerCase()
                .replaceAll("\r", " ")
                .replaceAll("\n", " ")
                .replaceAll("\\<.*?>", "")
                .replaceAll("\\[.*?\\]", "")
                .replaceAll("\\d{4}-\\d{2}-\\d{2}", "")
                .replaceAll("\\.", "")
                .replaceAll(", ", "")
                .replaceAll("\\?", "")
                .replaceAll(";", "")
                .replaceAll("\"", "")
                .replaceAll(":", "")
                .replaceAll("\\(", "")
                .replaceAll("\\*", "")
                .replaceAll("_", "")
                .replaceAll("#", "")
                .replaceAll("\\)", "");
    }

    /**
     * Finds all the words in a string.
     * @param sequence
     * @return
     */
    String findWords(String sequence) {
        Pattern p = Pattern.compile("[a-zA-Z]+(-[a-zA-Z]+)*");
        Matcher m = p.matcher(sequence);
        StringBuilder sb = new StringBuilder();
        while(m.find()) {
            sb.append(m.group(0)).append(" ");
        }

        return sb.toString();
    }

}
