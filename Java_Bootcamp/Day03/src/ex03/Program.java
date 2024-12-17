package ex03;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Program {
    private static final String FILE_NAME = "src/ex03/files_urls.txt";

    public static void main(String[] args) {
        int threadsCount = parseThreadsCount(args);
        List<FileDownloadTask> tasks = readTasksFromFile(FILE_NAME);
        if (tasks.isEmpty()) {
            System.out.println("No files to download");
            System.exit(0);
        }
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        for (FileDownloadTask task : tasks) {
            executor.execute(new Downloader(task));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for threads to finish");
        }
        System.out.println("All downloads completed.");
    }

    private static int parseThreadsCount(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--threadsCount=")) {
            System.out.println("Usage: java Program --threadsCount=<number>");
            System.exit(1);
        }

        int threadsCount;
        try {
            threadsCount = Integer.parseInt(args[0].substring("--threadsCount=".length()));
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format for threads count.");
            System.exit(1);
            return -1;
        }

        if (threadsCount <= 0) {
            System.err.println("Threads count must be greater than 0.");
            System.exit(1);
        }

        return threadsCount;
    }

    private static List<FileDownloadTask> readTasksFromFile(String fileName) {
        List<FileDownloadTask> tasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ", 2);
                if (parts.length == 2) {
                    int id = Integer.parseInt(parts[0]);
                    String url = parts[1];
                    tasks.add(new FileDownloadTask(id, url));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }

        return tasks;
    }
}
