// Template from https://github.com/thsc42/Prog2_2020/blob/master/src/sensorData/SensorDataSender.java
package transmission;

import dataBucket.SensorDataBucket;
import persistence.FilesystemException;

import java.io.DataOutputStream;
import java.io.IOException;

public class BucketSender {
    private final DataConnection connection;

    public BucketSender(DataConnection connection) {
        this.connection = connection;
    }

    public void sendData(SensorDataBucket bucket) throws IOException {
        DataOutputStream dostream = this.connection.getDataOutputStream();

        String archive = bucket.toString();

        try {
            dostream.writeUTF(archive);
        } catch (IOException ex) {
            throw new IOException("Can't write bytes over TCP");
        }
    }
}