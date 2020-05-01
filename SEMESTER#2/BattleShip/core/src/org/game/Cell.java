package org.game;

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

    public int getI() {
        return y / SIZE;
    }

    public int getJ() {
        return x / SIZE;
    }

    public void draw(GraphicsContext gc, boolean needFill) {
        if (needFill) {
            gc.setFill(Color.color(0.96F, 0.96F, 0.96F));
            gc.fillRect(x, y, SIZE, SIZE);
        }
        gc.setStroke(Color.BLUE);
        gc.strokeRect(x, y, SIZE, SIZE);
    }


    public void drawShipDeck(GraphicsContext gc, Color color) {
        gc.setFill(color);
        gc.fillRect(x + 1, y + 1, SIZE - 1, SIZE - 1);

    }

    public void drawWater(GraphicsContext gc) {
        gc.setFill(Color.TURQUOISE);
        setCellColor(Color.TURQUOISE);
        gc.fillRect(x + 1, y + 1, SIZE - 1, SIZE - 1);
    }

    public void drawDamaged(GraphicsContext gc) {
        gc.setFill(Color.ORANGE);
        gc.fillRect(x + 1, y + 1, SIZE - 1, SIZE - 1);
    }

    public int getBusyCount() {
        return busyCount;
    }
}
