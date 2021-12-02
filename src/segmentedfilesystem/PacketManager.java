package segmentedfilesystem;
import java.net.DatagramPacket;
import java.util.ArrayList;

public class PacketManager {
    ArrayList<ReceivedFile> files = new ArrayList<>();
    public void receivePacket(DatagramPacket packet){
        byte[] bytes = packet.getData();
        byte statusByte = bytes[0];
        boolean find = false;
        int currentID;
        //Case for header packets
        if (statusByte % 2 == 0) {
            HeaderPacket header = new HeaderPacket(bytes, packet.getLength());
            //Checking for if the fileID for this header already exists
            for (int i = 0; i < files.size(); i++) {
                if(files.get(i).getFileID() == bytes[1]){
                    header.setFileName();
                    files.get(i).setName(header.getFileName());
                    files.get(i).isComplete();
                    return;
                }
            }
            //Runs if there wasn't a file for the header being recieved
            ReceivedFile file = new ReceivedFile(header.getFileID());
            file.setName(header.getFileName());
            file.isComplete();
            files.add(file);
        }
        else {
            //runs for DataPackets
            DataPacket data = new DataPacket(bytes, packet.getLength());
            //Check if it's the last datapacket
            if (statusByte % 4 == 3) {
                //Checking for if the fileID for this datapacket already exists
                int finalID = data.getPacketNumber();
                for (int i = 0; i < files.size(); i++) {
                    if (files.get(i).getFileID() == bytes[1]) {
                        files.get(i).finalID = finalID;
                        files.get(i).finalPacket = true;
                        files.get(i).add(data);
                        files.get(i).isComplete();
                        return;
                    }
                }
                //Runs if the first packet for the file recieved is the last packet
                ReceivedFile file = new ReceivedFile(data.getId());
                file.add(data);
                file.isComplete();
                file.finalID = finalID;
                file.finalPacket = true;
                files.add(file);
                }
            else {
                //checking if file already exists for packet which is NON-last
                for (int i = 0; i < files.size(); i++) {
                    if(files.get(i).getFileID() == bytes[1]){
                        files.get(i).add(data);
                        files.get(i).isComplete();
                        return;
                    }
                }
                //runs if there was no file for recieved datapacket
                ReceivedFile file = new ReceivedFile(data.getId());
                file.add(data);
                file.isComplete();
                files.add(file);
            }
        }
    }

    //Checks if all files are done being received
    public boolean allComplete() {
        int len = files.size();
        if(len == 0){
            return false;
        }
        for (int i = 0; i < len; i++) {
            if (files.get(i).full == false) {
                return false;
            }
        }
        return true;
    }

    //runs .writeFiles on all entries of files
    public void saveFiles(){
        for (int i = 0; i < files.size(); i++) {
            files.get(i).writeFile();
        }
    }
}
