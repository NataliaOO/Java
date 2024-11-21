package ex02;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1 && !args[0].startsWith("--current-folder=")) {
            System.out.println("Usage: java Program --current-folder=ABSOLUTE_PATH");
            System.exit(0);
        }

        String startDirection = args[0].substring("--current-folder=".length());
        ConsoleView view = new ConsoleView();
        FileManagerController controller = new FileManagerController(new FileManager(startDirection), view);

        Scanner scanner = new Scanner(System.in);
        controller.showCurrentDirectory();
        while (true) {
            view.showCommandPrompt();
            String input = scanner.nextLine();
            controller.processCommand(input);
        }
    }
}
