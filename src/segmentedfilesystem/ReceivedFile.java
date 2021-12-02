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

    //constructor with basic required data
    public ReceivedFile(int fileID) {
        this.fileID = fileID;
        this.full = false;
        this.finalID = -1;
        this.maps = new TreeMap<>();
    }

    //getter for fileID
    public int getFileID() {
        return fileID;
    }

    //Setter for name when receiving header packet
    public void setName(String name) {
        this.name = name;
    }

    //adds packets to their respective treemaps, keypaired of their packet# and data
    public void add(DataPacket packet){
        maps.put(packet.getPacketNumber(), packet.getPacketData());
        isComplete();
    }

    //Uses ByteArrayOutputStream to set each file's bytes into a byte array for downloading
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

    //Writes the file using the data from the byteArrLast
    public void writeFile() {
        try (FileOutputStream outputStream = new FileOutputStream(name)) {
            outputStream.write(byteArrLast);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    //checks if a file is done receiving packets by running through every entry of a maps entry until the finalID
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
        toByteArray();
        return true;
    }
}
