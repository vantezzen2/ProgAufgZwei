/**
 * Generic Data Set
 */

import java.util.ArrayList;

public class DataSet {
    private String sensorName;
    private long time;
    private ArrayList<Float> values = new ArrayList<>();

    // New DataSet without previous data
    public DataSet(String sensorName, long time) {
        this.sensorName = sensorName;
        this.time = time;
    }

    // Recover DataSet from data string
    public DataSet(String data) throws IllegalArgumentException {
        String[] dataParts = data.split("\n");

        if (dataParts.length < 2) {
            throw new IllegalArgumentException("DataSet doesn't contain enough columns");
        }

        this.sensorName = dataParts[0];
        try {
            this.time = Long.parseLong(dataParts[1]);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Supplied Time is not a number");
        }

        // We may not have any values
        if (dataParts.length >= 3) {
            for (int i = 2; i < dataParts.length; i += 1) {
                try {
                    this.values.add(
                            Float.parseFloat(dataParts[i])
                    );
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Supplied Value is not a number");
                }
            }
        }
    }

    public void addValue(float value) {
        values.add(value);
    }

    @Override
    public String toString() {
        String lineBreak = System.lineSeparator();
        StringBuilder output = new StringBuilder();

        output.append(sensorName);
        output.append(lineBreak);
        output.append(time);
        output.append(lineBreak);

        for(float value : values) {
            output.append(value);
            output.append(lineBreak);
        }

        output.append(lineBreak);

        return output.toString();
    }
}
