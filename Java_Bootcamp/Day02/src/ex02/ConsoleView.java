package ex02;


import java.io.File;

public class ConsoleView {
    public void showCurrentDirectory(String path) {
        System.out.println(path);
    }

    public void showDirectoryContents(File[] files, FileManager fileManager) {
        if (files == null || files.length == 0) {
            System.out.println("Directory is empty.");
            return;
        }
        for (File file : files) {
            long sizeInKB = fileManager.calculateSize(file);
            System.out.printf("%s %d KB%n", file.getName(), sizeInKB);
        }
    }

    public void showCommandPrompt() {
        System.out.println("-> ");
    }

    public void showErrorMessage(String message) {
        System.out.println("Error: " + message);
    }

    public void showSuccessMessage(String message) {
        System.out.println(message);
    }
}
