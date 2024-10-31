package estrutura.threads;

import estrutura.dados.Data;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.Random;

public class Writer implements Runnable {
    private final Data data;
    private final ReentrantLock lock;
    private final Condition canWrite;
    private final SharedControl sharedControl;
    private final Random random = new Random();

    public Writer(Data data, ReentrantLock lock, Condition canWrite, SharedControl sharedControl) {
        this.data = data;
        this.lock = lock;
        this.canWrite = canWrite;
        this.sharedControl = sharedControl;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            while (sharedControl.getReaders() > 0) {
                canWrite.await();
            }
            sharedControl.incrementWriters();
            for (int i = 0; i < 100; i++) {
                int index = random.nextInt(data.getSize());
                data.writeData(index, "MODIFICADO");  // Escrita
            }
            sharedControl.decrementWriters();
            canWrite.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}