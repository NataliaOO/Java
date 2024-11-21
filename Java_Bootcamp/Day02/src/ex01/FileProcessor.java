package ex01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessor {
    private static final long MAX_FILE_SIZE = 10L * 1024 * 10; //10MB

    public static String readFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.length() > MAX_FILE_SIZE) {
            throw new IOException("File exceeds the maximum allowed size of 10MB: " + filePath);
        }
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(" ");
            }
        }
        return content.toString();
    }
}
