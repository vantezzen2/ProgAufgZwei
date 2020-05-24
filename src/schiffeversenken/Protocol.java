package schiffeversenken;

public interface Protocol {
    /**
     * Verschicke Random-Integer, höherer Wert gibt an, wer anfängt.
     * <p>
     * Diese Methode ist erlaubt im Zustand "START".
     * Die Statusmaschine geht danach über in "VERSENKEN_SENDEN" oder "VERSENKEN_EMPFANGEN"
     * oder zurück in "START", sollten die Werte gleich sein.
     *
     * @param num Zufälliger Integer
     * @throws StatusException wenn in falschem Status aufgerufen
     */
    void reihenfolgeWuerfeln(int num) throws StatusException;

    /**
     * Sende Koordinate
     * <p>
     * Diese Methode ist erlaubt im Zustand "VERSENKEN_SENDEN".
     * Die Statusmaschine geht danach über in "BESTAETIGEN_EMPFANGEN".
     *
     * @param zeile  Zeile der Koordinate
     * @param spalte Spalte der Koordinate
     * @throws StatusException wenn in falschem Status aufgerufen
     */
    void koordinate_senden(int zeile, int spalte) throws StatusException;

    /**
     * Empfange Koordinate
     * <p>
     * Diese Methode ist erlaubt im Zustand "VERSENKEN_EMPFANGEN".
     * Die Statusmaschine geht danach über in "BESTAETIGEN_SENDEN".
     *
     * @param zeile  Zeile der Koordinate
     * @param spalte Spalte der Koordinate
     * @throws StatusException wenn in falschem Status aufgerufen
     */
    void koordinate_empfangen(int zeile, int spalte) throws StatusException;

    /**
     * Sende Kapitulation.
     * <p>
     * Diese Methode ist erlaubt im Zustand "VERSENKEN_SENDEN" oder "VERSENKEN_EMPFANGEN".
     * Die Statusmaschine geht danach über in "BEENDEN"
     *
     * @throws StatusException wenn in falschem Status aufgerufen
     */
    void kapitulation() throws StatusException;

    /**
     * Bestätige eine Koordinate.
     * <p>
     * Mögliche Stati:
     * 0 => Treffer
     * 1 => Verfehlt
     * 2 => Versenkt
     * <p>
     * Diese Methode ist erlaubt im Zustand "BESTAETIGEN_EMPFANGEN".
     * Die Statusmaschine geht danach über in "VERSENKEN_EMPFANGEN" bei Status 0 oder 1
     * und bei Status 2, wenn das Spiel nicht verloren wurde, oder geht in "BEENDEN" über bei
     * Status 2, wenn das Spiel verloren wurde
     *
     * @param status Status
     * @throws StatusException
     */
    void bestaetigen_empfangen(int status) throws StatusException;

    /**
     * Sende eine Bestätigung.
     * <p>
     * Mögliche Stati:
     * 0 => Treffer
     * 1 => Verfehlt
     * 2 => Versenkt
     * <p>
     * Diese Methode ist erlaubt im Zustand "BESTAETIGEN_EMPFANGEN".
     * Die Statusmaschine geht danach über in "VERSENKEN_EMPFANGEN" bei Status 0 oder 1
     * und bei Status 2, wenn das Spiel nicht verloren wurde, oder geht in "BEENDEN" über bei
     * Status 2, wenn das Spiel verloren wurde
     *
     * @param status
     * @throws StatusException
     */
    void bestaetigen_senden(int status) throws StatusException;
}
