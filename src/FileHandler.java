import java.io.File;
import java.io.PrintWriter;
import java.util.HashSet;

class FileHandler {
    private String workingDir = System.getProperty("user.dir");
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
            createFolder(pagePath + "/words");
            createFolder(pagePath + "/links");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    int storeContent(String originPage, HashSet<String> links, String html) {
        String linksPath = createFilePath("links", originPage);
        String htmlPath = createFilePath("words", originPage);
        HashSet<String> htmlSet = new HashSet<>();
        htmlSet.add(html);
        if (write(linksPath, links) &&
            write(htmlPath, htmlSet)) {
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
