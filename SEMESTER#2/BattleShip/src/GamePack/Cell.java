package GamePack;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cell {
    public int x, y;
    public static final int SIZE = 24;


    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(GraphicsContext gc, int x, int y) {
        gc.setStroke(Color.BLUE);
        gc.strokeRect(x, y, SIZE, SIZE);
    }





}
