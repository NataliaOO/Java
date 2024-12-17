package ex02;

import java.util.Arrays;

public class Program {
    public static void main(String[] args) {
        Config config = parseArguments(args);

        int[] array = generatorRandomArray(config.arraySize);
        System.out.println("Sum: " + calculateReferenceSum(array));

        ThreadManager threadManager = new ThreadManager(array,config.threadscount);
        threadManager.runTreads();

        System.out.println("Sum by threads: " + threadManager.getTotalSum());
    }

    private static Config parseArguments(String[] args) {
        if (args.length != 2 || !args[0].startsWith("--arraySize=") || !args[1].startsWith("--threadsCount=")) {
            System.out.println("Usage: java Program --arraySize=<number> --threadsCount=<number>");
            System.exit(1);
        }

        int arraySize, threadsCount;
        try {
            arraySize = Integer.parseInt(args[0].substring("--arraySize=".length()));
            threadsCount = Integer.parseInt(args[1].substring("--threadsCount=".length()));
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format. Please use integers.");
            System.exit(1);
            return null; // Чтобы избежать предупреждений компилятора
        }

        if (arraySize <= 0 || threadsCount <= 0 || threadsCount > arraySize) {
            System.err.println("Invalid input. Ensure: arraySize > 0, threadsCount > 0, and threadsCount <= arraySize.");
            System.exit(1);
        }

        return new Config(arraySize, threadsCount);
    }

    private static int[] generatorRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 2001) - 1000;
        }
        return array;
    }

    private static int calculateReferenceSum(int[] array) {
        int sum = 0;
        for (int num : array) {
            sum += num;
        }
        return sum;
    }
}
