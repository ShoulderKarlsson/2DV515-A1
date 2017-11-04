import java.io.File;
import java.io.PrintWriter;
import java.util.HashSet;

class FileHandler {
    private String workingDir = System.getProperty("user.dir");
    private String startPage;

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
            createFolder(pagePath + "/words");
            createFolder(pagePath + "/links");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void addLinksToFile(HashSet<String> links, String originPage) {
        String linksFilePath = createFilePath("links", originPage);
        try {
            File f = new File(linksFilePath);
            f.createNewFile();
            PrintWriter pW = new PrintWriter(linksFilePath, "UTF-8");
            for (String link : links) {
                pW.println(link);
            }
            pW.close();
        } catch (Exception e) {
            System.out.println("addLinksToFileError");
            System.out.println(e);
        }
    }


    void addHTMLToFile(String html, String originPage) {
        String wordsFilePath = createFilePath("words", originPage);
        try {
            File f = new File(wordsFilePath);
            f.createNewFile();
            PrintWriter pw = new PrintWriter(wordsFilePath, "UTF-8");
            pw.print(html);
            pw.close();
        } catch (Exception e) {
            System.out.println("AddHTMLToFileError");
            System.out.println(e);
        }
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
