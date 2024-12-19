package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConverter {
    public static String[] convertImagetoArray(File imageFile, char whiteChar, char blackChar) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        if (image == null) {
            throw new IOException("Invalid image format. Only BMP is supported.");
        }
        int width = image.getWidth();
        int height = image.getHeight();
        String[] array = new String[height];
        for (int y = 0; y < height; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < width; x++) {
                int color = image.getRGB(x, y) & 0xFFFFFF;
                if (color == 0xFFFFFF) {
                    line.append(whiteChar);
                } else if (color == 0x000000) {
                    line.append(blackChar);
                } else throw new IOException("Unsupported pixel color in image (must be black or white only).");
            }
            array[y] = line.toString();
        }
        return array;
    }

}
