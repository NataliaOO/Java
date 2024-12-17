package ex00;

public class ObjectThread extends Thread {
    private final int count;
    private final String info;

    ObjectThread(int count, String text) {
        this.count = count;
        this.info = text;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println(info);
        }
    }
}
