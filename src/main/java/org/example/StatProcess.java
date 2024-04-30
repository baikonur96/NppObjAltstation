package org.example;

public class StatProcess implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++){
            System.out.println("---");
            System.out.println("+++");
        }

    }
}
