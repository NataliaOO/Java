package ex01;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Program {
    private static final String DICTIONARY_FILE = "src/ex01/dictionary.txt";

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Program <inputFileA> <inputFileB>");
            System.exit(1);
        }

        String intputFileA = args[0];
        String intputFileB = args[1];

        try {
            String textA = FileProcessor.readFile(intputFileA);
            String textB = FileProcessor.readFile(intputFileB);

            DictionaryManager dictionaryManager = new DictionaryManager();
            dictionaryManager.buildDictionary(textA, textB);
            Set<String> dictionary = dictionaryManager.getDictionary();
            dictionaryManager.saveToFile(DICTIONARY_FILE);

            CosineSimilarityCalculator calculator = new CosineSimilarityCalculator();
            TreeMap<String, Integer> freqVectorA =  calculator.buildFrequencyVector(dictionary, textA);
            TreeMap<String, Integer> freqVectorB = calculator.buildFrequencyVector(dictionary, textB);

            double similarity = calculator.calculateSimilarity(freqVectorA, freqVectorB);
            System.out.printf("Similarity = %.3f%n", similarity);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
