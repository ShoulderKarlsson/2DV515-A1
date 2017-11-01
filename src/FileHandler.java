import java.io.File;

public class FileHandler {

    /**
     * Creates the folder structure for a start page.
     * @param page - A start page for a scrape
     */
    public void createBaseStructureFor(String page) {
        String workingDir = System.getProperty("user.dir");
        try {
            String basePath = createFolder(workingDir + "/data");
            String pagePath = createFolder(basePath + "/" + page);
            createFolder(pagePath + "/words");
            createFolder(pagePath + "/links");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String createFolder(String path) {
        File f = new File(path);

        if (!f.exists()) {
            f.mkdir();
        }

        return f.getAbsolutePath();
    }
}
