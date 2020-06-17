package schiffeversenken.protocolBinding;

import schiffeversenken.StatusException;
import schiffeversenken.protocol.SVReceiver;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Empfange Kommandos für das Schiffe-Versenken Spiel und leite sie an einen SVReceiver weiter
 */
public class StreamBindingReceiver extends Thread {
    private final DataInputStream dis;
    private final SVReceiver receiver;

    public StreamBindingReceiver(DataInputStream is, SVReceiver receiver) {
        this.dis = is;
        this.receiver = receiver;
    }

    private void readWuerfel() throws IOException, StatusException {
        int random = this.dis.readInt();
        this.receiver.reihenfolgeWuerfeln_empfangen(random);
    }

    private void readKoordinate() throws IOException, StatusException {
        int x = this.dis.readInt();
        int y = this.dis.readInt();
        this.receiver.koordinate_empfangen(x, y);
    }

    private void readBestaetigung() throws IOException, StatusException {
        int status = this.dis.readInt();
        this.receiver.bestaetigen_empfangen(status);
    }

    public void run() {
        boolean repeat = true;
        while (repeat) {
            try {
                int command = this.dis.readInt();

                if (command == StreamBinding.WUERFELN) {
                    this.readWuerfel();
                } else if (command == StreamBinding.KOORDINATE) {
                    this.readKoordinate();
                } else if (command == StreamBinding.KAPITULATION) {
                    this.receiver.kapitulation();
                } else if (command == StreamBinding.BESTAETIGEN) {
                    this.readBestaetigung();
                } else {
                    System.err.println("unknown command code: " + command + StreamBinding.BESTAETIGEN);
                    System.out.println(command);
                    System.out.println(StreamBinding.BESTAETIGEN);
                    System.out.println(command == StreamBinding.BESTAETIGEN);
                }
            } catch (IOException e) {
                System.err.println("IOException: " + e.getLocalizedMessage());
                repeat = false;
            } catch (StatusException e) {
                System.err.println("Status Exception: " + e.getLocalizedMessage());
                //repeat = false;
            }

        }
    }
}
