import java.io.*;
import java.util.ArrayList;

public class WriteAndReadDataSets implements SetsReaderWriter {
    /**
     * Write a list of data sets to the file
     *
     * @param data DataSets to write
     * @throws Exception
     */
    public void writeDataSets(ArrayList<SensorData> data, String filename) throws FilesystemException {
        // Open file
        OutputStream os;

        try {
            os = new FileOutputStream(filename);
        } catch (FileNotFoundException ex) {
            throw new FilesystemException("Can't open file");
        }

        DataOutputStream dos = new DataOutputStream(os);

        for (SensorData set : data) {
            try {
                dos.writeBytes(set.toString());
            } catch (IOException ex) {
                throw new FilesystemException("Can't write to file");
            }
        }

        try {
            os.close();
        } catch(IOException ex) {
            throw new FilesystemException("Can't close file socket");
        }
    }

    /**
     * Read all DataSets contained in the file
     *
     * @throws Exception
     */
    public ArrayList<SensorData> readDataSets(String filename) throws FilesystemException {
        InputStream is = null;
        try {
            is = new FileInputStream(filename);
        } catch (FileNotFoundException ex) {
            throw new FilesystemException("Can't open file");
        }

        DataInputStream dis = new DataInputStream(is);
        String data = "";
        try {
            data = new String(dis.readAllBytes());
        } catch (IOException ex) {
            throw new FilesystemException("Can't read file");
        }

        try {
            is.close();
        } catch(IOException ex) {
            throw new FilesystemException("Can't close file socket");
        }

        // DataSets are separated by two line breaks
        String linebreak = System.lineSeparator();
        String[] setData = data.split(linebreak + linebreak);

        ArrayList<SensorData> dataSets = new ArrayList<>();

        for (String set : setData) {
            try {
                DataSet newDataSet = new DataSet(set.split(linebreak));
                dataSets.add(newDataSet);
            } catch (Exception ex) {
                System.out.println("Error while parsing data: " + ex);
            }
        }

        return dataSets;
    }
}
