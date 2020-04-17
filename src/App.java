import dataBucket.SensorDataBucket;
import dataBucket.StorageBucket;
import persistence.Filesystem;
import sensorData.DataSet;
import sensorData.SensorData;
import persistence.SensorDataPersistor;
import transmission.BucketReceiver;
import transmission.BucketSender;
import transmission.DataConnector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class App {
    public static final String FILENAME = "values.txt";
    public static final int PORT = 24434;

    /**
     * Create example storage bucket with random data
     *
     * @return SensorStorageBucket containing 3 SensorData instances
     */
    private static SensorDataBucket exampleData() {
        ArrayList<SensorData> data = new ArrayList<>();
        Random r = new Random();

        // Add test data
        DataSet ds1 = new DataSet("My Good Old Sensor");
        ds1.add(r.nextFloat());
        ds1.add(r.nextFloat());
        ds1.add(r.nextFloat());
        data.add(ds1);

        DataSet ds2 = new DataSet("My Other Sensor");
        ds2.add(r.nextFloat());
        ds2.add(r.nextFloat());
        ds2.add(r.nextFloat());

        data.add(ds2);

        DataSet ds3 = new DataSet("Sensor without values");

        data.add(ds3);

        return new StorageBucket(data);
    }

    /**
     * Write and read a storage bucket and output the read data
     *
     * @param bucket Bucket to use
     */
    private static void writeRead(SensorDataBucket bucket) {
        System.out.println("Storing Bucket to disk");

        SensorDataPersistor connection = new Filesystem();

        try {
            connection.writeDataSets(bucket, FILENAME);
        } catch (Exception ex) {
            System.out.println("Error while writing data: " + ex);
        }

        try {
            SensorDataBucket outBucket = connection.readDataSets(FILENAME);
            System.out.println(outBucket.getAll());
        } catch (Exception ex) {
            System.out.println("Error while reading data: " + ex);
        }
    }

    /**
     * Send and recieve a storage bucket over TCP
     *
     * @param bucket Bucket to use
     */
    private static void tcpSendReceive(SensorDataBucket bucket) {
        System.out.println("Sending Bucket over TCP");

        // Create server
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("[Server] Creating TCP server");
                DataConnector server;
                try {
                    server = new DataConnector(24434);
                } catch (IOException e) {
                    System.out.println("[Server] Error while creating TCP server: " + e);
                    return;
                }
                System.out.println("[Server] TCP connection established");

                BucketSender sender = new BucketSender(server);

                try {
                    sender.sendData(bucket);
                } catch (IOException e) {
                    System.out.println("[Server] Error while sending data: " + e);
                }
            }
        }).start();

        // Create client
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("[Client] Creating TCP connection");
                DataConnector client;
                try {
                    client = new DataConnector("localhost", 24434);
                } catch (IOException e) {
                    System.out.println("Error while creating TCP connection: " + e);
                    return;
                }
                System.out.println("[Client] TCP connection established");

                SensorDataBucket outBucket = new StorageBucket();

                BucketReceiver receiver = new BucketReceiver(client, outBucket);
                receiver.readData();

                System.out.println("Items transported over TCP:");
                System.out.println(outBucket.getAll());
            }
        }).start();
    }

    public static void main(String[] args) {
        SensorDataBucket bucket = exampleData();

        writeRead(bucket);
        tcpSendReceive(bucket);
    }
}
