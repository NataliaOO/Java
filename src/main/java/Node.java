import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Node {
    private File folder;
    private ArrayList<Node> children;
    private long size;

    public Node(File folder) {
        this.folder = folder;
        children = new ArrayList<>();
    }

    public File getFolder() {
        return folder;
    }

    public void addChild(Node child) {
        children.add(child);
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
        //String size = SizeCalculator.getHumanReadableSize(getSize());
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
