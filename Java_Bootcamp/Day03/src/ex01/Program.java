package ex01;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--count=")) {
            System.out.println("Usage: java Program --count=<number>");
            System.exit(1);
        }

        int count = 0;
        try {
            count = Integer.parseInt(args[0].substring("--count=".length()));
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format. Please use an integer.");
            System.exit(1);
        }

        ObjectThread eggThread = new ObjectThread(count, "Egg", true);
        ObjectThread henThread = new ObjectThread(count, "Hen", false);
        
        eggThread.start();
        henThread.start();
        
        try {
            eggThread.join();
            henThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
