/**
 * Dummy Sender für Tests:
 * Sende Daten direkt an einen Receiver, ohne TCP zu nutzen
 */
package schiffeversenken;

import schiffeversenken.protocol.SVReceiver;
import schiffeversenken.protocol.SVSender;

import java.io.IOException;

public class DummySender implements SVSender {
    private SVReceiver receiver;

    public DummySender(SVReceiver receiver) {
        this.receiver = receiver;
    }

    public DummySender() {
    }

    public void setReceiver(SVReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void reihenfolgeWuerfeln(int num) throws StatusException, IOException {
        this.receiver.reihenfolgeWuerfeln_empfangen(num);
    }

    @Override
    public void koordinate_senden(int zeile, int spalte) throws StatusException, IOException {
        this.receiver.koordinate_empfangen(zeile, spalte);
    }

    @Override
    public void kapitulation() throws StatusException, IOException {
        this.receiver.kapitulation();
    }

    @Override
    public void bestaetigen_senden(int status) throws StatusException, IOException {
        this.receiver.bestaetigen_empfangen(status);
    }
}
