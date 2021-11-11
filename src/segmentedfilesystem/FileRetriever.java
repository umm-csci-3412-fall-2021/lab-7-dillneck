package segmentedfilesystem;
import java.net.*;
import java.io.*;

public class FileRetriever {

	public String server;
	public int port;
	public int buffer_size = 1028;
	public DatagramSocket socket;
	public InetAddress address;

	public FileRetriever(String server, int port) {
        // Save the server and port for use in `downloadFiles()`
        //...
		this.server = server;
		this.port = port;
		address = InetAddress.getByName(server);
	}

	public void downloadFiles() {
        // Do all the heavy lifting here.
        // This should
        //   * Connect to the server
        //   * Download packets in some sort of loop
        //   * Handle the packets as they come in by, e.g.,
        //     handing them to some PacketManager class
        // Your loop will need to be able to ask someone
        // if you've received all the packets, and can thus
        // terminate. You might have a method like
        // PacketManager.allPacketsReceived() that you could
        // call for that, but there are a bunch of possible
        // ways.



		byte[] buffer = new byte[buffer_size];
		socket = new DatagramSocket(buffer, 0, address, port);
		DatagramPacket packet = new DatagramPacket(buffer, 0, server, port);

	}

}
