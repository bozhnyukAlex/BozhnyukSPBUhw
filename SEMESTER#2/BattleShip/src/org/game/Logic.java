package org.game;

import javafx.scene.paint.Color;
import org.app.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;


public class Logic {
    private int playerShipsLeft;
    private int enemyShipsLeft;
    private GameState state;
    private ArrayList<Ship> playerShips;
    private ArrayList<Ship> enemyShips;
    private final int INCREASE_MODE = 1;
    private AI enemyAI;
    private FightState fightState;
    private GameMode gameMode;
    public static final int PLAYER_SHIPS = 20;
    public static final int ENEMY_SHIPS = 30;
    private AnnotationConfigApplicationContext context;
    private boolean[] captureTriggers; //срабатывает при нажатии на кнопки выбора корабля
    private int[] enableCounts;
    private int clickCount = 0;



    public Logic(GameMode mode) {
        playerShipsLeft = 10;
        enemyShipsLeft = 10;
        captureTriggers = new boolean[] {false, false, false, false, false};
        enableCounts = new int[] {0, 4, 3, 2, 1};
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

    public void initAiWithContainer(GameField opponentField, IntelligenceLevel il) {
        //context = new AnnotationConfigApplicationContext(Config.class);
        switch (il) {
            case LOW: {
                enemyAI = context.getBean("enemyAILow", AI.class);
                break;
            }
            case MEDIUM: {
                enemyAI = context.getBean("enemyAIMedium", AI.class);
                break;
            }
            case HIGH: {
                enemyAI = context.getBean("enemyAIHigh", AI.class);
                break;
            }
        }
        /*System.out.println(opponentField == enemyAI.getOpponentField());
        enemyAI.setOpponentField(opponentField);*/
    }

    private ArrayList<Ship> autoShipGenerate(GameField field) {
        ArrayList<Ship> resShip = new ArrayList<Ship>();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
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
                    //resShip.add(new Ship(field.getCell(oi, oj)));
                    Ship newShip = context.getBean("ship1", Ship.class);
                    newShip.build(field.getCell(oi, oj));
                    resShip.add(newShip);
                    field.setBusyAroundCell(oi, oj, INCREASE_MODE);
                    break;
                }
            }
        }
        return resShip;
    }

    public boolean canSet(int ci, int cj, Direction dir, int length, GameField field) {
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
        Direction dir;
      //  Ship nwS = new Ship(length);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Ship nwS = context.getBean("ship" + length, Ship.class);
        M: while (true) {
            int twi = rnd.nextInt(GameField.SIZE), twj = rnd.nextInt(GameField.SIZE);
            boolean[] usedDir = new boolean[] {false, false, false, false};
            while(true) {
                dir = generateRandomDir(rnd);
                if (canSet(twi, twj, dir, length, field)) {
                    switch (dir) {
                        case DIR_UP: {
                            for (int i = 0; i < length; i++) {
                                nwS.build(field.getCell(twi - i, twj));
                                field.getCell(twi - i, twj).setCellColor(Color.RED);
                                field.setBusyAroundCell(twi - i, twj, INCREASE_MODE);
                            }
                            break;
                        }
                        case DIR_DOWN: {
                            for (int i = 0; i < length; i++) {
                                nwS.build(field.getCell(twi + i, twj));
                                field.getCell(twi + i, twj).setCellColor(Color.RED);
                                field.setBusyAroundCell(twi + i, twj, INCREASE_MODE);
                            }
                            break;
                        }
                        case DIR_LEFT: {
                            for (int i = 0; i < length; i++) {
                                nwS.build(field.getCell(twi, twj - i));
                                field.getCell(twi, twj - i).setCellColor(Color.RED);
                                field.setBusyAroundCell(twi, twj - i, INCREASE_MODE);
                            }
                            break;
                        }
                        case DIR_RIGHT: {
                            for (int i = 0; i < length; i++) {
                                nwS.build(field.getCell(twi, twj + i));
                                field.getCell(twi, twj + i).setCellColor(Color.RED);
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
                    usedDir[dir.ordinal()] = true;
                    if (usedDir[Direction.DIR_UP.ordinal()] && usedDir[Direction.DIR_DOWN.ordinal()] && usedDir[Direction.DIR_LEFT.ordinal()] && usedDir[Direction.DIR_RIGHT.ordinal()]) {
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

    public int getEnemyShipsLeft() {
        return enemyShipsLeft;
    }

    public GameState getState() {
        return state;
    }

    public void setGameState(GameState gameState) {
        state = gameState;
    }

    public ArrayList<Ship> getShips(int mode) {
        switch (mode) {
            case PLAYER_SHIPS: {
                return playerShips;
            }
            case ENEMY_SHIPS: {
                return enemyShips;
            }
            default: {
                return null;
            }
        }
    }

    public void setShips(ArrayList<Ship> ships, int mode) {
        switch (mode) {
            case PLAYER_SHIPS: {
                playerShips = ships;
                break;
            }
            case ENEMY_SHIPS: {
                enemyShips = ships;
                break;
            }
        }
    }



    public boolean checkPreparation() {
        if (state != GameState.PREPARATION1) {
            return false;
        }
        return playerShips.size() == 10;
    }

    public Ship getShipByDeck(Cell deck, ArrayList<Ship> shipList) {
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

    public void decreaseShips(int mode) {
        switch (mode) {
            case PLAYER_SHIPS: {
                playerShipsLeft--;
                break;
            }
            case ENEMY_SHIPS: {
                enemyShipsLeft--;
                break;
            }
        }
    }

    public void sendToAiSignalAboutDeadShip(boolean isDead) {
        enemyAI.setShipDead(isDead);
    }


    public IntelligenceLevel getDifficulty() {
        return enemyAI.getILevel();
    }

    public void setDifficulty(IntelligenceLevel difficulty) {
        enemyAI.setILevel(difficulty);
    }

    private Direction generateRandomDir(Random rnd) {
        int pick = rnd.nextInt(Direction.values().length);
        return Direction.values()[pick];
    }

    public void setContext(AnnotationConfigApplicationContext context) {
        this.context = context;
    }

    private void setTrigger(int num, boolean state) { ///ЕСЛИ state == true - то цикл, а иначе можно просто установить
        for (int i = 0; i < captureTriggers.length; i++) {
            if (i != num) {
                captureTriggers[i] = false;
            }
            else {
                captureTriggers[i] = state;
            }
        }
    }
    private void updateEnableCounts() {
        enableCounts[1] = 4;
        enableCounts[2] = 3;
        enableCounts[3] = 2;
        enableCounts[4] = 1;
    }

    private void updateTriggers() {
        captureTriggers[0] = captureTriggers[1] = captureTriggers[2] = captureTriggers[3] = captureTriggers[4] = false;
    }

    public void updateParams() {
        updateEnableCounts();
        updateTriggers();
    }

    public void processShipEnableClick(int num) {
        setTrigger(num, true);
    }



    public int getEnableCounts(int decksCount) {
        return enableCounts[decksCount];
    }

    public void fieldClick() {
        clickCount++;
    }

    public int fieldClickCount() {
        return clickCount;
    }

    public void autoGenerate(GameField field) {
        switch (field.getAccessory()) {
            case GameField.PLAYER_MODE: {
                playerShips = autoShipGenerate(field);
                break;
            }
            case GameField.ENEMY_MODE: {
                enemyShips = autoShipGenerate(field);
                break;
            }
        }
    }

    public boolean playerLose() {
        return playerShipsLeft == 0;
    }

    public boolean enemyLose() {
        return enemyShipsLeft == 0;
    }

    public boolean isPlaying() {
        return state.equals(GameState.PLAYING);
    }

    public boolean firstPreparing() {
        return state.equals(GameState.PREPARATION1);
    }

    public boolean secondPreparing() {
        return state.equals(GameState.PREPARATION2);
    }

    public boolean isPlayerMove() {
        return fightState.equals(FightState.PLAYER_MOVE);
    }

    public boolean isEnemyMove() {
        return fightState.equals(FightState.ENEMY_MOVE);
    }

    public boolean isOnePlayerMode() {
        return gameMode.equals(GameMode.ONE_PLAYER);
    }

    public boolean isTwoPlayersMode() {
        return gameMode.equals(GameMode.TWO_PLAYERS);
    }

    public void processShot(int shotI, int shotJ, GameField field) {
        field.getCell(shotI, shotJ).setShot(true);
        if (field.getCell(shotI, shotJ).isDeck()) {
            Ship firedShip = new Ship();
            if (field.ofPlayer()) {
                firedShip = getShipByDeck(field.getCell(shotI, shotJ), playerShips);
            }
            else if (field.ofEnemy()) {
                firedShip = getShipByDeck(field.getCell(shotI, shotJ), enemyShips);
            }
            firedShip.getDamage();
            if (firedShip.isDestroyed()) {
                if (field.ofPlayer()) {
                    if (gameMode.equals(GameMode.ONE_PLAYER)) {
                        field.drawShip(firedShip, Color.DARKOLIVEGREEN);
                        sendToAiSignalAboutDeadShip(true);
                    }
                    else if (gameMode.equals(GameMode.TWO_PLAYERS)) {
                        field.drawShip(firedShip, Color.RED);
                    }
                    if (playerShipsLeft == 0) {
                        setGameState(GameState.END);
                    }
                }
                else if (field.ofEnemy()) {
                    field.drawShip(firedShip, Color.RED);
                    decreaseShips(ENEMY_SHIPS);
                    if (enemyShipsLeft == 0) {
                        // isEnd = true;
                        setGameState(GameState.END);
                    }
                }
            }
            else { // если не уничтожен
                field.getCell(shotI, shotJ).drawDamaged(field.getGraphicsContext2D());
                if (gameMode.equals(GameMode.ONE_PLAYER)) {
                    sendToAiSignalAboutDeadShip(false);
                }
            }
        }
        else { //рисуем воду
            field.getCell(shotI, shotJ).drawWater(field.getGraphicsContext2D());
            if (field.ofPlayer()) {
                setFightState(FightState.PLAYER_MOVE);
            }
            else if (field.ofEnemy()) {
                setFightState(FightState.ENEMY_MOVE);
            }
        }
    }



}
