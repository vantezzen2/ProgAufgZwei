import java.util.ArrayList;

/**
 * Class, that helps reading and writing multiple data sets to files
 */
public interface SetsReaderWriter {
    /**
     * Write a list of SensorData to a file
     *
     * @param data ArrayList, containing SensorData instances
     * @param filename Filename to write the data to
     * @throws FilesystemException if the file couldn't be opened or written to
     */
    void writeDataSets(ArrayList<SensorData> data, String filename) throws FilesystemException;

    /**
     * Read a list of SensorData from a file. Invalid entries will be skipped.
     *
     * @param filename Filename to read the data from
     * @return ArrayList, containing SensorData instances
     * @throws FilesystemException if the file couldn't be opened or read
     */
    ArrayList<SensorData> readDataSets(String filename) throws FilesystemException;
}
