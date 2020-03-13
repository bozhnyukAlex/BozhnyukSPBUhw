package Picture;

public class BitMapFileHeader {
    private short bfType;
    private int bfSize;
    private short bfReserved1;
    private short bfReserved2;
    private int bfOffBits;

    public BitMapFileHeader(short bfType, int bfSize, short bfReserved1, short bfReserved2, int bfOffBits) {
        this.bfType = bfType;
        this.bfSize = bfSize;
        this.bfReserved1 = bfReserved1;
        this.bfReserved2 = bfReserved2;
        this.bfOffBits = bfOffBits;
    }

    public BitMapFileHeader() {
        this.bfType = 0;
        this.bfSize = 0;
        this.bfReserved1 = 0;
        this.bfReserved2 = 0;
        this.bfOffBits = 0;
    }

    public short getBfType() {
        return bfType;
    }

    public void setBfType(short bfType) {
        this.bfType = bfType;
    }

    public int getBfSize() {
        return bfSize;
    }

    public void setBfSize(int bfSize) {
        this.bfSize = bfSize;
    }

    public short getBfReserved1() {
        return bfReserved1;
    }

    public void setBfReserved1(short bfReserved1) {
        this.bfReserved1 = bfReserved1;
    }

    public short getBfReserved2() {
        return bfReserved2;
    }

    public void setBfReserved2(short bfReserved2) {
        this.bfReserved2 = bfReserved2;
    }

    public int getBfOffBits() {
        return bfOffBits;
    }

    public void setBfOffBits(int bfOffBits) {
        this.bfOffBits = bfOffBits;
    }
}
