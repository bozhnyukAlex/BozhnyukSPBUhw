package app;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import math.Curve;

import java.util.ArrayList;

public class DrawingField extends Canvas {
    private Point2D zeroPoint;
    private float scale = 1f;
    private int pixStep;
    private Pane parentPane;
    private Polyline[] polylineInQuarter;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;


    public DrawingField() {
        setWidth(WIDTH);
        setHeight(HEIGHT);
        zeroPoint = new Point2D(getWidth() / 2 - 2, getHeight() / 2 - 2);
        setLayoutX(275);
        setLayoutY(85);
        drawCoordinateSystem(getStep());
        polylineInQuarter = new Polyline[4];
    }

    private float getStep() {
        float step;
        if (scale == 0.1f) {
            step = 12f;
        }
        else if (scale == 0.2f) {
            step = 5f;
        }
        else if (scale > 0.2f && scale < 1f) {
            step = 3f;
        }
        else if (scale >= 1f && scale < 2f) {
            step = 1f;
        }
        else if (scale >= 2f && scale <= 3f){
            step = 0.5f;
        }
        else {
            throw new IllegalArgumentException("Wrong Scale!");
        }
        return step;
    }


    public void drawCoordinateSystem(float step) {
        float midWidth = (float) (getWidth() / 2), midHeight = (float) (getHeight() / 2);
        GraphicsContext gc = getGraphicsContext2D();
        drawZeroPoint();
        gc.setLineWidth(1);
        gc.strokeLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        gc.strokeLine(getWidth() / 2, 0, getWidth() / 2, getHeight());

        pixStep = (int) (midWidth / 10 * scale);
        float delta = pixStep * step;
        for (float x = midWidth + delta; x < getWidth(); x += delta) {
            gc.strokeLine(x, midHeight - 5, x, midHeight + 5);
            drawNumX((x - midWidth) / pixStep);
        }
        for (float x = midWidth - delta; x >= 0; x -= delta) {
            gc.strokeLine(x, midHeight - 5, x, midHeight + 5);
            drawNumX((x - midWidth) / pixStep);
        }
        for (float y = midHeight + delta; y < getHeight(); y += delta) {
            gc.strokeLine(midWidth - 5, y, midWidth + 5, y);
            drawNumY((y - midHeight) / pixStep);
        }
        for (float y = midHeight - delta; y >= 0; y -= delta) {
            gc.strokeLine(midWidth - 5, y, midWidth + 5, y);
            drawNumY((y - midHeight) / pixStep);
        }
    }

    private void drawNumX(float num) { // num == step * k
        GraphicsContext gc = getGraphicsContext2D();
        //int number = Math.round(num);
        num *= 10;
        int d = Math.round(num);
        num = (float) d / 10;
        gc.setLineWidth(2.0);
        gc.setFill(Color.BLACK);
        if (num == Math.ceil(num)) {
            gc.fillText(Integer.toString((int) num), getWidth() / 2 + num * pixStep - 5, getHeight() / 2 + 18);
        }
        else {
            gc.fillText(Float.toString(num), getWidth() / 2 + num * pixStep - 5, getHeight() / 2 + 18);
        }
    }

    private void drawNumY(float num) {
        GraphicsContext gc = getGraphicsContext2D();
        num *= 10;
        int d = Math.round(num);
        num = (float) d / 10;
        gc.setLineWidth(2.0);
        gc.setFill(Color.BLACK);
        if (num == Math.ceil(num)) {
            gc.fillText(Integer.toString((int) -num), getWidth() / 2 + 15, getHeight() / 2 + num * pixStep + 5);
        }
        else {
            gc.fillText(Float.toString(-num), getWidth() / 2 + 15, getHeight() / 2 + num * pixStep + 5);
        }
    }

    private void drawZeroPoint() {
        GraphicsContext graph = getGraphicsContext2D();
        graph.setFill(Color.BLACK);
        graph.fillOval(zeroPoint.getX(), zeroPoint.getY(), 4, 4);
    }

    private void drawQuarter(ArrayList<Point2D> points, int qNum) {
        Polyline polyline = new Polyline();
        for (Point2D point : points) {
            double sendX = point.getX() + getWidth() / 2;
            double sendY = point.getY() + getHeight() / 2;
            if (inRange(sendX, sendY)) {
                polyline.getPoints().add(sendX);
                polyline.getPoints().add(sendY);
            }

        }
        polyline.setStroke(Color.RED);
        polylineInQuarter[qNum - 1] = polyline;
        parentPane.getChildren().add(polyline);

    }
    public void drawCurve(Curve curve) {
        ArrayList<Point2D>[] quarters = curve.makePoints(pixStep);

        drawQuarter(quarters[0], 1);
        drawQuarter(quarters[1], 2);
        drawQuarter(quarters[2], 3);
        drawQuarter(quarters[3], 4);
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void incScale() {
        this.scale += 0.1f;
        scale *= 10;
        int d = Math.round(scale);
        scale = (float) d / 10;
        if (scale > 3f) {
            scale = 3f;
        }
    }

    public void decScale() {
        this.scale -= 0.1f;
        scale *= 10;
        int d = Math.round(scale);
        scale = (float) d / 10;
        if (scale < 0.1f) {
            scale = 0.1f;
        }
    }
    public void draw(Curve curve) {
        removeOldPolyline();
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        drawCoordinateSystem(getStep());
        drawCurve(curve);
    }

    public float getScale() {
        return this.scale;
    }

    public void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    private boolean inRange(double x, double y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }

    private void removeOldPolyline() {
        for (Polyline polyline : polylineInQuarter) {
            parentPane.getChildren().remove(polyline);
        }
    }

}
