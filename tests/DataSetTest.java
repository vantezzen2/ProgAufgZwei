import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataSetTest {
    @Test
    @DisplayName("Can create blank instance")
    void canCreateBlankInstance() {
        SensorData data = new DataSet("Test");

        assertTrue(data.isEmpty());
    }

    @Test
    @DisplayName("Can create instance from archive")
    void canCreateInstanceFromArchive() {
        String[] archive = {
          "My Sensor Name",
          "0.29463124 at 1586338104974",
          "0.69795287 at 1586338104974"
        };
        SensorData data = new DataSet(archive);

        assertFalse(data.isEmpty());
        assertEquals(2, data.size());
    }

    @Test
    @DisplayName("Will fail on invalid archive reading")
    void willFailOnInvalidArchiveReading() {
        String[] archive = {
                "My Sensor Name",
                "0.29463124",
                "0.69795287 at 1586338104974"
        };
        try {
            SensorData data = new DataSet(archive);
            fail("DataSet constructor should fail with invalid archive data");
        } catch(IllegalArgumentException e) {}
    }

    @Test
    @DisplayName("Will Construct without readings")
    void willConstructWithoutReadings() {
        String[] archive = {
                "My Sensor Name"
        };

        SensorData data = new DataSet(archive);
        assertTrue(data.isEmpty());
    }

    @Test
    @DisplayName("Will fail with empty archive")
    void willFailWithEmptyArchive() {
        String[] archive = {};

        try {
            SensorData data = new DataSet(archive);
            fail("DataSet constructor should fail with invalid archive data");
        } catch(IllegalArgumentException e) {}
    }

    @Test
    @DisplayName("Will fail with null archive")
    void willFailWithNullArchive() {
        String[] archive = null;

        try {
            SensorData data = new DataSet(archive);
            fail("DataSet constructor should fail with invalid archive data");
        } catch(IllegalArgumentException e) {}
    }

    @Test
    @DisplayName("Can add data with timestamp")
    void canAddDataWithTimestamp() {
        SensorData data = new DataSet("Test");

        data.add(0.5f, 1);

        assertFalse(data.isEmpty());
        assertEquals(1, data.size());
        assertEquals(0.5f, data.get(0));
        assertEquals(1, data.getTime(0));
    }

    @Test
    @DisplayName("Can add data without timestamp")
    void canAddDataWithoutTimestamp() {
        SensorData data = new DataSet("Test");

        data.add(0.5f);

        assertFalse(data.isEmpty());
        assertEquals(1, data.size());
        assertEquals(0.5f, data.get(0));
    }

    @Test
    @DisplayName("Can get data and time")
    void canGetDataAndTime() {
        SensorData data = new DataSet("Test");

        data.add(0.5f, 1);

        assertEquals(0.5f, data.get(0));
        assertEquals(1, data.getTime(0));
    }

    @Test
    @DisplayName("Will fail on invalid index")
    void willFailOnInvalidIndex() {
        SensorData data = new DataSet("Test");

        data.add(0.5f, 1);

        try {
            data.get(1);
            fail("Should have thrown IllegalArgumentException on invalid index");
        } catch (IllegalArgumentException e) {}
        try {
            data.getTime(1);
            fail("Should have thrown IllegalArgumentException on invalid index");
        } catch (IllegalArgumentException e) {}

        try {
            data.get(-1);
            fail("Should have thrown IllegalArgumentException on invalid index");
        } catch (IllegalArgumentException e) {}
        try {
            data.getTime(-1);
            fail("Should have thrown IllegalArgumentException on invalid index");
        } catch (IllegalArgumentException e) {}
    }

    @Test
    @DisplayName("Can get size")
    void canGetSize() {
        SensorData data = new DataSet("Test");

        assertEquals(0, data.size());

        for (int i = 1; i < 20; i++) {
            data.add(0.5f, i);
            assertEquals(i, data.size());
        }
    }

    @Test
    @DisplayName("Can indicate if list is empty")
    void canIndicateIfListIsEmpty() {
        SensorData data = new DataSet("Test");

        assertTrue(data.isEmpty());
        data.add(1);
        assertFalse(data.isEmpty());
    }

    @Test
    @DisplayName("Can clear data")
    void canClearData() {
        SensorData data = new DataSet("Test");
        data.add(1);

        data.clear();

        assertTrue(data.isEmpty());
    }

    @Test
    @DisplayName("Can export data")
    void canExportData() {
        SensorData data = new DataSet("Test");

        assertEquals("Test\n\n", data.toString());
        data.add(0.5f, 1);
        assertEquals("Test\n0.5 at 1\n\n", data.toString());
        data.add(1.85f, 6);
        assertEquals("Test\n0.5 at 1\n1.85 at 6\n\n", data.toString());
    }
}