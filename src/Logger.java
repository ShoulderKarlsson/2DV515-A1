import java.io.IOException;

class Logger {

    static void displayProgress(int current, int total) {
        int progress = Math.floorDiv(current * 100, total);
        System.out.println("Currently done with " + progress + "% of links.");
    }
}
