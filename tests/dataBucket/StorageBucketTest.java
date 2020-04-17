package dataBucket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sensorData.DataSet;
import sensorData.SensorData;

import java.util.ArrayList;

class StorageBucketTest {
    public static SensorDataBucket data;
    DataSet ds1;

    @BeforeEach
    void setUp() {
        data = new StorageBucket();

        // Add test data
        ds1 = new DataSet("A");
        ds1.add(1f);
        ds1.add(2f);
        ds1.add(3f);
        data.add(ds1);

        DataSet ds2 = new DataSet("B");
        ds2.add(4f);
        ds2.add(5f);
        ds2.add(6f);

        data.add(ds2);

        DataSet ds3 = new DataSet("C");

        data.add(ds3);
    }

    @Test
    @DisplayName("Will start empty")
    void willStartEmpty() {
        SensorDataBucket bucket = new StorageBucket();
        assertEquals(0, bucket.size());
    }

    @Test
    @DisplayName("Can add new data")
    void add() {
        assertEquals(3, data.size());

        SensorData set = new DataSet("D");

        data.add(set);

        assertEquals(4, data.size());
        assertEquals(set, data.get(3));
    }

    @Test
    @DisplayName("Can return all data")
    void getAll() {
        // Build same data structure as Bucket
        ArrayList<SensorData> expect = new ArrayList<>();
        expect.add(data.get(0));
        expect.add(data.get(1));
        expect.add(data.get(2));

        assertEquals(expect, data.getAll());
    }

    @Test
    @DisplayName("Can return data")
    void canReturnData() {
        assertEquals(ds1, data.get(0));
    }

    @Test
    @DisplayName("Will fail on invalid index")
    void willFailOnInvalidIndex() {
        assertThrows(IllegalArgumentException.class, () -> data.get(3));
        assertThrows(IllegalArgumentException.class, () -> data.get(-1));
    }

    @Test
    @DisplayName("Can get size")
    void canGetSize() {
        for(int i = 4; i < 15; i++) {
            data.add(ds1);
            assertEquals(i, data.size());
        }
    }
}