package segmentedfilesystem;

public class HeaderPacket {
    private byte[] byteArr;
    private int packetLen;
    private int fileID;
    private String fileName;
    private int len;

    public HeaderPacket(byte[] arr, int packetLen) {
        this.byteArr = arr;
        this.len = packetLen;
        this.packetLen = arr.length;
    }

    public void getPacketNumber() {
        byte firstByte = byteArr[2];
        byte secondByte = byteArr[3];
        int firstInt = Byte.toUnsignedInt(firstByte);
        int secondInt = Byte.toUnsignedInt(secondByte);
        int packetNumber = firstInt * 256 + secondInt;
        this.fileID = packetNumber;
    }

    public void setFileName(){
        byte[] name = new byte[len-2];
        for (int i = 0; i < len-2; i++) {
            name[i] = byteArr[i+2];
        }
        this.fileName = new String(name);
    }

    public int getFileID() {
        return fileID;
    }

    public String getFileName() {
        return fileName;
    }
}
