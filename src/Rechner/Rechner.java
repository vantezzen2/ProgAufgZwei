package Rechner;

import dataBucket.IntStorageBucket;
import persistence.Filesystem;
import persistence.FilesystemException;

import java.io.File;

public class Rechner implements IRechner {
    IntStorageBucket history;

    private final String historyFile = "./history.txt";
    private final Filesystem fs;

    /**
     * Erstelle einen neuen Rechner und stelle evtl. Historien wieder her
     */
    public Rechner() {
        File f = new File(this.historyFile);

        this.history = new IntStorageBucket();
        fs = new Filesystem();

        if (f.exists()) {
            try {
                fs.readBucket(this.historyFile, this.history);
            } catch (FilesystemException e) {
                System.err.println("Error: Kann history nicht wiederherstellen.");
                e.printStackTrace();
            }
        } else {
            history.add(0);
        }
    }

    private void saveHistory() {
        try {
            fs.writeBucket(
                    this.history,
                    this.historyFile
            );
        } catch (FilesystemException e) {
            System.err.println("Error: Kann history nicht speichern.");
            e.printStackTrace();
        }
    }

    @Override
    public void add(int i) {
        history.add(this.get() + i);
        this.saveHistory();
    }

    @Override
    public void subtract(int i) {
        history.add(this.get() - i);
        this.saveHistory();
    }

    @Override
    public void undo() throws IllegalAccessException {
        if (history.size() <= 1) {
            throw new IllegalAccessException("History ist leer");
        }

        history.remove(history.size() - 1);
        this.saveHistory();
    }

    @Override
    public void reset() {
        history.add(0);
        this.saveHistory();
    }

    @Override
    public int get() {
        if (history.size() == 0) {
            return 0;
        }

        return history.get(history.size() - 1);
    }
}
