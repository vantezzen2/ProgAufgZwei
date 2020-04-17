/**
 * Storage to save multiple SensorData instances
 *
 * @author vantezzen2
 */
package dataBucket;

import sensorData.SensorData;

import java.util.ArrayList;

public interface SensorDataBucket {
    /**
     * Add a new SensorData to the storage
     *
     * @param data SensorData to add to the bucket
     */
    void add(SensorData data);

    /**
     * Get all stored SensorData instances
     *
     * @return ArrayList of SensorData instances
     */
    ArrayList<SensorData> getAll();

    /**
     * Get a single SensorData instance
     *
     * @param index Index in the ArrayList returned in getAll()
     * @return SensorData
     */
    SensorData get(int index) throws IllegalArgumentException;

    /**
     * Get the number of elements in the bucket
     *
     * @return Number of instances stored
     */
    int size();

    /**
     * Import a String archive into the current bucket
     *
     * @param archive String archive
     */
    void importArchive(String archive);

    /**
     * Convert StorageBucket to string for archiving
     * @return
     */
    String toString();
}
