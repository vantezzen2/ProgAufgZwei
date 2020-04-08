import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class WriteAndReadDataSet {
    public static final String filename = "values.txt";

    /**
     * Write a list of data sets to the file
     *
     * @param data DataSets to write
     * @throws Exception
     */
    public static void writeDataSets(ArrayList<DataSet> data) throws Exception {
        // Open file
        OutputStream os;

        try {
            os = new FileOutputStream(filename);
        } catch (FileNotFoundException ex) {
            throw new Exception("Can't open file");
        }

        DataOutputStream dos = new DataOutputStream(os);

        for (DataSet set : data) {
            try {
                dos.writeBytes(set.toString());
            } catch (IOException ex) {
                throw new Exception("Can't write to file");
            }
        }
    }

    /**
     * Read all DataSets contained in the file
     *
     * @throws Exception
     */
    public static void readDataSets() throws Exception {
        InputStream is = null;
        try {
            is = new FileInputStream(filename);
        } catch (FileNotFoundException ex) {
            throw new Exception("Can't open file");
        }

        DataInputStream dis = new DataInputStream(is);
        String data = "";
        try {
            data = new String(dis.readAllBytes());
        } catch (IOException ex) {
            throw new Exception("Can't read file");
        }

        // DataSets are separated by two line breaks
        String linebreak = System.lineSeparator();
        String[] setData = data.split(linebreak + linebreak);

        ArrayList<DataSet> dataSets = new ArrayList<>();

        for (String set : setData) {
            try {
                DataSet newDataSet = new DataSet(set);
                dataSets.add(newDataSet);
            } catch (Exception ex) {
                System.out.println("Error while parsing data: " + ex);
            }
        }

        System.out.println(dataSets);
    }

    public static void main(String[] args) {
        ArrayList<DataSet> data = new ArrayList<>();
        Random r = new Random();

        // Add test data
        DataSet ds1 = new DataSet(
                "My Good Old Sensor",
                System.currentTimeMillis()
        );
        ds1.addValue(r.nextFloat());
        ds1.addValue(r.nextFloat());
        ds1.addValue(r.nextFloat());
        data.add(ds1);

        DataSet ds2 = new DataSet(
                "My Other Sensor",
                System.currentTimeMillis()
        );
        ds2.addValue(r.nextFloat());
        ds2.addValue(r.nextFloat());
        ds2.addValue(r.nextFloat());

        data.add(ds2);

        DataSet ds3 = new DataSet(
                "Sensor without values",
                System.currentTimeMillis()
        );

        data.add(ds3);

        try {
            writeDataSets(data);
        } catch (Exception ex) {
            System.out.println("Error while writing data: " + ex);
        }

        try {
            readDataSets();
        } catch (Exception ex) {
            System.out.println("Error while reading data: " + ex);
        }
    }
}
