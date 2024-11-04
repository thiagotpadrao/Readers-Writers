package estrutura;

import estrutura.threads.*;
import estrutura.dados.Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    
    public static int numReaders;
    public static int numWriters;
    public static void main(String[] args) {
        Data data = new Data("bd.txt");
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        boolean sameTimeReading = false;
        for (int i = 0; i <= 100; i++) {
            numReaders = i;
            numWriters = 100 - i;
    
            long avgTime = 0;
            long totalTime = 0;
            for (int j = 0; j < 50; j++) {
                List<Thread> threads = new ArrayList<>(); // Reinicie a lista para cada execução
                populateThreadsArray(threads, data, lock, numReaders, numWriters, sameTimeReading);
    
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
                totalTime += endTime - startTime;
            }
            avgTime = totalTime / 50;
            data.logTime(i, avgTime);
        }
    }
    
    private static void populateThreadsArray(List<Thread> threads, Data data, ReentrantReadWriteLock lock, int numReaders, int numWriters, boolean sameTimeReading) {
        
        if (sameTimeReading) {
            for (int i = 0; i < numReaders; i++) {
                threads.add(new Thread(new Reader(data, lock)));
            }
        } else {
            for (int i = 0; i < numReaders; i++) {
                threads.add(new Thread(new LockReader(data, lock)));
            }
        }

        for (int i = 0; i < numWriters; i++) {
            threads.add(new Thread(new Writer(data, lock)));
        }
    
        Collections.shuffle(threads); // Shuffle the list of threads
    }
    
}