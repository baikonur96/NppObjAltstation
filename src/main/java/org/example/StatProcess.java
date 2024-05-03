package org.example;

public class StatProcess implements Runnable{

    int i;
    int total;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public StatProcess(int i, int total) {
        this.i = i;
        this.total = total;
    }

    public static void updateProgressBar(int done, int total) {
        String format = "\r%3d%% %s %c";
        String icon = "=";
        int width = 50;
        int progress = (done * width) / total;
        String bar = new String(new char[progress]).replace('\0', icon.charAt(0)) +
                new String(new char[width - progress]).replace('\0', ' ');
        System.out.printf(format, (done * 100) / total, bar, '|');
    }


    @Override
    public void run() {
        for (int y = 0; y <= total; y++) {
            updateProgressBar(i, total);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
