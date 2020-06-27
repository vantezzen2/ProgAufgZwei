package persistence;

import dataBucket.iStorageBucket;

/**
 * Class, that helps reading and writing multiple data sets to files
 */
public interface iPersistor {
    /**
     * Write a list of iStorageBucket to a file
     *
     * @param data     iStorageBucket instance
     * @param filename Filename to write the data to
     * @throws FilesystemException if the file couldn't be opened or written to
     */
    void writeBucket(iStorageBucket data, String filename) throws FilesystemException;

    /**
     * Read a list of datasets from a file and save them into an iStorageBucket. Invalid entries will be skipped.
     *
     * @param filename Filename to read the data from
     * @return SensorDataBucket, containing the data
     * @throws FilesystemException if the file couldn't be opened or read
     */
    iStorageBucket readBucket(String filename, iStorageBucket bucket) throws FilesystemException;
}
