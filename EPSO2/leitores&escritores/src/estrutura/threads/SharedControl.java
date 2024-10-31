package estrutura.threads;

public class SharedControl {
    private int readers = 0;
    private int writers = 0;

    public synchronized void incrementReaders() {
        readers++;
    }

    public synchronized void decrementReaders() {
        readers--;
    }

    public synchronized int getReaders() {
        return readers;
    }

    public synchronized void incrementWriters() {
        writers++;
    }

    public synchronized void decrementWriters() {
        writers--;
    }

    public synchronized int getWriters() {
        return writers;
    }
}