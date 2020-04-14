package GamePack;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cell {
    private int x, y;
    public static final int SIZE = 24;
    private boolean isDeck;
    private boolean isShot;
    private int busyCount;
    private Color cellColor;


    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        isDeck = false;
        isShot = false;
        busyCount = 0;
    }

    public boolean isBusy() {
        return busyCount != 0;
    }

    public void changeBusyCount(int mode) {
        busyCount += mode;
    }

    public void setDeck(boolean deck) {
        isDeck = deck;
    }

    public boolean isDeck() {
        return isDeck;
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

    public boolean isShot() {
        return isShot;
    }

    public void setShot(boolean shot) {
        isShot = shot;
    }

    public void draw(GraphicsContext gc, boolean isDelete) {
        if (isDelete) {
            gc.setFill(Color.color(0.96F, 0.96F, 0.96F));
            gc.fillRect(x, y, SIZE, SIZE);
        }
        gc.setStroke(Color.BLUE);
        gc.strokeRect(x, y, SIZE, SIZE);
    }




    public void drawShipDeck(GraphicsContext gc, boolean isFirstClickSet) {
        if (isFirstClickSet) {
            gc.setFill(Color.ORANGE);
        }
        else {
            gc.setFill(Color.RED);
        }
        gc.fillRect(x + 1, y + 1, SIZE - 1, SIZE - 1);

    }

    public void drawWater(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(x + 1, y + 1, SIZE - 1, SIZE - 1);
    }
}
