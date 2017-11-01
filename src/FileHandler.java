import java.io.File;
import java.util.ArrayList;

class FileHandler {

    /**
     * Creates the folder structure for a start page.
     * @param page - A start page for a scrape
     */
    void createBaseStructureFor(String page) {
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

    void addLinksToFile(ArrayList<String> links, String originPage) {
        throw new Error("IMPLEMENT THIS. it should store all the links fetched from a wikipedia page and store it to a file with the same name as the page it was fetched from.");
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
}
