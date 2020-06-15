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
    private int empfangeneZahl = UNDEFINED_DICE;

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
        if (this.status != Status.START && this.status != Status.WURFEL_GESENDET) {
            throw new StatusException();
        }

        this.empfangeneZahl = num;

        // höhere zahl - aktiv, kleinere -> passiv, gleiche zahl noch einmal.
        if (this.status == Status.WURFEL_GESENDET) {
            this.reihenfolgeEntscheiden();
        } else {
            this.status = Status.WURFEL_EMPFANGEN;
        }
    }

    @Override
    public void koordinate_empfangen(int x, int y) throws StatusException, IOException {
        if (this.status != Status.VERSENKEN_EMPFANGEN) {
            throw new StatusException();
        }

        this.angegriffeneKoordinate = new int[]{x, y};

        this.status = Status.BESTAETIGEN_SENDEN;

        BoardStatus koordinate = ownBoard.get(x, y);
        int status = 0;
        if (koordinate == BoardStatus.WASSER) {
            status = 1;
        } else if (koordinate == BoardStatus.SCHIFF) {
            ownBoard.set(x, y, BoardStatus.GETROFFEN);
            status = 0;
        }

        this.sender.bestaetigen_senden(status);

        this.status = Status.VERSENKEN_SENDEN;
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

        BoardStatus newStatus;
        if (status == 0) {
            newStatus = BoardStatus.GETROFFEN;
        } else if (status == 1) {
            newStatus = BoardStatus.VERFEHLT;
        } else {
            newStatus = BoardStatus.VERSENKT;
        }
        this.otherBoard.set(
                this.angegriffeneKoordinate[0],
                this.angegriffeneKoordinate[1],
                newStatus
        );

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
        if (this.status != Status.START && this.status != Status.WURFEL_EMPFANGEN) {
            throw new StatusException();
        }

        Random r = new Random();
        this.gesendeteZahl = r.nextInt();

        // sende den Wert über den Sender
        this.sender.reihenfolgeWuerfeln(this.gesendeteZahl);

        if (this.status == Status.WURFEL_EMPFANGEN) {
            this.reihenfolgeEntscheiden();
        } else {
            this.status = Status.WURFEL_GESENDET;
        }
    }

    private void reihenfolgeEntscheiden() {
        if (this.empfangeneZahl == this.gesendeteZahl) {
            this.status = Status.START;
        } else if (this.empfangeneZahl > this.gesendeteZahl) {
            this.status = Status.VERSENKEN_EMPFANGEN;
        } else {
            this.status = Status.VERSENKEN_SENDEN;
        }
    }

    public void bombe_werfen(int zeile, int spalte) throws StatusException, IOException {
        this.status = Status.BESTAETIGEN_EMPFANGEN;
        this.angegriffeneKoordinate = new int[]{zeile, spalte};
        this.sender.koordinate_senden(zeile, spalte);
    }

    @Override
    public boolean isActive() {
        return this.status == Status.VERSENKEN_SENDEN;
    }

    public Board getOwnBoard() {
        return ownBoard;
    }

    public Board getOtherBoard() {
        return otherBoard;
    }
}
