package segmentedfilesystem;

public class DataPacket {
    private byte[] byteArr;
    private int packetLen;
    public int finalID;

    //Grabs the byte and turns it into an unsigned Int
    public int getId() {
        return Byte.toUnsignedInt(byteArr[1]);
    }

    //Grabs the status byte and turns it into an unsigned Int
    public int getStatusByte() {
        return Byte.toUnsignedInt(byteArr[0]);
    }

    //constructor with needed basic data
    public DataPacket(byte[] data, int length) {
        this.byteArr = data;
        this.packetLen = length;
    }

    //Grabs the data from a packet, excluding the information bytes at the front
    public byte[] getPacketData() {
        byte[] packetData = new byte[packetLen-4];
        for (int i = 4; i < packetLen; i++) {
            packetData[i-4] = byteArr[i];
        }
        return packetData;
    }

    //gets the two byte integer in base 256 for the packet's number
    public int getPacketNumber() {
        byte firstByte = byteArr[2];
        byte secondByte = byteArr[3];
        int firstInt = Byte.toUnsignedInt(firstByte);
        int secondInt = Byte.toUnsignedInt(secondByte);
        int packetNumber = firstInt * 256 + secondInt;
        return packetNumber;
    }
}
