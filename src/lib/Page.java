package lib;

import java.util.ArrayList;

public class Page {
    private int level = 0;
    private String path = "";
    private ArrayList<Page> pages = new ArrayList<>();

    public Page(String path, int level) {
        this.path = path;
        this.level = level;
    }
}
