package persistence;

import dataBucket.IntStorageBucket;
import dataBucket.iStorageBucket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilesystemTest {
    @Test
    @DisplayName("Can recover dataset")
    void canRecoverDataset() throws FilesystemException {
        iStorageBucket bucket = new IntStorageBucket();
        bucket.add(1);
        bucket.add(2);
        bucket.add(3);
        bucket.add(4);

        Filesystem fs = new Filesystem();
        fs.writeBucket(bucket, "./test.txt");
        iStorageBucket recoveredBucket = new IntStorageBucket();
        fs.readBucket("./test.txt", recoveredBucket);

        assertEquals(bucket.toString(), recoveredBucket.toString());
    }

    @Test
    @DisplayName("Will fail on invalid file name")
    void willFailOnInvalidFilename() {
        Filesystem fs = new Filesystem();
        iStorageBucket bucket = new IntStorageBucket();

        assertThrows(FilesystemException.class, () -> fs.readBucket("./invalid.txt", bucket));
    }
}