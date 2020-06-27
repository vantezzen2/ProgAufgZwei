package dataBucket;

import java.util.ArrayList;

public class IntStorageBucket implements iStorageBucket<Integer> {
    ArrayList<Integer> data;

    /**
     * Create new empty bucket
     */
    public IntStorageBucket() {
        data = new ArrayList<>();
    }

    /**
     * Create bucket with specified data
     *
     * @param data ArrayList of SensorData instances
     */
    public IntStorageBucket(ArrayList<Integer> data) {
        this.data = data;
    }

    /**
     * Create bucket from the data stored in another bucket
     *
     * @param bucket Other Bucket instance
     */
    public IntStorageBucket(iStorageBucket bucket) {
        this.data = bucket.getAll();
    }

    /**
     * Create bucket from archived string, ignoring invalid entries
     *
     * @param archive Archive to use
     */
    public IntStorageBucket(String archive) {
        data = new ArrayList<>();
        this.importArchive(archive);
    }

    @Override
    public void add(Integer data) {
        this.data.add(data);
    }

    @Override
    public ArrayList<Integer> getAll() {
        return data;
    }

    @Override
    public Integer get(int index) throws IllegalArgumentException {
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
                data.add(Integer.parseInt(set));
            } catch (Exception ex) {
                // Ignore invalid entries
            }
        }
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int set : data) {
            output.append(set + System.lineSeparator() + System.lineSeparator());
        }

        return output.toString();
    }
}
