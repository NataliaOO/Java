import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderSizeCalculator  extends RecursiveTask<Long> {

    private File folder;

    public FolderSizeCalculator(File folder) {
        this.folder = folder;
    }

    @Override
    protected Long compute() {

        if (folder.isFile()) {
            return folder.length();
        }

        long sum = 0;
        List<FolderSizeCalculator> subTask = new LinkedList<>();
        File[] files = folder.listFiles();
        for (File file: files) {
            FolderSizeCalculator task = new FolderSizeCalculator(file);
            task.fork();
            subTask.add(task);
        }

        for (FolderSizeCalculator task : subTask) {
            sum += task.join();
        }

        return sum;
    }

    //TODO: 24B, 234Kb, 36Mb, 34Mb, 42Tb
    public String getHumanReadableSize(long size) {
        return "";
    }

    //TODO: 24B, 234Kb, 36Mb, 34Mb, 42Tb
    // 24B, 234K, 36M, 34G, 42T
    // 236K => 240640
    public long getSizeFromHumanReadable(String size) {
        return 0;
    }
}
