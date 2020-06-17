package schiffeversenken.protocolBinding;

import schiffeversenken.StatusException;
import schiffeversenken.protocol.SVSender;

import java.io.DataOutputStream;
import java.io.IOException;

public class StreamBindingSender implements SVSender {
    private final DataOutputStream dos;

    public StreamBindingSender(DataOutputStream os) {
        this.dos = os;
    }

    @Override
    public void reihenfolgeWuerfeln(int num) throws StatusException, IOException {
        this.dos.writeInt(StreamBinding.WUERFELN);
        this.dos.writeInt(num);
    }

    @Override
    public void koordinate_senden(int zeile, int spalte) throws StatusException, IOException {
        this.dos.writeInt(StreamBinding.KOORDINATE);
        this.dos.writeInt(zeile);
        this.dos.writeInt(spalte);
    }

    @Override
    public void kapitulation() throws StatusException, IOException {
        this.dos.writeInt(StreamBinding.KAPITULATION);
    }

    @Override
    public void bestaetigen_senden(int status) throws StatusException, IOException {
        this.dos.writeInt(StreamBinding.BESTAETIGEN);
        this.dos.writeInt(status);
    }
}
