package edu.school21.printer.logic;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConverter {

    private final Attribute white;
    private final Attribute black;
    private final BufferedImage image;

    public ImageConverter(String whiteColor, String blackColor, String s) throws IOException {
        this.white = toAttribute(whiteColor);
        this.black = toAttribute(blackColor);
        this.image = ImageIO.read(new File(s));
    }

    public void convertAndPrintImage() {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                if (pixel == Color.WHITE.getRGB()) {
                    System.out.print(Ansi.colorize(" ", white));
                } else {
                    System.out.print(Ansi.colorize(" ", black));
                }
            }
            System.out.println();
        }
    }

    private Attribute toAttribute(String attribute) {
        return switch (attribute.toLowerCase()) {
            case "black" -> Attribute.BLACK_BACK();
            case "green" -> Attribute.GREEN_BACK();
            case "blue" -> Attribute.BLUE_BACK();
            case "red" -> Attribute.RED_BACK();
            case "yellow" -> Attribute.YELLOW_BACK();
            case "magenta" -> Attribute.MAGENTA_BACK();
            case "cyan" -> Attribute.CYAN_BACK();
            default -> Attribute.WHITE_BACK();
        };
    }
}
