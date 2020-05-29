package math;

import java.util.ArrayList;

public class Parabola extends Curve {
    private float p;
    // y^2 = 2*p*x
    public Parabola(float p, float from, float to) {
        super(from, to);
        this.p = p;
        equation = "y^2 = 2*" + p + "*x";
    }

    @Override
    public ArrayList<Float> getYPoints(float x) {
        float doubledP = p * 2;
        ArrayList<Float> yRes = new ArrayList<>();
        float y = (float) Math.sqrt(doubledP * x);
        if (y == 0) {
            yRes.add((float) 0);
            return yRes;
        }
        yRes.add(y);
        yRes.add(-y);
        return yRes;
    }
}
