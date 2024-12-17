package ex01;

public class ObjectThread extends Thread {
    private static final Object lock = new Object();
    private static boolean eggTurn = true;
    private final int count;
    private final String message;
    private final boolean isEgg;

    ObjectThread(int count, String text, boolean isEgg) {
        this.count = count;
        this.message = text;
        this.isEgg = isEgg;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            synchronized (lock) {
                while (eggTurn != isEgg) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                System.out.println(message);

                eggTurn = !eggTurn;
                lock.notifyAll();
            }
        }
    }
}
