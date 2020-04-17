package sensorData;

/**
 * sensorData.SensorData container, containing all readings from a sensor
 */
public interface SensorData {
    /**
     * Add a value (reading) from the Sensor with a supplied timestamp
     * @param value Reading from the Sensor
     * @param time Time, that the reading has been done
     */
    void add(float value, long time);

    /**
     * Add a value (reading) from the Sensor at the current time.
     * This will use the current time automatically
     * @param value Current Reading from the Sensor
     */
    void add(float value);

    /**
     * Get a specific reading from the sensor
     * @param index Index of the reading in the readings ArrayList
     * @return Value of the Reading
     * @throws IllegalArgumentException if the reading doesn't exist
     */
    float get(int index) throws IllegalArgumentException;

    /**
     * Get time a reading has been made
     * @param index Index of the reading in the readings ArrayList
     * @return Timestamp, when the reading has been made
     * @throws IllegalArgumentException if the reading doesn't exist
     */
    long getTime(int index) throws IllegalArgumentException;

    /**
     * Get the size of the sensorData.DataSet (a.k.a. how many readings have been saved)
     * @return Size of the sensorData.DataSet
     */
    int size();

    /**
     * Returns true if this list contains no elements
     * @return true if this list contains no elements
     */
    boolean isEmpty();

    /**
     * Removes all of the readings
     */
    void clear();

    /**
     * Archive data to String. This String can later be used to recover the sensorData.DataSet instance
     * @return Archived data
     */
    String toString();
}
