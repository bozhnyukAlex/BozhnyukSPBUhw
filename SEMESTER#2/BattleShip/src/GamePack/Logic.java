package GamePack;

import java.util.ArrayList;
import java.util.Random;

public class  Logic {
    private int playerShipsLeft;
    private int enemyShipsLeft;
    private GameState state;
    private ArrayList<Ship> playerShips;
    private ArrayList<Ship> enemyShips;
    private final int DIR_UP = 0;
    private final int DIR_DOWN = 1;
    private final int DIR_LEFT = 2;
    private final int DIR_RIGHT = 3;
    private final int INCREASE_MODE = 1;
    private AI enemyAI;
    private FightState fightState;
    private GameMode gameMode;


    public Logic(GameMode mode) {
        playerShipsLeft = 10;
        enemyShipsLeft = 10;
        switch (mode) {
            case ONE_PLAYER: {
                playerShips = new ArrayList<Ship>();
                break;
            }
            case TWO_PLAYERS: {
                playerShips = new ArrayList<Ship>();
                enemyShips = new ArrayList<Ship>();
            }
        }
        gameMode = mode;
    }

    public void initAI(GameField opponentField, IntelligenceLevel il) {
        enemyAI = new AI(opponentField, il);
    }

    public ArrayList<Ship> autoShipGenerate(GameField field) {
        ArrayList<Ship> resShip = new ArrayList<Ship>();
        Random rnd = new Random();
        resShip.add(getRandomShip(4, field));
        for (int k = 0; k < 2; k++) {
            resShip.add(getRandomShip(3, field));
        }
        for (int k = 0; k < 3; k++) {
            resShip.add(getRandomShip(2, field));
        }
        for (int k = 0; k < 4; k++) {
            while (true) {
                int oi = rnd.nextInt(GameField.SIZE), oj = rnd.nextInt(GameField.SIZE);
                if (!field.getCell(oi, oj).isBusy()) {
                    resShip.add(new Ship(field.getCell(oi, oj)));
                    field.setBusyAroundCell(oi, oj, INCREASE_MODE);
                    break;
                }
            }
        }
        return resShip;
    }

    public boolean canSet(int ci, int cj, int dir, int length, GameField field) {
        switch (dir) {
            case DIR_UP: {
                if (ci - length + 1 < 0) {
                    return false;
                }
                for (int i = 0; i < length; i++) {
                    if (field.getCell(ci - i, cj).isBusy()) {
                        return false;
                    }
                }
                break;
            }
            case DIR_DOWN: {
                if (ci + length - 1 >= GameField.SIZE) {
                    return false;
                }
                for (int i = 0; i < length; i++) {
                    if (field.getCell(ci + i, cj).isBusy()) {
                        return false;
                    }
                }
                break;
            }
            case DIR_LEFT: {
                if (cj - length + 1 < 0) {
                    return false;
                }
                for (int i = 0; i < length; i++) {
                    if (field.getCell(ci, cj - i).isBusy()) {
                        return false;
                    }
                }
                break;
            }
            case DIR_RIGHT: {
                if (cj + length - 1 >= GameField.SIZE) {
                    return false;
                }
                for (int i = 0; i < length; i++) {
                    if (field.getCell(ci, cj + i).isBusy()) {
                        return false;
                    }
                }
                break;
            }
        }
        return true;
    }

    private Ship getRandomShip(int length, GameField field) {
        Random rnd = new Random();
        int dir;
        Ship nwS = new Ship(length);
        M: while (true) {
            int twi = rnd.nextInt(GameField.SIZE), twj = rnd.nextInt(GameField.SIZE);
            boolean[] usedDir = new boolean[] {false, false, false, false};
            while(true) {
                dir = rnd.nextInt(4);
                if (canSet(twi, twj, dir, length, field)) {
                    switch (dir) {
                        case DIR_UP: {
                            //nwS = new Ship(field.getCell(twi,  twj), field.getCell(twi - 1, twj), field.getCell(twi - 2, twj));
                            for (int i = 0; i < length; i++) {
                                nwS.build(field.getCell(twi - i, twj));
                                field.setBusyAroundCell(twi - i, twj, INCREASE_MODE);
                            }
                            break;
                        }
                        case DIR_DOWN: {
                         //   nwS = new Ship(field.getCell(twi,  twj), field.getCell(twi + 1, twj), field.getCell(twi + 2, twj));
                            for (int i = 0; i < length; i++) {
                                nwS.build(field.getCell(twi + i, twj));
                                field.setBusyAroundCell(twi + i, twj, INCREASE_MODE);
                            }
                            break;
                        }
                        case DIR_LEFT: {
                        //    nwS = new Ship(field.getCell(twi,  twj), field.getCell(twi,  twj - 1), field.getCell(twi,  twj - 2));
                            for (int i = 0; i < length; i++) {
                                nwS.build(field.getCell(twi, twj - i));
                                field.setBusyAroundCell(twi, twj - i, INCREASE_MODE);
                            }
                            break;
                        }
                        case DIR_RIGHT: {
                         //   nwS = new Ship(field.getCell(twi,  twj), field.getCell(twi,  twj + 1), field.getCell(twi,  twj + 2));
                            for (int i = 0; i < length; i++) {
                                nwS.build(field.getCell(twi, twj + i));
                                field.setBusyAroundCell(twi, twj + i, INCREASE_MODE);
                            }
                            break;
                        }
                        default: {
                            throw new IllegalStateException("Unexpected value: " + dir);
                        }
                    }
                    break M;
                }
                else {
                    usedDir[dir] = true;
                    if (usedDir[DIR_UP] && usedDir[DIR_DOWN] && usedDir[DIR_LEFT] && usedDir[DIR_RIGHT]) {
                        break;
                    }
                }
            }
        }
        return nwS;
    }


    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void addPlayerShip(Ship s) {
        playerShips.add(s);
    }

    public void addEnemyShip(Ship s) {
        enemyShips.add(s);
    }

    public int getPlayerShipsLeft() {
        return playerShipsLeft;
    }

    public FightState getFightState() {
        return fightState;
    }

    public void setFightState(FightState fightState) {
        this.fightState = fightState;
    }

    public ArrayList<Ship> getPlayerShips() {
        return playerShips;
    }

    public void setPlayerShips(ArrayList<Ship> playerShips) {
        this.playerShips = playerShips;
    }

    public int getEnemyShipsLeft() {
        return enemyShipsLeft;
    }

    public GameState getState() {
        return state;
    }

    public void preparationFirst() {
        state = GameState.PREPARATION1;
    }

    public void preparationSecond() {
        state = GameState.PREPARATION2;
    }

    public void play() {
        state = GameState.PLAYING;
    }

    public ArrayList<Ship> getEnemyShips() {
        return enemyShips;
    }

    public void setEnemyShips(ArrayList<Ship> enemyShips) {
        this.enemyShips = enemyShips;
    }

    public boolean checkPreparation() {
        if (state != GameState.PREPARATION1) {
            return false;
        }
        return playerShips.size() == 10;
    }

    public Ship getShipByDeck (Cell deck, ArrayList<Ship> shipList) {
        for (Ship ship : shipList) {
            if (ship.getDecks().contains(deck)) {
                return ship;
            }
        }
        return null;
    }

    public Cell makeAiAttack() {
        return enemyAI.makeShot();
    }

    public void decreaseEnemyShips() {
        enemyShipsLeft--;
    }


    public void decreasePlayerShips() {
        playerShipsLeft--;
    }

    public void sendToAiSignalAboutDeadShip(boolean isDead) {
        enemyAI.setShipDead(isDead);
    }

    public void tellAiAboutDestroyedShip(int deckCount) {
        enemyAI.increaseFiredShip(deckCount);
    }

    public void changeDifficulty(IntelligenceLevel level) {
        enemyAI.setILevel(level);
    }

}
