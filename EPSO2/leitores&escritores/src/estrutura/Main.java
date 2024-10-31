package estrutura;

import estrutura.threads.Reader;
import estrutura.threads.Writer;
import estrutura.dados.Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    public static void main(String[] args) {
        // Inicializa a estrutura de dados e carrega o arquivo
        Data data = new Data("bd.txt");
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        
        // Popula o array de threads de maneira aleatória com Readers e Writers
        List<Thread> threads = new ArrayList<>();
        populateThreadsArray(threads, data, lock, 10, 10);

        // Marca o início da contagem do tempo
        long startTime = System.currentTimeMillis();
        
        // Executa cada thread no array
        for (Thread thread : threads) {
            thread.start();
        }

        // Espera todas as threads terminarem
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Marca o fim da contagem do tempo
        long endTime = System.currentTimeMillis();
        System.out.println("Tempo total de execução: " + (endTime - startTime) + " ms");

        //printa o array
        data.logData("logFile.txt");
    }

    private static void populateThreadsArray(List<Thread> threads, Data data, ReentrantReadWriteLock lock, int numReaders, int numWriters) {
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < numReaders; i++) {
            threadList.add(new Thread(new Reader(data, lock)));
        }

        for (int i = 0; i < numWriters; i++) {
            threadList.add(new Thread(new Writer(data, lock)));
        }

        // Embaralha a lista para garantir a aleatoriedade
        Collections.shuffle(threadList);
        threads.addAll(threadList);
    }
}
