package GamePack;

import java.util.ArrayList;

public class Logic {
    private int playerShipsLeft;
    private int enemyShipsLeft;
    private GameState state;
    ArrayList<Ship> playerShips;

    public Logic() {
        playerShipsLeft = 10;
        enemyShipsLeft = 10;
        playerShips = new ArrayList<Ship>();
    }

    public void addPlayerShip(Ship s) {
        playerShips.add(s);
    }

    public int getPlayerShipsLeft() {
        return playerShipsLeft;
    }

    public ArrayList<Ship> getPlayerShips() {
        return playerShips;
    }

    public int getEnemyShipsLeft() {
        return enemyShipsLeft;
    }

    public GameState getState() {
        return state;
    }

    public void preparation() {
        state = GameState.PREPARATION;
    }

    public void gameStart() {
        state = GameState.PLAYING;
    }

    public boolean checkPreparation() {
        if (state != GameState.PREPARATION) {
            return false;
        }
        return playerShips.size() == 10;
    }

}
