/**
 * Generic data storage bucket for saving and managing multiple iDataSet instances
 */
package dataBucket;

import java.util.ArrayList;

public interface iStorageBucket<T> {
    /**
     * Add a new item to the storage
     *
     * @param data Item to add to the bucket
     */
    void add(T data);

    /**
     * Get all stored instances
     *
     * @return ArrayList of data instances
     */
    ArrayList<T> getAll();

    /**
     * Get a single data instance
     *
     * @param index Index in the ArrayList returned in getAll()
     * @return Data
     */
    T get(int index) throws IllegalArgumentException;

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
