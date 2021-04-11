package Test;

import Main.Filter;
import Picture.Picture;
import org.openjdk.jmh.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class Measure {

    private final String CUR_DIR = System.getProperty("user.dir");
    private final String SEP = File.separator;
    private final String TEST_PATH = CUR_DIR + SEP + "pic" + SEP + "input.bmp";

    private Picture test;
    private Filter filter;

    @Param({"1", "2", "4", "8", "16", "32"})
    private int threadN;

    @Param({"ColorWB", "Average3x3", "Gauss3x3", "Gauss5x5", "SobelX", "SobelY"})
    private String filterLabel;

    @Param({"true", "false"})
    private boolean isHorizontal;

    @Setup
    public void prepare() throws IOException {
        test = new Picture();
        test.Read(TEST_PATH);
        filter = new Filter(filterLabel);
    }

    @Benchmark
    public void measure() throws Exception {
        filter.parallelUse(test, threadN, isHorizontal);
    }

}
