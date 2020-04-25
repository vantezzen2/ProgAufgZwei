package transmission;

import dataBucket.SensorDataBucket;
import dataBucket.StorageBucket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sensorData.DataSet;
import sensorData.SensorData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BucketTransmissionTest {
    private static final int PORTNUMBER = 9876;

    private static SensorDataBucket testBucket() {
        // Create test data set
        SensorData data = new DataSet("A");
        data.add(1.0f);
        SensorData data2 = new DataSet("B");
        data2.add(2.0f);
        data2.add(3.0f);
        SensorData data3 = new DataSet("C");

        SensorDataBucket bucket = new StorageBucket();
        bucket.add(data);
        bucket.add(data2);
        bucket.add(data3);

        return bucket;
    }

    @Test
    @DisplayName("Can transmit data")
    public void canTransmitData() throws IOException {
        SensorDataBucket bucket = testBucket();

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

        SensorDataBucket receivingBucket = new StorageBucket();

        BucketReceiver receiver = new BucketReceiver(clientSide, receivingBucket);
        receiver.readData();

        assertEquals(bucket.toString(), receivingBucket.toString());
    }
}