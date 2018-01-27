package SempahoreExplained;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class PrinterSemaphore {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(() -> {
            System.out.println("Printing started");
            for (int i = 0; i < 12; i++) {
                Printer.INSTANCE.print(i);
            }
        });
    }
}

enum Printer {

    INSTANCE;
    private Semaphore semaphore = new Semaphore(1);
    public void print(int user) {
        try {
            semaphore.acquire();
            printForAUser(user);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Printing completed for user " + user);
            semaphore.release();
        }
    }

    private void printForAUser(int user) {
        System.out.println("Printing for a user " + user);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}