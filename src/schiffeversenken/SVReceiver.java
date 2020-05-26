package schiffeversenken;

public interface SVReceiver {
    /**
     * Empfange Random-Integer, höherer Wert gibt an, wer anfängt.
     * <p>
     * Diese Methode ist erlaubt im Zustand "START".
     * Die Statusmaschine geht danach über in "VERSENKEN_SENDEN" oder "VERSENKEN_EMPFANGEN"
     * oder zurück in "START", sollten die Werte gleich sein.
     *
     * @param num Zufälliger Integer
     * @throws StatusException wenn in falschem Status aufgerufen
     */
    void reihenfolgeWuerfeln_empfangen(int num) throws StatusException;

    /**
     * Empfange Koordinate
     * <p>
     * Diese Methode ist erlaubt im Zustand "VERSENKEN_EMPFANGEN".
     * Die Statusmaschine geht danach über in "BESTAETIGEN_SENDEN".
     *
     * @throws StatusException wenn in falschem Status aufgerufen
     */
    void koordinate_empfangen(int x, int y) throws StatusException;

    /**
     * Empfange Kapitulation.
     * <p>
     * Diese Methode ist erlaubt im Zustand "VERSENKEN_SENDEN" oder "VERSENKEN_EMPFANGEN".
     * Die Statusmaschine geht danach über in "BEENDEN"
     *
     * @throws StatusException wenn in falschem Status aufgerufen
     */
    void kapitulation() throws StatusException;

    /**
     * Empfange die Bestätigung einer Koordinate.
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
     * @param status Status der Bestätigung
     * @throws StatusException
     */
    void bestaetigen_empfangen(int status) throws StatusException;
}
