package dataBucket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sensorData.DataSet;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IntStorageBucketTest {
    public static iStorageBucket data;
    DataSet ds1;

    @BeforeEach
    void setUp() {
        data = new IntStorageBucket();

        // Add test data
        data.add(1);
        data.add(2);
        data.add(3);
        data.add(4);
        data.add(5);
        data.add(6);
    }

    @Test
    @DisplayName("Will start empty")
    void willStartEmpty() {
        iStorageBucket bucket = new IntStorageBucket();
        assertEquals(0, bucket.size());
    }

    @Test
    @DisplayName("Can add new data")
    void add() {
        assertEquals(6, data.size());

        data.add(7);

        assertEquals(7, data.size());
        assertEquals(7, data.get(6));
    }

    @Test
    @DisplayName("Can return all data")
    void getAll() {
        // Build same data structure as Bucket
        ArrayList<Integer> expect = new ArrayList<>();
        expect.add(1);
        expect.add(2);
        expect.add(3);
        expect.add(4);
        expect.add(5);
        expect.add(6);

        assertEquals(expect, data.getAll());
    }

    @Test
    @DisplayName("Can return data")
    void canReturnData() {
        assertEquals(1, data.get(0));
    }

    @Test
    @DisplayName("Will fail on invalid index")
    void willFailOnInvalidIndex() {
        assertThrows(IllegalArgumentException.class, () -> data.get(8));
        assertThrows(IllegalArgumentException.class, () -> data.get(-1));
    }

    @Test
    @DisplayName("Can get size")
    void canGetSize() {
        for (int i = 7; i < 15; i++) {
            data.add(9);
            assertEquals(i, data.size());
        }
    }
}