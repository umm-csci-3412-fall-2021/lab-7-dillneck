package segmentedfilesystem;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class ReceivedFile {
    private int fileID;
    public int finalID;
    public String name;
    public SortedMap<Integer, byte[]> maps;
    public boolean full = false;
    public boolean finalPacket = false;

    public ReceivedFile(int fileID) {
        this.fileID = fileID;
        this.full = false;
        this.finalID = -1;
        this.maps = new TreeMap<>();
    }

    public int getFileID() {
        return fileID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(DataPacket packet){
        maps.put(packet.getPacketNumber(), packet.getPacketData());
        isComplete();
    }

    public int finalFileSize(){
        int num=0;
        for(Map.Entry<Integer, byte[]> entry : maps.entrySet()){
            num+=entry.getValue().length;
            System.out.println(num);
        }
            return num;
    }

    public boolean isComplete(){
        if(finalID == 0){
            this.full = true;
        }
        if (!finalPacket) return false;
        for (int i = 0; i < finalID; i++) {
            if (!maps.containsKey(i)) {
                return false;
            }

        }
        this.full = true;
        return true;

    }

}
