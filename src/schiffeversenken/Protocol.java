package schiffeversenken;

import java.io.IOException;

public interface Protocol {
    /**
     * Verbinde mit einem Server
     * Erlaubt im Zustand "START".
     * <p>
     * Nach erfolgreichem verbinden geht die Zustandmaschine in "SET_SHIPS" über, sonst wird ConnectionException geworfen
     *
     * @param host Host des Servers
     * @param port Port des Servers
     * @throws IOException     Wenn keine Verbindung hergestellt werden kann
     * @throws StatusException
     */
    void connect(String host, int port) throws IOException, StatusException;

    /**
     * Starte den Server und warte auf einen Partner
     * Erlaubt im Zustand "START"
     * <p>
     * Nach erfolgreichem verbinden geht die Zustandmaschine in "SET_SHIPS" über
     *
     * @param port
     * @throws IOException
     * @throws StatusException
     */
    void serve(int port) throws IOException, StatusException;

    /**
     * Setze ein Schiff auf das eigene Spielfeld.
     * Erlaubt im Zustand "SET_SHIPS"
     * <p>
     * Die Zustandmaschine kann nach dieser Funktion in "SET_SHIPS", "WAIT_FOR_BEGINNER" übergehen
     *
     * @param laenge   Länge des Schiffs
     * @param zeile    Zeile, in dem das Schiff startet
     * @param spalte   Spalte, in dem das Schiff startet
     * @param vertikal Steht das Schiff vertikal (sonst horizontal)
     * @throws StatusException
     * @throws IllegalArgumentException Wenn das Schiff an einem ungültigen Ort platziert wurde
     */
    void set_ship(int laenge, int zeile, int spalte, boolean vertikal) throws StatusException, IllegalArgumentException;

    /**
     * Wähle den Anfangenden zufällig und sende das Ergebnis an den Client
     * Erlaubt im Zustand "WAIT_FOR_BEGINNER" und nur im Server-Modus
     * <p>
     * Die Zustandmaschine geht nach dieser Funktion in "ACTIVE" oder "PASSIVE" über
     *
     * @throws StatusException
     * @throws IOException
     */
    void select_beginner() throws StatusException, IOException;

    /**
     * Warte auf die Entscheidung des Servers, wer anfängt
     * Erlaubt im Zustand "WAIT_FOR_BEGINNER" und nur im Client-Modus
     * <p>
     * Die Zustandmaschine geht nach dieser Funktion in "ACTIVE" oder "PASSIVE" über
     *
     * @throws StatusException
     * @throws IOException
     */
    void wait_for_descision() throws StatusException, IOException;

    /**
     * Schieße auf ein Gegner-Schiff
     * Erlaubt im Zustand "ACTIVE"
     * <p>
     * Die Zustandmaschine geht nach dieser Funktion in "PASSIVE" oder "GAME_END" über
     * <p>
     * Diese Funktion meldet das Ergebnis des Schusses zurück:
     * 0 = Kein Treffer
     * 1 = Treffer
     * 2 = Treffer versenkt
     *
     * @param zeile
     * @param spalte
     * @return Ergebnis des Schusses
     * @throws StatusException
     * @throws IOException
     * @throws IllegalArgumentException
     */
    int shoot(int zeile, int spalte) throws StatusException, IOException, IllegalArgumentException;

    /**
     * Warte auf den Schuss des Gegners
     * Erlaubt im Zustand "PASSIVE"
     * <p>
     * Die Zustandmaschine geht nach dieser Funktion in "ACTIVE" oder "GAME_END" über
     *
     * @throws StatusException
     * @throws IOException
     */
    void receive_shot() throws StatusException, IOException;

    /**
     * Wähle, ob es zu einem erneuten Spiel kommen soll
     * Erlaubt in "GAME_END"
     * <p>
     * Die Zustandmaschine geht nach dieser Funktion in "NO_REMATCH" oder "START" über
     *
     * @param rematch
     * @throws StatusException
     * @throws IOException
     */
    void choose_to_rematch(boolean rematch) throws StatusException, IOException;
}
