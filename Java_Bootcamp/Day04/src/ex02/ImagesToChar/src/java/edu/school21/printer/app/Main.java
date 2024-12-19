package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import edu.school21.printer.logic.ImageConverter;

import java.io.IOException;

@Parameters(separators = "=")
public class Main {
    @Parameter(names = "--white", required = true)
    private static String whiteColor;
    @Parameter(names = "--black", required = true)
    private static String blackColor;

    public static void main(String[] args) throws IOException {
        JCommander.newBuilder()
                .addObject(new Main())
                .build()
                .parse(args);

        ImageConverter image = new ImageConverter(whiteColor, blackColor, "target/resources/image.bmp");
        image.convertAndPrintImage();
    }
}