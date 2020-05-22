package org.game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.app.Condition;

import java.util.ArrayList;


public class GameField extends Canvas {
    public static final int SIZE = 10;
    public static final int PLAYER_MODE = 3;
    public static final int ENEMY_MODE = 4;
    private Cell[][] cells;
    private int accessory; //3 - player, 4 - enemy

    public GameField(int mode) {
        switch (mode) {
            case PLAYER_MODE: {
                setLayoutX(95);
                setLayoutY(126);
                break;
            }
            case ENEMY_MODE: {
                setLayoutX(367);
                setLayoutY(126);
                break;
            }
        }
        accessory = mode;
        setWidth(240);
        setHeight(240);
        initAndDraw();
    }

    public GameField () {}

    public Cell getCell(int i, int j) {
        return cells[i][j];
    }

    public void initAndDraw() {
        cells = new Cell[SIZE][SIZE];
        draw();
    }

    public void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE ; j++) {
                cells[i][j] = new Cell(j * Cell.SIZE, i * Cell.SIZE);
                //cells[i][j].setCellColor(Color.WHITE);
                cells[i][j].draw(gc, false);
            }
        }
    }

    public void redraw() {
        GraphicsContext gc = getGraphicsContext2D();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE ; j++) {
                if (cells[i][j].isNotShotDeck()) {
                    cells[i][j].setCondition(Condition.SHIP_REDRAW);
                    cells[i][j].draw(gc, true);
                }

            }
        }
    }

    public void update() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0,0, getWidth(), getHeight());
        draw();
    }

    public void setBusyAroundCell(int i, int j, int mode) {
        for (int w = -1; w <= 1; w++) {
            for (int v = -1; v <= 1; v++) {
                if (inRange(i + w, j + v)) {
                    cells[i + w][j + v].changeBusyCount(mode);
                }
            }
        }
    }

    public void drawShips(ArrayList<Ship> ships, Color color) {
        for(Ship ship : ships) {
            drawShip(ship, color);
        }
    }

    public void drawShip(Ship ship, Color color) {
        for (Cell deck : ship.getDecks()) {
           // deck.drawShipDeck(getGraphicsContext2D(), color);
            deck.draw(getGraphicsContext2D(), false);
        }
    }

    public void setWaterAroundDestroyedShip(Ship ship) {
        for (Cell cell : ship.getDecks()) {
            for (int w = -1; w <= 1; w++) {
                for (int v = -1; v <= 1; v++) {
                    int i = cell.getI(), j = cell.getJ();
                    if (inRange(i + w, j + v) && !getCell(i + w, j + v).isShipKilledDeck()) {
                        getCell(i + w, j + v).setCondition(Condition.SHIP_AROUND);
                      //  getCell(i + w, j + v).drawWater(getGraphicsContext2D()); //draw()
                        getCell(i + w, j + v).draw(getGraphicsContext2D(), false);
                    }
                }
            }
        }
    }

    public void setShip(Ship ship) {
        for (Cell deck : ship.getDecks()) {
            cells[deck.getI()][deck.getJ()] = deck;
            setBusyAroundCell(deck.getI(), deck.getJ(), 1);
        }
    }

    public static boolean inRange(int i, int j) {
        return i >= 0 && i < SIZE && j >= 0 && j < SIZE;
    }

    public boolean hasFiredShipAround(int i, int j) {
        for (int w = -1; w <= 1; w++) {
            for (int v = -1; v <= 1; v++) {
                if (inRange(i + w, j + v) && getCell(i + w, j + v).isShipKilledDeck()) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getAccessory() {
        return accessory;
    }

    public boolean ofPlayer() {
        return accessory == GameField.PLAYER_MODE;
    }

    public boolean ofEnemy() {
        return accessory == GameField.ENEMY_MODE;
    }
}
