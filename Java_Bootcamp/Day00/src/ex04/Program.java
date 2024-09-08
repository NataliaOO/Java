package ex04;

import java.util.*;

public class Program {
    public static final String ILLEGAL_ARGUMENT = "Illegal Argument: Maximum frequency of any character is 999.";
    public static final String EMPTY_INPUT_ERROR = "Input cannot be empty or only contain whitespace.";
    public static final int CHAR_ARRAY_SIZE = 65536;
    public static final int MAX_HEIGHT = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = getInput(scanner);

        int[] frequencyArray = calculateFrequencies(input);
        List<Map.Entry<Character, Integer>> frequencyList = getSortedFrequencyList(frequencyArray);

        int topCount = Math.min(MAX_HEIGHT, frequencyList.size());
        int maxFrequency = frequencyList.getFirst().getValue();
        int[] scaledFrequencies = scaleFrequencies(frequencyList, maxFrequency, topCount);
        printHistogram(scaledFrequencies, frequencyList);
    }

    private static String getInput(Scanner scanner) {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.err.println(EMPTY_INPUT_ERROR);
            System.exit(-1);
        }
        return input;
    }

    private static int[] calculateFrequencies(String input) {
        int[] frequencyArray = new int[CHAR_ARRAY_SIZE];
        for (char ch : input.toCharArray()) {
            frequencyArray[ch]++;
            if (frequencyArray[ch] > 999) {
                System.err.println(ILLEGAL_ARGUMENT);
                System.exit(-1);
            }
        }
        return frequencyArray;
    }

    private static List<Map.Entry<Character, Integer>> getSortedFrequencyList(int[] frequencyArray) {
        List<Map.Entry<Character, Integer>> frequencyList = new ArrayList<>();
        for (int i = 0; i < CHAR_ARRAY_SIZE; i++) {
            if (frequencyArray[i] > 0) {
                frequencyList.add(new AbstractMap.SimpleEntry<>((char) i, frequencyArray[i]));
            }
        }
        frequencyList.sort((e1, e2) -> {
            int freqCompare = e2.getValue().compareTo(e1.getValue());
            if (freqCompare != 0) {
                return freqCompare;
            } else {
                return Character.compare(e1.getKey(), e2.getKey());
            }
        });
        return frequencyList;
    }

    private static int[] scaleFrequencies(List<Map.Entry<Character, Integer>> frequencyList, int maxFrequency, int topCount) {
        int[] scaledFrequencies = new int[topCount];
        for (int i = 0; i < topCount; i++) {
            scaledFrequencies[i] = (frequencyList.get(i).getValue() * MAX_HEIGHT) / maxFrequency;
        }
        return scaledFrequencies;
    }

    private static void printHistogram(int[] scaledFrequencies, List<Map.Entry<Character, Integer>> frequencyList) {
        int k = 0;
        while (scaledFrequencies.length > k && scaledFrequencies[k] == MAX_HEIGHT) {
            System.out.printf("%3d ", frequencyList.getFirst().getValue());
            k++;
        }
        System.out.println();
        for (int i = MAX_HEIGHT; i > 0; i--) {
            for (int j = 0; j < scaledFrequencies.length; j++) {
                if (scaledFrequencies[j] >= i) {
                    System.out.print("  # ");
                } else if (scaledFrequencies[j] == i - 1) {
                    System.out.printf("%3d ", frequencyList.get(j).getValue());
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println();
        }

        for (int i = 0; i < scaledFrequencies.length; i++) {
            System.out.printf("  %c ", frequencyList.get(i).getKey());
        }
    }
}
