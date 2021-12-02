package segmentedfilesystem;

public class HeaderPacket {
    private byte[] byteArr;
    private int packetLen;
    private int fileID;
    private String fileName;
    private int len;

    //Constructor for basic data required
    public HeaderPacket(byte[] arr, int packetLen) {
        this.byteArr = arr;
        this.len = packetLen;
        this.packetLen = arr.length;
    }

    //Grabs the rest of the data (excluding info at the beginning) from the header packet (this is the name)
    public void setFileName(){
        byte[] name = new byte[len-2];
        for (int i = 0; i < len-2; i++) {
            name[i] = byteArr[i+2];
        }
        this.fileName = new String(name);
    }

    //getter for fileID
    public int getFileID() {
        return fileID;
    }

    //getter for fileName
    public String getFileName() {
        return fileName;
    }
}
