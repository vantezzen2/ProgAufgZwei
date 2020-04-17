package persistence;

import dataBucket.SensorDataBucket;

/**
 * Class, that helps reading and writing multiple data sets to files
 */
public interface SensorDataPersistor {
    /**
     * Write a list of sensorData.SensorData to a file
     *
     * @param data SensorDataBucket
     * @param filename Filename to write the data to
     * @throws FilesystemException if the file couldn't be opened or written to
     */
    void writeDataSets(SensorDataBucket data, String filename) throws FilesystemException;

    /**
     * Read a list of sensorData.SensorData from a file. Invalid entries will be skipped.
     *
     * @param filename Filename to read the data from
     * @return SensorDataBucket, containing the data
     * @throws FilesystemException if the file couldn't be opened or read
     */
    SensorDataBucket readDataSets(String filename) throws FilesystemException;
}
