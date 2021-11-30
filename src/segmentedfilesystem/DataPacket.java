package segmentedfilesystem;

public class DataPacket {
    private byte[] byteArr;
    private int packetLen;

    public int getId() {
        return Byte.toUnsignedInt(byteArr[1]);
    }

    public int getStatusByte() {
        return Byte.toUnsignedInt(byteArr[0]);
    }

    public boolean LastPacket() {
        return (getStatusByte() % 4 == 3);
    }

    public DataPacket(byte[] data, int length) {
        this.byteArr = data;
        this.packetLen = length;
    }

    public String fileName(){
        byte[] name = new byte[packetLen-2];
        for (int i = 0; i < packetLen-2; i++) {
            name[i] = byteArr[i+2];
        }
        return new String(name);
    }

    public byte[] getPacketData() {
        byte[] packetData = new byte[packetLen-4];
        for (int i = 4; i < packetLen; i++) {
            packetData[i-4] = byteArr[i];
        }
        return packetData;
    }

    public int getPacketNumber() {
        byte firstByte = byteArr[2];
        byte secondByte = byteArr[3];
        int firstInt = Byte.toUnsignedInt(firstByte);
        int secondInt = Byte.toUnsignedInt(secondByte);
        int packetNumber = firstInt * 256 + secondInt;
        return packetNumber;
    }





}
