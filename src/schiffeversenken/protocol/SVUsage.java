package schiffeversenken.protocol;

import schiffeversenken.Board.Board;
import schiffeversenken.StatusException;

import java.io.IOException;

public interface SVUsage {
    /**
     * Stelle heraus, wer statet
     *
     * @throws StatusException
     * @throws IOException
     */
    void doDice() throws StatusException, IOException;

    /**
     * Finde heraus, ob der Spieler am Spielzug ist
     *
     * @return true falls Spieler aktiv
     */
    boolean isActive();

    /**
     * Werfe eine Bombe an den Gegner
     *
     * @param zeile
     * @param spalte
     * @throws StatusException
     * @throws IOException
     */
    void bombe_werfen(int zeile, int spalte) throws StatusException, IOException;

    /**
     * Hole das aktuelle, eigene Board Objekt
     *
     * @return Eigenes Board
     */
    Board getOwnBoard();

    /**
     * Hole das aktuelle, fremde Board Objekt
     *
     * @return Fremdes Board
     */
    Board getOtherBoard();
}
