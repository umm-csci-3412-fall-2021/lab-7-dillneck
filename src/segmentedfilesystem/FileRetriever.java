package segmentedfilesystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
		PacketManager packetManager = new PacketManager();
		try{
			address = InetAddress.getByName(server);
			byte[] buffer = new byte[buffer_size];
			socket = new DatagramSocket();
			DatagramPacket packet = new DatagramPacket(buffer, buffer_size, address, port);
			socket.send(packet);
			//Will run until packetManager.allComplete() returns true.
			//This would mean that all three files have finished being organized.
			while(!packetManager.allComplete()){
				byte[] data = new byte[buffer_size];
				packet = new DatagramPacket(data, buffer_size);
				socket.receive(packet);
				packetManager.receivePacket(packet);
			}
			//Saves files once packetManager.allComplete == true
			packetManager.saveFiles();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}

	}

}
