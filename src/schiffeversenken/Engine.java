package schiffeversenken;

import schiffeversenken.Board.Board;
import schiffeversenken.Board.BoardStatus;
import schiffeversenken.protocol.SVReceiver;
import schiffeversenken.protocol.SVSender;
import schiffeversenken.protocol.SVUsage;

import java.io.IOException;
import java.util.Random;

public class Engine implements SVReceiver, SVUsage {
    public static final int UNDEFINED_DICE = -1;

    /**
     * Auf dem Spielfeld können insgesamt 30 Felder mit "Schiff" belegt sein.
     * Diese Kontante dient dazu, um herausfinden zu können, ob der Gegenspieler
     * verloren hat.
     */
    private static final int TOTAL_SHIP_ELEMENTS = 30;

    private final SVSender sender;
    private Status status;

    private int gesendeteZahl = UNDEFINED_DICE;

    private int[] angegriffeneKoordinate;

    private final Board ownBoard;
    private final Board otherBoard;

    public Engine(SVSender sender) {
        this.sender = sender;
        this.status = Status.START;
        this.ownBoard = new Board();
        this.otherBoard = new Board();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                  remote engine support                                     //
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void reihenfolgeWuerfeln_empfangen(int num) throws StatusException {
        if (this.status != Status.START) {
            throw new StatusException();
        }

        // höhere zahl - aktiv, kleinere -> passiv, gleiche zahl noch einmal.
        if (num == this.gesendeteZahl) {
            this.status = Status.START;
        } else if (num > this.gesendeteZahl) {
            this.status = Status.VERSENKEN_EMPFANGEN;
        } else {
            this.status = Status.VERSENKEN_SENDEN;
        }
    }

    @Override
    public void koordinate_empfangen(int x, int y) throws StatusException {
        if (this.status != Status.VERSENKEN_EMPFANGEN) {
            throw new StatusException();
        }

        this.angegriffeneKoordinate = new int[]{x, y};

        this.status = Status.BESTAETIGEN_SENDEN;
    }

    @Override
    public void kapitulation() throws StatusException {
        if (this.status != Status.VERSENKEN_SENDEN && this.status != Status.VERSENKEN_EMPFANGEN) {
            throw new StatusException();
        }

        this.status = Status.BEENDEN;
    }

    @Override
    public void bestaetigen_empfangen(int status) throws StatusException {
        if (this.status != Status.BESTAETIGEN_EMPFANGEN) {
            throw new StatusException();
        }

        // Wurde das Spiel gewonnen? => BEENDEN
        if (this.otherBoard.countFieldsWithStatus(BoardStatus.GETROFFEN) >= TOTAL_SHIP_ELEMENTS) {
            this.status = Status.BEENDEN;
        } else {
            this.status = Status.VERSENKEN_EMPFANGEN;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                  user interface support                                    //
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void doDice() throws StatusException, IOException {
        if (this.status != Status.START) {
            throw new StatusException();
        }

        Random r = new Random();
        this.gesendeteZahl = r.nextInt();

        // sende den Wert über den Sender
        this.sender.reihenfolgeWuerfeln(this.gesendeteZahl);
    }

    @Override
    public boolean isActive() {
        return this.status == Status.VERSENKEN_SENDEN;
    }
}
