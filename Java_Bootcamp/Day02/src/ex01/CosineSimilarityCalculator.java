package ex01;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CosineSimilarityCalculator {
    public TreeMap<String, Integer> buildFrequencyVector(Set<String> dictionary, String text) {
        TreeMap<String, Integer> frequencyVector = new TreeMap<>();
        for (String word : dictionary) {
            frequencyVector.put(word, 0);
        }
        for (String word : text.split(" ")) {
            if (frequencyVector.containsKey(word)) {
                frequencyVector.put(word, frequencyVector.get(word) + 1);
            }
        }
        return frequencyVector;
    }

    public double calculateSimilarity(Map<String, Integer> vectorA, Map<String, Integer> vectorB) {
        double numerator = 0;
        double normA = 0;
        double normB = 0;

        for (String word : vectorA.keySet()) {
            int freqA = vectorA.get(word);
            int freqB = vectorB.get(word);

            numerator += freqA * freqB;
            normA += freqA * freqA;
            normB += freqB * freqB;
        }
        return numerator / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
