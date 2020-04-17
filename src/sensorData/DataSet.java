package sensorData;

/*
 * Generic Data Set
 */

import java.util.ArrayList;

public class DataSet implements SensorData {
    private String sensorName;
    private ArrayList<Long> timestamps = new ArrayList<>();
    private ArrayList<Float> values = new ArrayList<>();

    // New sensorData.DataSet without previous data
    public DataSet(String sensorName) {
        this.sensorName = sensorName;
    }

    // Recover sensorData.DataSet from data string
    public DataSet(String[] data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("Invalid archive");
        }
        if (data.length == 0) {
            throw new IllegalArgumentException("sensorData.DataSet doesn't contain enough columns");
        }

        this.sensorName = data[0];

        // We may not have any values
        if (data.length >= 2) {
            for (int i = 1; i < data.length; i += 1) {
                String[] reading = data[i].split(" at ");

                if (reading.length != 2) {
                    throw new IllegalArgumentException("Supplied reading is not a valid format");
                }

                try {
                    this.values.add(
                            Float.parseFloat(reading[0])
                    );
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Supplied Value is not a number");
                }
                try {
                    this.timestamps.add(
                            Long.parseLong(reading[1])
                    );
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Supplied Value doesn't have a valid timestamp");
                }
            }
        }
    }

    public void add(float value, long time) {
        values.add(value);
        timestamps.add(time);
    }

    public void add(float value) {
        add(value, System.currentTimeMillis());
    }

    public float get(int index) throws IllegalArgumentException {
        if (index >= size() || index < 0) {
            throw new IllegalArgumentException("Reading doesn't exist");
        }

        return values.get(index);
    }

    public long getTime(int index) throws IllegalArgumentException {
        if (index >= size() || index < 0) {
            throw new IllegalArgumentException("Reading doesn't exist");
        }

        return timestamps.get(index);
    }

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        this.values.clear();
        this.timestamps.clear();
    }

    @Override
    public String toString() {
        String lineBreak = System.lineSeparator();
        StringBuilder output = new StringBuilder();

        output.append(sensorName);
        output.append(lineBreak);

        for (int i = 0; i < values.size(); i++) {
            output.append(values.get(i));
            output.append(" at ");
            output.append(timestamps.get(i));
            output.append(lineBreak);
        }

        output.append(lineBreak);

        return output.toString();
    }
}
