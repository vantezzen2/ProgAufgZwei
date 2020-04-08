import java.util.ArrayList;
import java.util.Random;

public class App {
    public static final String filename = "values.txt";

    public static void main(String[] args) {
        ArrayList<SensorData> data = new ArrayList<>();
        Random r = new Random();

        // Add test data
        DataSet ds1 = new DataSet("My Good Old Sensor");
        ds1.add(r.nextFloat());
        ds1.add(r.nextFloat());
        ds1.add(r.nextFloat());
        data.add(ds1);

        DataSet ds2 = new DataSet("My Other Sensor");
        ds2.add(r.nextFloat());
        ds2.add(r.nextFloat());
        ds2.add(r.nextFloat());

        data.add(ds2);

        DataSet ds3 = new DataSet("Sensor without values");

        data.add(ds3);

        WriteAndReadDataSets connection = new WriteAndReadDataSets();

        try {
            connection.writeDataSets(data, filename);
        } catch (Exception ex) {
            System.out.println("Error while writing data: " + ex);
        }

        try {
            ArrayList<SensorData> dataSets = connection.readDataSets(filename);
            System.out.println(dataSets);
        } catch (Exception ex) {
            System.out.println("Error while reading data: " + ex);
        }
    }
}
