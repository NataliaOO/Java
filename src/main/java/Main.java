import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {

    private static final String FOLDER_PATH = "C:\\Users\\nuary\\Documents\\НАСТОЛКИ";

    public static void main(String[] args) {

        File file = new File(FOLDER_PATH);
        Node root = new Node(file);

        FolderSizeCalculator calculator = new FolderSizeCalculator(root);
        new ForkJoinPool().invoke(calculator);
        System.out.println(root.getSize());
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
