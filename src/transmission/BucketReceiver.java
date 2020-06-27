// Template from https://github.com/thsc42/Prog2_2020/blob/master/src/sensorData/SensorDataReceiver.java
package transmission;

import dataBucket.iStorageBucket;

import java.io.DataInputStream;
import java.io.IOException;

public class BucketReceiver {
    private final DataConnection connection;
    private final iStorageBucket storage;

    public BucketReceiver(DataConnection connection, iStorageBucket storage) {
        this.connection = connection;
        this.storage = storage;
    }

    public void readData() {
        DataInputStream input;
        try {
            input = connection.getDataInputStream();
        } catch (IOException e) {
            System.out.println("Cannot setup connection: " + e);
            e.printStackTrace();
            return;
        }

        String data;

        try {
            data = input.readUTF();
        } catch (IOException e) {
            System.out.println("Cannot setup connection: " + e);
            e.printStackTrace();
            return;
        }

        System.out.println("[BucketReceiver] Received all data");

        storage.importArchive(data);

        System.out.println("Added " + storage.size() + " items to the bucket");
    }

    iStorageBucket getStorage() {
        return storage;
    }
}