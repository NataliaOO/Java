import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Node {
    private File folder;
    private ArrayList<Node> children;
    private long size;
    private int level;
    private long limit;

    public Node(File folder, long limit) {
        this(folder);
        this.limit = limit;
    }

    public Node(File folder) {
        this.folder = folder;
        children = new ArrayList<>();
    }

    private void setLimit( long limit) {
        this.limit = limit;
    }

    public File getFolder() {
        return folder;
    }

    public void addChild(Node child) {
        child.setLevel(level + 1);
        child.setLimit(limit);
        children.add(child);
    }

    private void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String size = SizeCalculator.getHumanReadableSize(getSize());
        builder.append(folder.getName() + " - " + size  + "\n");
        for (Node child : children) {

            if (child.getSize() < limit) {
                continue;
            }
            builder.append("-".repeat(this.level) + child.toString());
        }
        return builder.toString();
    }
}
