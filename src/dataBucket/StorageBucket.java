package dataBucket;

import persistence.FilesystemException;
import sensorData.DataSet;
import sensorData.SensorData;

import java.io.IOException;
import java.util.ArrayList;

public class StorageBucket implements SensorDataBucket {
    ArrayList<SensorData> data;

    /**
     * Create new empty bucket
     */
    public StorageBucket() {
        data = new ArrayList<>();
    }

    /**
     * Create bucket with specified data
     *
     * @param data ArrayList of SensorData instances
     */
    public StorageBucket(ArrayList<SensorData> data) {
        this.data = data;
    }

    /**
     * Create bucket from the data stored in another bucket
     *
     * @param bucket Other Bucket instance
     */
    public StorageBucket(SensorDataBucket bucket) {
        this.data = bucket.getAll();
    }

    /**
     * Create bucket from archived string, ignoring invalid entries
     *
     * @param archive Archive to use
     */
    public StorageBucket(String archive) {
        data = new ArrayList<>();
        this.importArchive(archive);
    }

    @Override
    public void add(SensorData data) {
        this.data.add(data);
    }

    @Override
    public ArrayList<SensorData> getAll() {
        return data;
    }

    @Override
    public SensorData get(int index) throws IllegalArgumentException {
        if (index >= this.data.size() || index < 0) {
            throw new IllegalArgumentException("Index doesn't exist");
        }
        return data.get(index);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public void importArchive(String archive) {
        String linebreak = System.lineSeparator();
        String[] setData = archive.split(linebreak + linebreak);

        for (String set : setData) {
            try {
                DataSet newDataSet = new DataSet(set.split(linebreak));
                data.add(newDataSet);
            } catch (Exception ex) {
                // Ignore invalid entries
            }
        }
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        for (SensorData set : data) {
            output.append(set);
        }

        return output.toString();
    }
}
