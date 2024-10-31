package estrutura.threads;

import estrutura.dados.Data;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Writer implements Runnable {
    private final Data data;
    private final ReentrantReadWriteLock lock;
    private final Random random = new Random();

    public Writer(Data data, ReentrantReadWriteLock lock) {
        this.data = data;
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.writeLock().lock();
        try {
            for (int i = 0; i < 100; i++) {
                int index = random.nextInt(data.getSize());
                data.writeData(index, "MODIFICADO");  // Escrita
            }
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
