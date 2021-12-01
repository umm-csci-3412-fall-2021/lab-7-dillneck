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


        if (statusByte % 2 == 0) {
            HeaderPacket header = new HeaderPacket(bytes, packet.getLength());
            for (int i = 0; i < files.size(); i++) {
                if(files.get(i).getFileID() == bytes[1]){
                    header.setFileName();
                    files.get(i).setName(header.getFileName());
                    files.get(i).isComplete();
                    return;
                }
            }
            ReceivedFile file = new ReceivedFile(header.getFileID());
            file.setName(header.getFileName());
            file.isComplete();
            files.add(file);
        }
        else {
            DataPacket data = new DataPacket(bytes, packet.getLength());
            if (statusByte % 4 == 3) {
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
                ReceivedFile file = new ReceivedFile(data.getId());
                file.add(data);
                file.isComplete();
                file.finalID = finalID;
                file.finalPacket = true;
                files.add(file);
                }
            else {
                for (int i = 0; i < files.size(); i++) {
                    if(files.get(i).getFileID() == bytes[1]){
                        files.get(i).add(data);
                        files.get(i).isComplete();
                        return;
                    }
                }
                ReceivedFile file = new ReceivedFile(data.getId());
                file.add(data);
                file.isComplete();
                files.add(file);
            }
        }
    }

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

}
