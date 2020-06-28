package Rechner;

/**
 * Einfache Rechnenmaschine mit Verlaufshistorie
 */
public interface IRechner {
    /**
     * Addiere eine Zahl zum aktuellen Status
     *
     * @param i Zahl, die addiert werden soll
     */
    void add(int i);

    /**
     * Subtrahiere eine Zahl vom aktuellen Status
     *
     * @param i Zahl, die subtrahiert werden soll
     */
    void subtract(int i);

    /**
     * Mache die letzte Statusänderung rückgängig
     */
    void undo() throws IllegalAccessException;

    /**
     * Setzte den Status zurück auf 0. Dies behält die Statushistorie bei
     */
    void reset();

    /**
     * Hole den aktuellen Status
     *
     * @return Status
     */
    int get();
}
