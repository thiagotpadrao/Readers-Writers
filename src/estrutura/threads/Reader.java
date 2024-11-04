package estrutura.threads;

import estrutura.dados.Data;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.Random;

public class Reader implements Runnable {
    private final Data data;
    private final ReentrantReadWriteLock lock;
    private final Random random = new Random();

    public Reader(Data data, ReentrantReadWriteLock lock) {
        this.data = data;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            lock.readLock().lock();
            for (int i = 0; i < 100; i++) {
                int index = random.nextInt(data.getSize());
                String word = data.readData(index);  // Leitura
            }
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.readLock().unlock();
        }
    }
}