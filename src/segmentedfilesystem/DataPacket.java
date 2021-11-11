package segmentedfilesystem;

public class DataPacket {
    private int statByte;
    private int fileID;
    private int packetNum;
    private int[] data;

    public DataPacket(int stat, int ID, int num, int[] data) {
        this.statByte = stat;
        this. fileID = ID;
        this.packetNum = num;
        this.data = data;
    }

    public boolean isLast() {
        if(this.statByte%4==3){
            return true;
        }
        else{
            return false;
        }
    }

    public int getFileID() {
        return fileID;
    }

    public int getPacketNum() {
        return packetNum;
    }

    public int[] getData() {
        return data;
    }
}
