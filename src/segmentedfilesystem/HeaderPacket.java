package segmentedfilesystem;

public class HeaderPacket {
    private int fileID;
    private String fileName;

    public HeaderPacket(int ID, String fileName) {
        this.fileID = ID;
        this.fileName = fileName;
    }

    public int getFileID() {
        return fileID;
    }

    public String getFileName() {
        return fileName;
    }
}
