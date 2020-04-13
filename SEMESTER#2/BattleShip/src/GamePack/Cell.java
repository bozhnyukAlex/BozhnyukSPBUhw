package GamePack;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cell {
    private int x, y;
    public static final int SIZE = 24;
    private boolean isBusy;
    private int busyCount;
    private Color cellColor;


    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        isBusy = false;
        busyCount = 0;
    }

    public boolean isBusy() {
        return busyCount != 0;
    }

    public void changeBusyCount(int mode) {
        busyCount += mode;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public void setCellColor(Color cellColor) {
        this.cellColor = cellColor;
    }

    public Color getCellColor() {
        return cellColor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(GraphicsContext gc, boolean isDelete) {
        if (isDelete) {
            gc.setFill(Color.color(0.96F, 0.96F, 0.96F));
            gc.fillRect(x, y, SIZE, SIZE);
        }
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
        gc.fillRect(x + 1, y + 1, SIZE - 1, SIZE - 1);

    }
}
