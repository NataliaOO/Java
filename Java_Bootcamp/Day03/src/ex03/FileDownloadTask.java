package ex03;

public class FileDownloadTask {
    private final int id;
    private final String url;

    public FileDownloadTask(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
