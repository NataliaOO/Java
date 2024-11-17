package ex05;

public class Program {
    public static void main(String[] args) {
        boolean isDevMode = args.length > 0 && "--profile=dev".equals(args[0]);
        Menu menu = new Menu(new TransactionsService(), isDevMode);
        menu.run();
    }
}
