// Source: https://github.com/thsc42/Prog2_2020/blob/master/test/transmission/TransmissionTests.java
// but ported to JUnit 5
package transmission;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class DataConnectorTest {
    private static final int PORTNUMBER = 9876;
    private static final int TEST_INT = 42;

    @Test
    public void gutConnectionTest1() throws IOException {
        // open server side
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataConnection serverSide;
                try {
                    serverSide = new DataConnector(PORTNUMBER);
                    DataInputStream dataInputStream = serverSide.getDataInputStream();
                    int readValue = dataInputStream.readInt();

                    assertEquals(TEST_INT, readValue);
                } catch (IOException e) {
                    fail("Can't create server");
                    e.printStackTrace();
                }
            }
        }).start();

        // open client side
        DataConnection clientSide = new DataConnector("localhost", PORTNUMBER);

        DataOutputStream dataOutputStream = clientSide.getDataOutputStream();
        dataOutputStream.writeInt(TEST_INT);
    }
}