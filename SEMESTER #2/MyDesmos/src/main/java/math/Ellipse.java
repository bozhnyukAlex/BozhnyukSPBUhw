package math;

import java.util.ArrayList;

public class Ellipse extends Curve {
    private float a, b;

    public Ellipse(float a, float b, float from, float to) {
        super(from, to);
        this.a = a;
        this.b = b;
        //equation = "x^2/" + this.a * this.a + " + y^2/" + this.b * this.b + " = 1";
        buildEquation();
    }

    @Override
    public ArrayList<Float> getYPoints(float x) {
        float aSqr = a * a;
        float bSqr = b * b;
        ArrayList<Float> yRes = new ArrayList<>();
        float y = (float) Math.sqrt(bSqr * (1 - (x * x) / aSqr));
        if (y == 0) {
            yRes.add((float) 0);
            return yRes;
        }
        yRes.add(y);
        yRes.add(-y);
        return yRes;
    }

    @Override
    public void buildEquation() {
        StringBuilder builder = new StringBuilder("x^2/");
        int ta = (int) a, tb = (int) b;
        if (ta == a) {
            builder.append(ta * ta);
        }
        else {
            builder.append(a * a);
        }
        builder.append(" + y^2/");
        if (tb == b) {
            builder.append(tb * tb);
        }
        else {
            builder.append(b * b);
        }
        builder.append(" = 1");
        equation = builder.toString();
    }


}
