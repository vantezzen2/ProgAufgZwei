package schiffeversenken.protocol;

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
}
