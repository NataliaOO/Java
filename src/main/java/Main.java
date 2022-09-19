import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.ForkJoinPool;

public class Main {

    private static final String folderPath = "C:\\Users\\nuary\\Documents\\НАСТОЛКИ";
    public static void main(String[] args) {
        File file = new File(folderPath);

        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        System.out.println(new ForkJoinPool().invoke(calculator));
    }

    public static long getFolderSize(File folder) {
        if (folder.isFile()) {
            return folder.length();
        }
        long sum = 0;
        File[] files = folder.listFiles();
        for (File file : files) {
            sum += getFolderSize(file);
        }
        return sum;
    }
}
