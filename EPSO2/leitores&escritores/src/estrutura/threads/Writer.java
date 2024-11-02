package estrutura.threads;

import estrutura.dados.Data;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.Random;

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
        try {
            lock.writeLock().lock();
            int index;
            for (int i = 0; i < 100; i++) {
                index = random.nextInt(data.getSize());
                data.writeData(index, "MODIFICADO");  // Escrita
            }
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.writeLock().unlock();
        }
    }
}