import java.io.IOException;

class Logger {

    static void displayLevelProgress(int current, int total, int level) {
        int progress = Math.floorDiv(current * 100, total);
        System.out.println("Currently done with " + progress + "% of links on level " + level);
    }

    static void displayLevelInformation(int levelAmount, int level) {
        displayBar();
        System.out.println("Starting on level " + level);
        System.out.println("Pages on current level is: " + levelAmount);
    }

    static void displayInformatinoWhenDone(int totalAmount, String category) {
        System.out.println("Done with " + category);
        System.out.println("Saved " + totalAmount + " pages.");
    }

    static void displayDoneWithLevelInformaiton(int amount, int level) {
        System.out.println("Done with level " + level);
        System.out.println("Have stored a total of " + amount + " pages so far.");
        displayBar();
    }

    private static void displayBar() {
        System.out.println("==================================");
    }
}
