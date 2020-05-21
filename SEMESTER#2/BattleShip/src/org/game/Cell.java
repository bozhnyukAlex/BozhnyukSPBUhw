package org.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.app.Condition;

public class Cell {
    private int x, y;
    public static final int SIZE = 24;
    private boolean isDeck;
    private boolean isShot;
    private int busyCount;
    private Color cellColor;
    private Condition condition;


    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        isDeck = false;
        isShot = false;
        busyCount = 0;
        condition = Condition.EMPTY;
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

        /*if (isEmpty()) {
            if (needFill) {
                gc.setFill(getCellColor());
                gc.fillRect(x, y, SIZE, SIZE);
            }
            gc.setStroke(Color.BLUE);
            gc.strokeRect(x, y, SIZE, SIZE);
            return;
        }
        gc.setFill(getCellColor());
        gc.fillRect(x + 1, y + 1, SIZE - 1, SIZE - 1);*/

    }


    public void drawShipDeck(GraphicsContext gc, Color color) {
        gc.setFill(color);
        setCellColor(color);
        gc.fillRect(x + 1, y + 1, SIZE - 1, SIZE - 1);

    }

    public void drawWater(GraphicsContext gc) {
        gc.setFill(Color.TURQUOISE);
      //  setCellColor(Color.TURQUOISE);
        gc.fillRect(x + 1, y + 1, SIZE - 1, SIZE - 1);
    }

    public void drawDamaged(GraphicsContext gc) {
        gc.setFill(Color.ORANGE);
      //  setCellColor(Color.ORANGE);
        gc.fillRect(x + 1, y + 1, SIZE - 1, SIZE - 1);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getBusyCount() {
        return busyCount;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
        switch (condition) {
            case EMPTY: {
                setCellColor(Color.color(0.96F, 0.96F, 0.96F));
                break;
            }
            case SHOT_WATER:
            case SHIP_AROUND: {
                setCellColor(Color.TURQUOISE);
                break;
            }
            case SHIP_DAMAGED: {
                setCellColor(Color.ORANGE);
                break;
            }
            case SHIP_KILLED_ENEMY:
            case SHIP_KILLED_TWO_PLAYERS: {
                setCellColor(Color.RED);
                break;
            }
            case SHIP_KILLED_PLAYER: {
                setCellColor(Color.DARKOLIVEGREEN);
                break;
            }

        }
    }

    public boolean isEmpty() {
        return condition.equals(Condition.EMPTY);
    }
    public boolean isShotWater() {
        return condition.equals(Condition.SHOT_WATER);
    }
    public boolean isDamagedDeck() {
        return condition.equals(Condition.SHIP_DAMAGED);
    }
    public boolean isShipKilledPlayer() {
        return condition.equals(Condition.SHIP_KILLED_PLAYER);
    }
    public boolean isShipKilledPlayerTwo() {
        return condition.equals(Condition.SHIP_KILLED_TWO_PLAYERS);
    }
    public boolean isShipKilledEnemy() {
        return condition.equals(Condition.SHIP_KILLED_ENEMY);
    }
    public boolean isAroundShip() {
        return condition.equals(Condition.SHIP_AROUND);
    }
    public boolean isNotShotDeck() {
        return condition.equals(Condition.SHIP_DECK);
    }

    public boolean isShipKilledDeck() {
        return isShipKilledEnemy() || isShipKilledPlayer() || isShipKilledPlayerTwo();
    }

}
