// Source: https://github.com/thsc42/Prog2_2020/blob/master/src/transmission/DataConnector.java
package transmission;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DataConnector implements DataConnection {
    Socket client = null;
    ServerSocket server = null;

    /**
     * Create client side - open connection to address / port
     * @param address
     */
    public DataConnector(String address, int port) throws IOException {
        client = new Socket(address, port);
    }

    /**
     * Create server side - open port on this port and wait for one client
     * @param port
     */
    public DataConnector(int port) throws IOException {
        server = new ServerSocket(port);
        client = server.accept();
    }

    @Override
    public DataInputStream getDataInputStream() throws IOException {
        if (client != null) {
            return new DataInputStream(client.getInputStream());
        }
        throw new IOException("No client connected");
    }

    @Override
    public DataOutputStream getDataOutputStream() throws IOException {
        if (client != null) {
            return new DataOutputStream(client.getOutputStream());
        }
        throw new IOException("No client connected");
    }
}
