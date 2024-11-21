package ex02;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Optional;

public class FileManager {
    private Path currentDir;

    public FileManager(String startDir) {
        this.currentDir = Paths.get(startDir).toAbsolutePath();
        if (!Files.isDirectory(currentDir)) {
            throw new IllegalArgumentException("Invalid starting directory");
        }
    }

    public File[] getDirectoryContents() {
        File folder = currentDir.toFile();
        return folder.listFiles();
    }

    public boolean changeDirectory(String folderName) {
        Path newPath = currentDir.resolve(folderName).normalize();
        if (Files.isDirectory(newPath)) {
            currentDir = newPath;
            return true;
        }
        return false;
    }

    public boolean moveOrRename (String sourceName, String targetName) {
        Path source = currentDir.resolve(sourceName).normalize();
        Path target = currentDir.resolve(targetName).normalize();
        if (Files.isDirectory(target))  target = target.resolve(source.getFileName());

        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String getCurrentDirectory() {
        return currentDir.toString();
    }

    public long calculateSize(File file) {
        if (file.isFile()) {
            return (file.length() + 1023) / 1024;
        }

        return Optional.ofNullable(file.listFiles())
                .map(files ->
                        Arrays.stream(files)
                                .mapToLong(this::calculateSize)
                                .sum()
                )
                .orElse(0L);
    }
}
