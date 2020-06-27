package transmission;

import dataBucket.IntStorageBucket;
import dataBucket.iStorageBucket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class BucketTransmissionTest {
    private static final int PORTNUMBER = 9876;

    private static iStorageBucket testBucket() {
        // Create test data set
        iStorageBucket bucket = new IntStorageBucket();
        bucket.add(1);
        bucket.add(2);
        bucket.add(3);

        return bucket;
    }

    @Test
    @DisplayName("Can transmit data")
    public void canTransmitData() throws IOException {
        iStorageBucket bucket = testBucket();

        // open server side
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataConnection serverSide;
                try {
                    serverSide = new DataConnector(PORTNUMBER);
                } catch (IOException e) {
                    fail("Can't create server: " + e);
                    e.printStackTrace();
                    return;
                }

                BucketSender sender = new BucketSender(serverSide);

                try {
                    sender.sendData(bucket);
                } catch (IOException e) {
                    fail("[Server] Error while sending data: " + e);
                    e.printStackTrace();
                }
            }
        }).start();

        // open client side
        DataConnection clientSide = new DataConnector("localhost", PORTNUMBER);

        iStorageBucket receivingBucket = new IntStorageBucket();

        BucketReceiver receiver = new BucketReceiver(clientSide, receivingBucket);
        receiver.readData();

        assertEquals(bucket.toString(), receivingBucket.toString());
    }
}