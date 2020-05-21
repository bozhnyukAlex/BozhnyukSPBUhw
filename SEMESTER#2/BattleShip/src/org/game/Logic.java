package org.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.app.Condition;
import org.app.Config;
import org.app.SettingsWindow;
import org.app.StringConst;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    public boolean noEnableButtonsClicked() {
        return !captureTriggers[1] && !captureTriggers[2] && !captureTriggers[3] && !captureTriggers[4];
    }

    public void updateParams() {
        updateEnableCounts();
        updateTriggers();
    }

    public void processShipEnableClick(int num) {
        setTrigger(num, true);
    }

    public int[] getEnableCounts() {
        return enableCounts;
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

    public boolean playing() {
        return state.equals(GameState.PLAYING);
    }

    public boolean playerMove() {
        return fightState.equals(FightState.PLAYER_MOVE);
    }

    public boolean enemyMove() {
        return fightState.equals(FightState.ENEMY_MOVE);
    }

    public boolean firstPreparing() {
        return state.equals(GameState.PREPARATION1);
    }

    public boolean secondPreparing() {
        return state.equals(GameState.PREPARATION2);
    }

    public boolean end() {
        return state.equals(GameState.END);
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

    public boolean allShipsAreReady() {
        return enableCounts[1] == 0 && enableCounts[2] == 0 && enableCounts[3] == 0 && enableCounts[4] == 0;
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
            if (firedShip.isDestroyed()) { // если корабь уничтожен, рисуй его
                field.setWaterAroundDestroyedShip(firedShip);
                if (field.ofPlayer()) {
                    decreaseShips(PLAYER_SHIPS);
                    if (gameMode.equals(GameMode.ONE_PLAYER)) {
                        firedShip.setCondition(Condition.SHIP_KILLED_PLAYER);
                        field.drawShip(firedShip, Color.DARKOLIVEGREEN);
                        sendToAiSignalAboutDeadShip(true);
                    }
                    else if (gameMode.equals(GameMode.TWO_PLAYERS)) {
                        firedShip.setCondition(Condition.SHIP_KILLED_TWO_PLAYERS);
                        field.drawShip(firedShip, Color.RED);
                    }
                    if (playerShipsLeft == 0) {
                        setGameState(GameState.END);
                    }
                }
                else if (field.ofEnemy()) {
                    firedShip.setCondition(Condition.SHIP_KILLED_ENEMY);
                    field.drawShip(firedShip, Color.RED);
                    decreaseShips(ENEMY_SHIPS);
                    if (enemyShipsLeft == 0) {
                        setGameState(GameState.END);
                    }
                }
            }
            else { // если не уничтожен
                field.getCell(shotI, shotJ).setCondition(Condition.SHIP_DAMAGED);
                field.getCell(shotI, shotJ).drawDamaged(field.getGraphicsContext2D());
             //   field.getCell(shotI, shotJ).setCondition(Condition.SHIP_DAMAGED, field.getGraphicsContext2D());
                if (gameMode.equals(GameMode.ONE_PLAYER)) {
                    sendToAiSignalAboutDeadShip(false);
                }
            }
        }
        else { //рисуем воду
            field.getCell(shotI, shotJ).setCondition(Condition.SHOT_WATER);
            field.getCell(shotI, shotJ).drawWater(field.getGraphicsContext2D());
            if (field.ofPlayer()) {
                setFightState(FightState.PLAYER_MOVE);
            }
            else if (field.ofEnemy()) {
                setFightState(FightState.ENEMY_MOVE);
            }
        }
    }

    private void deleteAllDecks(int di, int dj, GameField field) {
        if (field.getCell(di, dj).getCellColor().equals(Color.RED)) {
          //  field.getCell(di, dj).setCellColor(Color.WHITE);
            field.getCell(di, dj).setDeck(false);
            field.getCell(di, dj).setCondition(Condition.EMPTY);
            field.getCell(di, dj).draw(field.getGraphicsContext2D(), true);
            int DECREASE_BUSY = -1;
            field.setBusyAroundCell(di, dj, DECREASE_BUSY);
            if (di - 1 >= 0 && field.getCell(di - 1, dj).getCellColor().equals(Color.RED)) {
                deleteAllDecks(di - 1, dj, field);
            }
            if (di + 1 < GameField.SIZE && field.getCell(di + 1, dj).getCellColor().equals(Color.RED)) { ///don't write else!!!!!
                deleteAllDecks(di + 1, dj, field);
            }
            if (dj + 1 < GameField.SIZE && field.getCell(di, dj + 1).getCellColor().equals(Color.RED)) {
                deleteAllDecks(di, dj + 1, field);
            }
            if (dj - 1 >= 0 && field.getCell(di, dj - 1).getCellColor().equals(Color.RED)) {
                deleteAllDecks(di, dj - 1, field);
            }
        }
        else if (field.getCell(di, dj).getCellColor().equals(Color.ORANGE)) {
           // field.getCell(di, dj).setCellColor(Color.WHITE);
            field.getCell(di, dj).setCondition(Condition.EMPTY);
            field.getCell(di, dj).draw(field.getGraphicsContext2D(), true);
        }
    }

    public int deleteProcessing(int di, int dj, GameField field) {
        if (field.getCell(di, dj).getCellColor().equals(Color.ORANGE)) {
            deleteAllDecks(di, dj, field);
            if (field.ofPlayer()) {
                //logic.getShips(Logic.PLAYER_SHIPS).remove(logic.getShips(Logic.PLAYER_SHIPS).size() - 1);
                playerShips.remove(playerShips.size() - 1);
            }
            else if (field.ofEnemy()) {
                //logic.getShips(Logic.ENEMY_SHIPS).remove(logic.getShips(Logic.ENEMY_SHIPS).size() - 1);
                enemyShips.remove(enemyShips.size() - 1);
            }
            clickCount--;
            return -1;
        }
        int shipLength = 0;
        if (field.ofPlayer()) {
            for (Ship ship : playerShips) {
                if (ship.hasDeckWithThisCoordinates(di, dj)) {
                    shipLength = ship.getLength();
                    //logic.getShips(Logic.PLAYER_SHIPS).remove(ship);
                    playerShips.remove(ship);
                    break;
                }
            }
        }
        else if (field.ofEnemy()){
            for (Ship ship : enemyShips) {
                if (ship.hasDeckWithThisCoordinates(di, dj)) {
                    shipLength = ship.getLength();
                    //logic.getShips(Logic.ENEMY_SHIPS).remove(ship);
                    enemyShips.remove(ship);
                    break;
                }
            }
        }
        deleteAllDecks(di, dj, field);
        enableCounts[shipLength]++;
        return shipLength;
    }

    private int getTrigger() {
        if (captureTriggers[1]) {
            return 1;
        }
        else if (captureTriggers[2]) {
            return 2;
        }
        else if (captureTriggers[3]) {
            return 3;
        }
        else if (captureTriggers[4]) {
            return 4;
        }
        else {
            return 0;
        }
    }


    public String processSettingShip(int cli, int clj, GameField clickedField) {
        int INCREASE_BUSY = 1;
        if (getTrigger() == 1) {
            if (clickedField.getCell(cli, clj).isBusy()) {
                return StringConst.CELL_IS_BUSY;
            }
            clickedField.getCell(cli, clj).drawShipDeck(clickedField.getGraphicsContext2D(), Color.RED);
            clickedField.getCell(cli, clj).setCellColor(Color.RED);
            Ship ship1 = context.getBean("ship1", Ship.class);
            ship1.build(clickedField.getCell(cli, clj));
            if (clickedField.ofPlayer()) {
                addPlayerShip(ship1);
            }
            else if (clickedField.ofEnemy()) {
                addEnemyShip(ship1);
            }
            clickedField.setBusyAroundCell(cli, clj, INCREASE_BUSY);
            enableCounts[1]--;
            setTrigger(1, false);
            if (enableCounts[1] == 0 && enableCounts[2] == 0 && enableCounts[3] == 0 && enableCounts[4] == 0) {
                return StringConst.YOU_ARE_READY;
            }
            else {
                return StringConst.CHOOSE_GAME_MODE;
            }
        }
        if (getTrigger() != 1 && getTrigger() != 0) {
            clickCount++;
            if (clickCount == 1) {
                if (clickedField.getCell(cli, clj).isBusy()) {
                    clickCount--;
                    return StringConst.CELL_IS_BUSY;
                }
                clickedField.getCell(cli, clj).drawShipDeck(clickedField.getGraphicsContext2D(), Color.ORANGE);
                clickedField.getCell(cli, clj).setCellColor(Color.ORANGE);
                //Ship nShip = new Ship(getTrigger());
                Ship nShip = context.getBean("ship" + getTrigger(), Ship.class);
                nShip.build(clickedField.getCell(cli, clj));
                if (clickedField.ofPlayer()) {
                    addPlayerShip(nShip);
                }
                else if (clickedField.ofEnemy()) {
                    addEnemyShip(nShip);
                }
                return StringConst.SET_DIR;
            }
            if (clickCount == 2) {
                Ship prevShip = new Ship();
                if (clickedField.ofPlayer()) {
                    prevShip = playerShips.get(playerShips.size() - 1);
                }
                else if (clickedField.ofEnemy()) {
                    prevShip = enemyShips.get(enemyShips.size() - 1);
                }
                int pi = prevShip.getDecks().get(0).getY() / Cell.SIZE,  pj = prevShip.getDecks().get(0).getX() / Cell.SIZE;
                clickedField.getCell(pi, pj).setCellColor(Color.RED);
                int di = Math.abs(pi - cli),
                        dj = Math.abs(pj - clj);
                if (cli == pi - di && pj == clj) {
                    if (pi - prevShip.getLength() + 1 < 0) {
                        clickCount--;
                        clickedField.getCell(pi, pj).setCellColor(Color.ORANGE);
                        return StringConst.CELL_IS_BUSY;
                    }
                    for (int i = 0; i < prevShip.getLength(); i++) {
                        if (clickedField.getCell(pi - i, pj).isBusy()) {
                            clickCount--;
                            clickedField.getCell(pi, pj).setCellColor(Color.ORANGE);
                            return StringConst.CELL_IS_BUSY;
                        }
                    }
                    for (int i = 0; i < prevShip.getLength(); i++) {
                        clickedField.getCell(pi - i, pj).drawShipDeck(clickedField.getGraphicsContext2D(), Color.RED);
                        clickedField.getCell(pi - i, pj).setCellColor(Color.RED);
                        clickedField.setBusyAroundCell(pi - i, pj, INCREASE_BUSY);
                        if (i != 0) {
                            prevShip.build(clickedField.getCell(pi - i, pj));
                        }
                    }
                }
                else if (cli == pi + di && pj == clj) {
                    if (pi + prevShip.getLength() - 1 >= GameField.SIZE) {
                        clickCount--;
                        clickedField.getCell(pi, pj).setCellColor(Color.ORANGE);
                        return StringConst.CELL_IS_BUSY;
                    }
                    for (int i = 0; i < prevShip.getLength(); i++) {
                        if (clickedField.getCell(pi + i, pj).isBusy()) {
                            clickCount--;
                            clickedField.getCell(pi, pj).setCellColor(Color.ORANGE);
                            return StringConst.CELL_IS_BUSY;
                        }
                    }
                    for (int i = 0; i < prevShip.getLength(); i++) {
                        clickedField.getCell(pi + i, pj).drawShipDeck(clickedField.getGraphicsContext2D(), Color.RED);
                        clickedField.getCell(pi + i, pj).setCellColor(Color.RED);
                        clickedField.setBusyAroundCell(pi + i, pj, INCREASE_BUSY);
                        if (i != 0) {
                            prevShip.build(clickedField.getCell(pi + i, pj));
                        }
                    }
                }
                else if (cli == pi && clj == pj - dj) {
                    if (pj - prevShip.getLength() + 1 < 0) {
                        clickCount--;
                        clickedField.getCell(pi, pj).setCellColor(Color.ORANGE);
                        return StringConst.CELL_IS_BUSY;
                    }
                    for (int i = 0; i < prevShip.getLength(); i++) {
                        if (clickedField.getCell(pi, pj - i).isBusy()) {
                            clickCount--;
                            clickedField.getCell(pi, pj).setCellColor(Color.ORANGE);
                            return StringConst.CELL_IS_BUSY;
                        }
                    }
                    for (int i = 0; i < prevShip.getLength(); i++) {
                        clickedField.getCell(pi, pj - i).drawShipDeck(clickedField.getGraphicsContext2D(), Color.RED);
                        clickedField.getCell(pi, pj - i).setCellColor(Color.RED);
                        clickedField.setBusyAroundCell(pi, pj - i, INCREASE_BUSY);
                        if (i != 0) {
                            prevShip.build(clickedField.getCell(pi, pj - i));
                        }
                    }
                }
                else if (cli == pi && clj == pj + dj) {
                    if (pj + prevShip.getLength() - 1 >= GameField.SIZE) {
                  //      statusLabel.setText(StringConst.CELL_IS_BUSY);
                        clickCount--;
                        clickedField.getCell(pi, pj).setCellColor(Color.ORANGE);
                        return StringConst.CELL_IS_BUSY;
                    }
                    for (int i = 0; i < prevShip.getLength(); i++) {
                        if (clickedField.getCell(pi, pj + i).isBusy()) {
                      //      statusLabel.setText(StringConst.CELL_IS_BUSY);
                            clickCount--;
                            clickedField.getCell(pi, pj).setCellColor(Color.ORANGE);
                            return StringConst.CELL_IS_BUSY;
                        }
                    }
                    for (int i = 0; i < prevShip.getLength(); i++) {
                        clickedField.getCell(pi, pj + i).drawShipDeck(clickedField.getGraphicsContext2D(), Color.RED);
                        clickedField.getCell(pi, pj + i).setCellColor(Color.RED);
                        clickedField.setBusyAroundCell(pi, pj + i, INCREASE_BUSY);
                        if (i != 0) {
                            prevShip.build(clickedField.getCell(pi, pj + i));
                        }
                    }
                }
                else { // если не на прямых
                    clickCount--;
                    return StringConst.CELL_IS_BUSY;
                }
                clickCount = 0;
                enableCounts[prevShip.getLength()]--;
                setTrigger(2, false);
                if (enableCounts[1] == 0 && enableCounts[2] == 0 && enableCounts[3] == 0 && enableCounts[4] == 0) {
                    return StringConst.YOU_ARE_READY;
                }
                else {
                    return StringConst.CHOOSE_SHIP;
                }
                /*if (logic.checkPreparation()) {
                    readyButton.setDisable(false);
                }*/
            }
        }
        return StringConst.CELL_IS_BUSY;
    }





}
