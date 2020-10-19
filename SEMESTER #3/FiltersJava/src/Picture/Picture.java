package Picture;

import java.io.*;
import Main.Main;

public class Picture {
    private BitMapFileHeader bitMapFileHeader;
    private BitMapInfoHeader bitMapInfoHeader;
    private RGB[][] pixels;




    public void Read(String filePath) throws IOException {
        DataInputStream dataInput = new DataInputStream(new FileInputStream(filePath));
        this.bitMapInfoHeader = new BitMapInfoHeader();
        this.bitMapFileHeader = new BitMapFileHeader();
        this.bitMapFileHeader.setBfType(Main.toLittleEndian(dataInput.readShort()));
        this.bitMapFileHeader.setBfSize(Main.toLittleEndian(dataInput.readInt()));
        this.bitMapFileHeader.setBfReserved1(Main.toLittleEndian(dataInput.readShort()));
        this.bitMapFileHeader.setBfReserved2(Main.toLittleEndian(dataInput.readShort()));
        this.bitMapFileHeader.setBfOffBits(Main.toLittleEndian(dataInput.readInt()));

        this.bitMapInfoHeader.setBiSize(Main.toLittleEndian(dataInput.readInt()));
        this.bitMapInfoHeader.setBiWidth(Main.toLittleEndian(dataInput.readInt()));
        this.bitMapInfoHeader.setBiHeight(Main.toLittleEndian(dataInput.readInt()));
        this.bitMapInfoHeader.setBiPlanes(Main.toLittleEndian(dataInput.readShort()));
        this.bitMapInfoHeader.setBiBitCount(Main.toLittleEndian(dataInput.readShort()));
        this.bitMapInfoHeader.setBiCompression(Main.toLittleEndian(dataInput.readInt()));
        this.bitMapInfoHeader.setBiSizeImage(Main.toLittleEndian(dataInput.readInt()));
        this.bitMapInfoHeader.setBiXPelsPerMeter(Main.toLittleEndian(dataInput.readInt()));
        this.bitMapInfoHeader.setBiYPelsPerMeter(Main.toLittleEndian(dataInput.readInt()));
        this.bitMapInfoHeader.setBiClrUsed(Main.toLittleEndian(dataInput.readInt()));
        this.bitMapInfoHeader.setBiClrImportant(Main.toLittleEndian(dataInput.readInt()));
        //System.out.println("Width: " + bitMapInfoHeader.getBiWidth() + "\nHeight: " + bitMapInfoHeader.getBiHeight());

        this.pixels = new RGB[bitMapInfoHeader.getBiHeight()][bitMapInfoHeader.getBiWidth()];

        for (int i = 0; i < bitMapInfoHeader.getBiHeight(); i++) {
            for (int j = 0; j < bitMapInfoHeader.getBiWidth(); j++) {
                pixels[i][j] = new RGB();
                pixels[i][j].setRed(dataInput.readUnsignedByte());
                pixels[i][j].setGreen(dataInput.readUnsignedByte());
                pixels[i][j].setBlue(dataInput.readUnsignedByte());
                if (bitMapInfoHeader.getBiBitCount() == 32) {
                    dataInput.readByte();
                }
            }
        }


    }
    public void Write(String filePath) throws IOException {
        DataOutputStream dataOutput = new DataOutputStream(new FileOutputStream(filePath));
       // System.out.println("!!!");
        dataOutput.writeShort(Main.toLittleEndian(this.bitMapFileHeader.getBfType()));
        dataOutput.writeInt(Main.toLittleEndian(this.bitMapFileHeader.getBfSize()));
        dataOutput.writeShort(Main.toLittleEndian(this.bitMapFileHeader.getBfReserved1()));
        dataOutput.writeShort(Main.toLittleEndian(this.bitMapFileHeader.getBfReserved2()));
        dataOutput.writeInt(Main.toLittleEndian(this.bitMapFileHeader.getBfOffBits()));

        dataOutput.writeInt(Main.toLittleEndian(this.bitMapInfoHeader.getBiSize()));
        dataOutput.writeInt(Main.toLittleEndian(this.bitMapInfoHeader.getBiWidth()));
        dataOutput.writeInt(Main.toLittleEndian(this.bitMapInfoHeader.getBiHeight()));
        dataOutput.writeShort(Main.toLittleEndian(this.bitMapInfoHeader.getBiPlanes()));
        dataOutput.writeShort(Main.toLittleEndian(this.bitMapInfoHeader.getBiBitCount()));
        dataOutput.writeInt(Main.toLittleEndian(this.bitMapInfoHeader.getBiCompression()));
        dataOutput.writeInt(Main.toLittleEndian(this.bitMapInfoHeader.getBiSizeImage()));
        dataOutput.writeInt(Main.toLittleEndian(this.bitMapInfoHeader.getBiXPelsPerMeter()));
        dataOutput.writeInt(Main.toLittleEndian(this.bitMapInfoHeader.getBiYPelsPerMeter()));
        dataOutput.writeInt(Main.toLittleEndian(this.bitMapInfoHeader.getBiClrUsed()));
        dataOutput.writeInt(Main.toLittleEndian(this.bitMapInfoHeader.getBiClrImportant()));

        for (int i = 0; i < bitMapInfoHeader.getBiHeight(); i++) {
            for (int j = 0; j < bitMapInfoHeader.getBiWidth(); j++) {
                dataOutput.writeByte(pixels[i][j].getRed() & 0xFF);
                dataOutput.writeByte(pixels[i][j].getGreen() & 0xFF);
                dataOutput.writeByte(pixels[i][j].getBlue() & 0xFF);
                if (bitMapInfoHeader.getBiBitCount() == 32) {
                    dataOutput.writeByte(0);
                }
            }
        }
    }

    public int getHeight() {
        return this.bitMapInfoHeader.getBiHeight();
    }
    public int getWidth() {
        return this.bitMapInfoHeader.getBiWidth();
    }

    public RGB getPixel(int i, int j) {
        return this.pixels[i][j];
    }

    public void setPixel(int i, int j, RGB par) {
        this.pixels[i][j].setRed(par.getRed());
        this.pixels[i][j].setGreen(par.getGreen());
        this.pixels[i][j].setBlue(par.getBlue());
    }

    public RGB[][] getPixelPane() {
        return pixels;
    }
}
