package estrutura;

import estrutura.threads.Reader;
import estrutura.threads.Writer;
import estrutura.threads.SharedControl;
import estrutura.dados.Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Main {
    
    public static final int numReaders = 5;
    public static final int numWriters = 5;
    
    public static void main(String[] args) {
        Data data = new Data("bd.txt");
        ReentrantLock lock = new ReentrantLock();
        Condition canWrite = lock.newCondition();
        SharedControl sharedControl = new SharedControl();

        List<Thread> threads = new ArrayList<>();
        populateThreadsArray(threads, data, lock, canWrite, sharedControl, numReaders, numWriters);

        long startTime = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Tempo total de execução: " + (endTime - startTime) + " ms");

        data.logData("logFile.txt");
    }

    private static void populateThreadsArray(List<Thread> threads, Data data, ReentrantLock lock, Condition canWrite, SharedControl sharedControl, int numReaders, int numWriters) {
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < numReaders; i++) {
            threadList.add(new Thread(new Reader(data, lock, canWrite, sharedControl)));
        }

        for (int i = 0; i < numWriters; i++) {
            threadList.add(new Thread(new Writer(data, lock, canWrite, sharedControl)));
        }

        Collections.shuffle(threadList);
        threads.addAll(threadList);
    }
}