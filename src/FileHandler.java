import java.io.File;
import java.io.PrintWriter;
import java.util.HashSet;

class FileHandler {
    private final String workingDir = System.getProperty("user.dir");
    private final String WORDS = "/words";
    private final String RAW_HTML = "/rawHtml";
    private final String LINKS = "/links";
    private final String NO_HTML = "/noHtml";
    private String startPage;
    private int amountWritten = 0;


    FileHandler(String startPage) {
        this.startPage = startPage;
        createBaseStructure();
    }

    /**
     * Creates the folder structure for a start page.
     */
    void createBaseStructure() {
        try {
            String dataFolderPath = createFolder(workingDir + "/data");
            String pagePath = createFolder(dataFolderPath + "/" + startPage);
            createFolder(pagePath + WORDS);
            createFolder(pagePath + RAW_HTML);
            createFolder(pagePath + LINKS);
            createFolder(pagePath + NO_HTML);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    int storeContent(String originPage, HashSet<String> links, String html, String noHtml, String onlyWords) {
        String linksPath = createFilePath(LINKS, originPage);
        String rawHtmlPath = createFilePath(RAW_HTML, originPage);
        String noHtmlPath = createFilePath(NO_HTML, originPage);
        String wordsPath = createFilePath(WORDS, originPage);

        if (write(linksPath, links) &&
            write(rawHtmlPath, html) &&
            write(noHtmlPath, noHtml)) {
            amountWritten++;
        }

        return amountWritten;
    }

    private boolean write(String path, HashSet<String> content) {
        try {
            File f = new File(path);
            if (f.createNewFile()) {
                PrintWriter pw = new PrintWriter(path, "UTF-8");
                content.forEach(pw::println);
                pw.close();
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return true;
    }

    /**
     * Overloading and creating content to the format
     * that I've specified for my writer method.
     * @param path to the file to be written
     * @param content content of the file
     * @return success of fail for written file
     */
    private boolean write(String path, String content) {
        HashSet<String> s = new HashSet<>();
        s.add(content);
        return write(path, s);
    }

    /**
     * Helper method that creates a folder if it is not existing
     * @param path path for the folder to be created at
     * @return String that represents the path for the folder that was created.
     */
    private String createFolder(String path) {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        return f.getAbsolutePath();
    }


    private String createFilePath(String section, String originPage) {
        return workingDir + "/data/" + startPage + "/" + section + "/" + originPage + ".txt";
    }
}
