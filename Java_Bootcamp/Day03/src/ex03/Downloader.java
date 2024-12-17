package ex03;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Downloader implements Runnable {
    private final FileDownloadTask task;

    public Downloader(FileDownloadTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        System.out.printf("%s start download file number %d%n",
                Thread.currentThread().getName().toUpperCase(), task.getId());
        try(InputStream in = new URL(task.getUrl()).openStream();
            FileOutputStream fos = new FileOutputStream("file" + task.getId())) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            System.out.printf("%s finish download file number %d%n",
                    Thread.currentThread().getName().toUpperCase(), task.getId());

        } catch (IOException e) {
            System.err.printf("Thread-%s failed to download file number %d: %s%n",
                    Thread.currentThread().getName(), task.getId(), e.getMessage());
        }
    }
}
