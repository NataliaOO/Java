import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {

        ParametersBag bag = new ParametersBag(args);

        File file = new File(bag.getPath());
        Node root = new Node(file, bag.getLimit());

        FolderSizeCalculator calculator = new FolderSizeCalculator(root);
        new ForkJoinPool().invoke(calculator);
        System.out.println(root);
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
