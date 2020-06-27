package Rechner;

import dataBucket.IntStorageBucket;

public class Rechner implements IRechner {
    private final int status = 0;
    IntStorageBucket history;

    /**
     * Erstelle einen neuen Rechner und stelle evtl. Historien wieder her
     */
    public Rechner() {

    }

    @Override
    public void add(int i) {

    }

    @Override
    public void subtract(int i) {

    }

    @Override
    public void undo() {

    }

    @Override
    public void reset() {

    }

    @Override
    public int get() {
        return 0;
    }
}
