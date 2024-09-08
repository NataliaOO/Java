package ex03;

import java.util.Scanner;

public class Program {
    public static final String EXIT = "42";
    public static final String ILLEGAL_ARGUMENT = "Illegal Argument";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        int week = 1;
        String input;
        while (!(input = scanner.nextLine()).equals(EXIT) && week <= 18) {
            processWeekInput(input, week);
            int minScore = getMinScore(scanner);
            sb.append(minScore);
            scanner.nextLine();  // Очищаем оставшуюся строку
            week++;
        }
        printProgressGraph(sb);
    }

    private static void printProgressGraph(StringBuilder sb) {
        for (int i = 0; i < sb.length(); i++) {
            int minScore = sb.charAt(i) - '0';
            System.out.printf("Week %d ", i + 1);
            System.out.println("=".repeat(minScore) + ">");
        }
    }

    private static int getMinScore(Scanner scanner) {
        int minScore = Integer.MAX_VALUE;
        for (int i = 0; i < 5; i++) {
            int score = scanner.nextInt();
            if (score < 1 || score > 9) {
                exitWithError();
            }
            minScore = Math.min(minScore, score);
        }
        return minScore;
    }

    private static void processWeekInput(String input, int expectedWeek) {
        if (!input.equals("Week " + expectedWeek)) {
            exitWithError();
        }
    }

    private static void exitWithError() {
        System.err.println(ILLEGAL_ARGUMENT);
        System.exit(-1);
    }
}
