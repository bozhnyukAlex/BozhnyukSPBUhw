package org.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.app.Condition;

public class Cell {
    private int x, y;
    public static final int SIZE = 24;
    private int busyCount;
    private Color cellColor;
    private Condition condition;


    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        busyCount = 0;
        condition = Condition.EMPTY;
    }

    public boolean isBusy() {
        return busyCount != 0;
    }

    public void changeBusyCount(int mode) {
        busyCount += mode;
    }


    public boolean isNotShotDeck() {
        return condition.equals(Condition.SHIP_DECK);
    }

    private void setCellColor(Color cellColor) {
        this.cellColor = cellColor;
    }

    private Color getCellColor() {
        return cellColor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isShot() {
        return isShipKilledDeck() || isShotWater() || isDamagedDeck();
    }


    public int getI() {
        return y / SIZE;
    }

    public int getJ() {
        return x / SIZE;
    }

    public void draw(GraphicsContext gc, boolean needFill) {
        if (isEmpty()) {
            if (needFill) {
                gc.setFill(getCellColor());
                gc.fillRect(x, y, SIZE, SIZE);
            }
            gc.setStroke(Color.BLUE);
            gc.strokeRect(x, y, SIZE, SIZE);
            return;
        }
        gc.setFill(getCellColor());
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
            case SHIP_FIRST_CLICK:
            case SHIP_DAMAGED: {
                setCellColor(Color.ORANGE);
                break;
            }
            case SHIP_DECK:
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

    public boolean isShipKilledDeck() {
        return isShipKilledEnemy() || isShipKilledPlayer() || isShipKilledPlayerTwo();
    }

    public boolean isFirstClickedDeck() {
        return condition.equals(Condition.SHIP_FIRST_CLICK);
    }

}
