package GamePack;

public class Logic {
    int playerShipsLeft;
    int enemyShipsLeft;
    GameState state;

    public Logic() {
        playerShipsLeft = 10;
        enemyShipsLeft = 10;
    }

    public int getPlayerShipsLeft() {
        return playerShipsLeft;
    }

    public int getEnemyShipsLeft() {
        return enemyShipsLeft;
    }

    void start() {
        state = GameState.PREPARATION;
    }

}
