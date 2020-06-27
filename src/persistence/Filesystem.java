package persistence;

import dataBucket.iStorageBucket;

import java.io.*;

public class Filesystem implements iPersistor {
    /**
     * Write a list of data sets to the file
     *
     * @param bucket DataSets to write
     * @throws Exception
     */
    public void writeBucket(iStorageBucket bucket, String filename) throws FilesystemException {
        // Open file
        OutputStream os;

        try {
            os = new FileOutputStream(filename);
        } catch (FileNotFoundException ex) {
            throw new FilesystemException("Can't open file");
        }

        DataOutputStream dos = new DataOutputStream(os);

        try {
            dos.writeBytes(bucket.toString());
        } catch (IOException ex) {
            throw new FilesystemException("Can't write to file");
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
    public iStorageBucket readBucket(String filename, iStorageBucket bucket) throws FilesystemException {
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

        bucket.importArchive(data);

        return bucket;
    }
}
