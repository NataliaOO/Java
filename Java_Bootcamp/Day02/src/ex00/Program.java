package ex00;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Program {

    private static final String RESULT_FILE_PATH = "src/ex00/result.txt";
    private static final Map<String, String> FILE_SIGNATURES = new HashMap<>();

    static {
        FILE_SIGNATURES.put("89504E47", "PNG");
        FILE_SIGNATURES.put("FFD8FFE0","JPEG (EXIF)");
        FILE_SIGNATURES.put("FFD8FFE1", "JPEG (JFIF)");
        FILE_SIGNATURES.put("25504446", "PDF");
        FILE_SIGNATURES.put("504B0304", "ZIP");
        FILE_SIGNATURES.put("504B0506", "ZIP (EMPTY)");
        FILE_SIGNATURES.put("504B0708", "ZIP (SPANNED)");
        FILE_SIGNATURES.put("52617221", "RAR");
        FILE_SIGNATURES.put("7F454C46", "ELF");
        FILE_SIGNATURES.put("49443303", "MP3");
        FILE_SIGNATURES.put("47494638", "GIF");
        FILE_SIGNATURES.put("000001BA", "MPEG");
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02X", b));
        }
        return hex.toString();
    }

    private static String validateSignature(String hexSignature) {
        return FILE_SIGNATURES.entrySet().stream()
                .filter(entry -> hexSignature.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    private static void writeToFile(String content) {
        File outputFile = new File(RESULT_FILE_PATH);
        try (FileOutputStream out = new FileOutputStream(outputFile, true)) {
            out.write(content.concat("\n").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void identifyFileType(String fileName) {
        File file = new File(fileName);
        if (!file.exists() || !file.isFile()) {
            System.out.println("File not found: " + fileName);
            return;
        }

        try (FileInputStream fis = new FileInputStream(fileName)) {
            byte[] signature = new byte[4];
            fis.read(signature);
            String hexSignature = bytesToHex(signature);
            String fileType = validateSignature(hexSignature);

            if (fileType != null) {
                writeToFile(fileType);
                System.out.println("PROCESSED");
            } else System.out.println("UNDEFINED");

        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }



    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("-> ");
                String input = scanner.nextLine();

                if (input.equals("42")) {
                    break;
                }
                identifyFileType(input);
            }
        }
    }
}
