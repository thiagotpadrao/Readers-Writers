package estrutura.threads;

import estrutura.dados.Data;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.Random;

public class Reader implements Runnable {
    private final Data data;
    private final ReentrantLock lock;
    private final Condition canWrite;
    private final SharedControl sharedControl;
    private final Random random = new Random();

    public Reader(Data data, ReentrantLock lock, Condition canWrite, SharedControl sharedControl) {
        this.data = data;
        this.lock = lock;
        this.canWrite = canWrite;
        this.sharedControl = sharedControl;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            sharedControl.incrementReaders();
            for (int i = 0; i < 100; i++) {
                int index = random.nextInt(data.getSize());
                String word = data.readData(index);  // Leitura
            }
            sharedControl.decrementReaders();
            if (sharedControl.getReaders() == 0) {
                canWrite.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}