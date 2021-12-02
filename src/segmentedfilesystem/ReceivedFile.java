package segmentedfilesystem;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.SortedMap;
import java.util.TreeMap;

public class ReceivedFile {
    private int fileID;
    public int finalID;
    public String name;
    public SortedMap<Integer, byte[]> maps;
    public boolean full = false;
    public boolean finalPacket = false;
    public byte[] byteArrLast;

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

    public void toByteArray(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            for (int i = 0; i <= finalID; i++) {
                outputStream.write(maps.get(i));
            }
            byteArrLast = outputStream.toByteArray();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void writeFile() {
        toByteArray();
        try (FileOutputStream outputStream = new FileOutputStream(name)) {
            outputStream.write(byteArrLast);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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
