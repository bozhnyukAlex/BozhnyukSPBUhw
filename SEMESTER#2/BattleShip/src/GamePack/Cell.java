package GamePack;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cell {
    private int x, y;
    public static final int SIZE = 24;
    private boolean isBusy;
    private Color cellColor;


    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        isBusy = false;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public void setCellColor(GraphicsContext gc, Color cellColor) {

        this.cellColor = cellColor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.BLUE);
        gc.strokeRect(x, y, SIZE, SIZE);
    }




    public void drawShipDeck(GraphicsContext gc, boolean isStart) {
        if (isStart) {
            gc.setFill(Color.ORANGE);
        }
        else {
            gc.setFill(Color.RED);
        }
        gc.fillRect(x+1, y+1, SIZE-1, SIZE-1);

    }
}
