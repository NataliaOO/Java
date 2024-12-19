package edu.school21.printer.app;
import edu.school21.printer.logic.ImageConverter;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java Main <white_char> <black_char>");
            System.exit(1);
        }
        char white_char = args[0].charAt(0);
        char black_char = args[1].charAt(0);
        String image_path = "target/resources/image.bmp";

        File imageFile = new File(image_path);
        if (!imageFile.exists() || !imageFile.isFile()) {
            System.err.println("Error: Image file not found or invalid path.");
            System.exit(1);
        }

        try {
            String[] imageResult = ImageConverter.convertImagetoArray(imageFile,white_char,black_char);
            for (String line : imageResult) {
                System.out.println(line);
            }

        } catch (Exception e) {
            System.err.println("Error processing image: " + e.getMessage());
            System.exit(1);
        }
    }
}