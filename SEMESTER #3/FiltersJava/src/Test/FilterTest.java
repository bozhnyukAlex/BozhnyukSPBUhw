package Test;

import Main.Filter;
import Picture.Picture;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FilterTest {
    private final String COLORWB_PATH = "D:\\BozhnyukSPBUhw\\EXTRA\\FiltersJava\\pic\\colorWb.bmp";
    private final String AVERAGE_3_PATH = "D:\\BozhnyukSPBUhw\\EXTRA\\FiltersJava\\pic\\average.bmp";
    private final String GAUSS3_PATH = "D:\\BozhnyukSPBUhw\\EXTRA\\FiltersJava\\pic\\gauss3.bmp";
    private final String GAUSS5_PATH = "D:\\BozhnyukSPBUhw\\EXTRA\\FiltersJava\\pic\\gauss5.bmp";
    private final String SOBELX_PATH = "D:\\BozhnyukSPBUhw\\EXTRA\\FiltersJava\\pic\\sobelX.bmp";
    private final String SOBELY_PATH = "D:\\BozhnyukSPBUhw\\EXTRA\\FiltersJava\\pic\\sobelY.bmp";
    private final String TEST_PATH = "D:\\BozhnyukSPBUhw\\EXTRA\\FiltersJava\\pic\\input.bmp";

    private final String COLORWB_LABEL = "ColorWB";
    private final String AVERAGE_LABEL = "Average3x3";
    private final String GAUSS3_LABEL = "Gauss3x3";
    private final String GAUSS5_LABEL = "Gauss5x5";
    private final String SOBELX_LABEL = "SobelX";
    private final String SOBELY_LABEL = "SobelY";
    private Picture test;
    private Picture expected;

    @Before
    public void setUp() throws Exception {
        test = new Picture();
        test.Read(TEST_PATH);
        expected = new Picture();
    }

    private void check() {
        for (int i = 0; i < test.getHeight(); i++) {
            for (int j = 0; j < test.getWidth(); j++) {
                assertEquals(expected.getPixel(i, j).getRed(), test.getPixel(i, j).getRed());
                assertEquals(expected.getPixel(i, j).getGreen(), test.getPixel(i, j).getGreen());
                assertEquals(expected.getPixel(i, j).getBlue(), test.getPixel(i, j).getBlue());
            }
        }
    }
    @Test
    public void colorWBTest() throws IOException {
        expected.Read(COLORWB_PATH);
        Filter colorWbFilter = new Filter(COLORWB_LABEL);
        colorWbFilter.use(test);
        check();
    }

    @Test
    public void averageTest() throws IOException {
        expected.Read(AVERAGE_3_PATH);
        Filter averageFilter = new Filter(AVERAGE_LABEL);
        averageFilter.use(test);
        check();
    }

    @Test
    public void gauss3Test() throws IOException {
        expected.Read(GAUSS3_PATH);
        Filter gauss3Filter = new Filter(GAUSS3_LABEL);
        gauss3Filter.use(test);
        check();
    }
    @Test
    public void gauss5Test() throws IOException {
        expected.Read(GAUSS5_PATH);
        Filter gauss5Filter = new Filter(GAUSS5_LABEL);
        gauss5Filter.use(test);
        check();
    }
    @Test
    public void sobelXTest() throws IOException {
        expected.Read(SOBELX_PATH);
        Filter sobelXFilter = new Filter(SOBELX_LABEL);
        sobelXFilter.use(test);
        check();
    }
    @Test
    public void sobelYTest() throws IOException {
        expected.Read(SOBELY_PATH);
        Filter sobelYFilter = new Filter(SOBELY_LABEL);
        sobelYFilter.use(test);
        check();
    }

}