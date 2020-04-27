package AppInterface;

import GamePack.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BattleShip extends Application {

    @FXML
    public VBox pane;
    @FXML
    private AnchorPane settingsPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button autoGenerateButton;
    @FXML
    private Button onePlayerButton;
    @FXML
    private Button twoPlayersButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label statusLabel;
    private GameField playerField;
    private GameField enemyField;
    @FXML
    private Button enable4Ship;
    @FXML
    private Button enable3Ship;
    @FXML
    private Button enable2Ship;
    @FXML
    private Button enable1Ship;
    @FXML
    private Button readyButton;
    @FXML
    private Label playerShipsLeft;
    @FXML
    private Label enemyShipsLeft;
    @FXML
    private Pane enemyPane;
    @FXML
    private Label oneShipToGoLab;
    @FXML
    private Label twoShipToGoLab;
    @FXML
    private Label threeShipToGoLab;
    @FXML
    private Label fourShipToGoLab;

    private ContextMenu deleteMenu;
    private MenuItem itemDelete;
    private boolean[] captureTriggers; //срабатывает при нажатии на кнопки выбора корабля
    private int clickCount;
    private Logic logic;
    private int oneShipToGo, twoShipToGo, threeShipToGo, fourShipToGo;
    private boolean isEnd = true;
    private IntelligenceLevel levelToSend;

    private final String PLAYER_FIELD_ID = "playerField";
    private final String ENEMY_FIELD_ID = "enemyField";
    public static final String DELETE_MENU_ID = "deleteMenu";
    public static final String TITLE = "Морской бой";
    public static final String CELL_IS_BUSY = "Сюда ставить нельзя";
    public static final String SET_DIR = "Нажмите на поле еще раз для установки направления";
    public static final String CHOOSE_SHIP = "Выберите корабль";
    public static final String DELETE_SHIP = "Удалить корабль";
    public static final String YOU_ARE_READY = "Вы готовы к бою!";
    public static final String YOUR_MOVE = "Ваш ход!";
    public static final String ENEMY_MOVE = "Ход противника!";
    public static final String YOU_WON = "Вы победили!";
    public static final String YOU_LOSE = "Вы проиграли!";
    public static final String FIGHT = "Бой!";
    public static final String PREPARE = "Подготовка";
    public static final String PREPARE_FIRST = "Подготовка первого игрока";
    public static final String PREPARE_SECOND = "Подготовка второго игрока";
    public static final String MOVE_FIRST = "Ход первого игрока";
    public static final String MOVE_SECOND = "Ход второго игрока";
    public static final String FIRST_WON = "Выиграл первый игрок";
    public static final String SECOND_WON = "Выиграл второй игрок";
    public static final String EDIT_AI = "Уровень сложности";
    public static final String LEVEL_EDITED = "Уровень сложности изменен";
    public static final String SET_SHIP_1 = "Поставьте 1 палубный корабль";
    public static final String SET_SHIP_2 = "Поставьте 2-х палубный корабль";
    public static final String SET_SHIP_3 = "Поставьте 3-х палубный корабль";
    public static final String SET_SHIP_4 = "Поставьте 4-х палубный корабль";
    public static final String MENU_PATH = "/view/battleMenu.fxml";
    public static final String SETTINGS_PATH = "/view/settings.fxml";
    private static final String ICON_PATH = "/images/icon.png";

    private final int TO_BUTTON_PANE = 5;
    private final int TO_ENEMY_FIELD = 6;
    private final int DECREASE_PLAYER = 7;
    private final int DECREASE_ENEMY = 8;

    @Override
    public void start(Stage stage) throws Exception {
       // FXMLLoader loader = new FXMLLoader();
       // loader.setLocation(BattleShip.class.getResource("battleMenu.fxml"));
        pane = FXMLLoader.load(BattleShip.class.getResource("battleMenu.fxml"));
        Scene startScene = new Scene(pane, 810, 435);
        stage.setScene(startScene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle(TITLE);
        stage.getIcons().add(new Image(getClass().getResourceAsStream(ICON_PATH)));
        stage.show();
    }

    @FXML
    public void initialize() {
        captureTriggers = new boolean[] {false, false, false, false, false};
        setDisableToButtonsOnSecondField(true);
        playerField = new GameField(GameField.PLAYER_MODE);
        playerField.setId(PLAYER_FIELD_ID);
        enemyField = new GameField();
        anchorPane.getChildren().add(playerField);
        deleteMenu = new ContextMenu();
        deleteMenu.setId(DELETE_MENU_ID);
        itemDelete = new MenuItem(DELETE_SHIP);
        deleteMenu.getItems().add(itemDelete);
        autoGenerateButton.setDisable(true);
        oneShipToGo = Integer.parseInt(oneShipToGoLab.getText());
        twoShipToGo = Integer.parseInt(twoShipToGoLab.getText());
        threeShipToGo = Integer.parseInt(threeShipToGoLab.getText());
        fourShipToGo = Integer.parseInt(fourShipToGoLab.getText());
        settingsButton.setDisable(true);
        levelToSend = IntelligenceLevel.MEDIUM;

        onePlayerButton.setOnAction(actionEvent -> gameStart(GameMode.ONE_PLAYER));
        twoPlayersButton.setOnAction(actionEvent -> gameStart(GameMode.TWO_PLAYERS));

        settingsButton.setOnAction(actionEvent -> {
            boolean okClicked  = showDialogEditAi();
            if (okClicked) {
                levelToSend = logic.getDifficulty();
                statusLabel.setText(LEVEL_EDITED);
            }
        });

        exitButton.setOnAction(actionEvent -> System.exit(0));

        enable1Ship.setOnAction(actionEvent -> {
            statusLabel.setText(SET_SHIP_1);
            clickCount = 0;
            setTrigger(1, true);
        });

        enable2Ship.setOnAction(actionEvent -> {
            statusLabel.setText(SET_SHIP_2);
            clickCount = 0;
            setTrigger(2, true);
        });

        enable3Ship.setOnAction(actionEvent -> {
            statusLabel.setText(SET_SHIP_3);
            setTrigger(3, true);
        });

        enable4Ship.setOnAction(actionEvent -> {
            statusLabel.setText(SET_SHIP_4);
            setTrigger(4,true);
        });

        playerField.setOnMouseClicked(mouseEvent -> {
            if (isEnd) {
                return;
            }
            if (mouseEvent.getButton() == MouseButton.PRIMARY && logic.getState().equals(GameState.PREPARATION1)) {
                int cli = (int) mouseEvent.getY(), clj = (int) mouseEvent.getX();
                cli /= Cell.SIZE;
                clj /= Cell.SIZE;
                setShipToClickedField(playerField, cli, clj);
                hideDeleteMenu();
            }
            else if (mouseEvent.getButton() == MouseButton.PRIMARY && logic.getState().equals(GameState.PLAYING) && logic.getGameMode().equals(GameMode.TWO_PLAYERS) && !isEnd && logic.getFightState().equals(FightState.ENEMY_MOVE)) {
                int plsi = (int) mouseEvent.getY() / Cell.SIZE;
                int plsj = (int) mouseEvent.getX() / Cell.SIZE;
                if (playerField.getCell(plsi, plsj).isShot() && (playerField.getCell(plsi, plsj).isDeck() || playerField.getCell(plsi, plsj).getCellColor().equals(Color.TURQUOISE))) {
                    return;
                }
                makeFieldShot(plsi, plsj, playerField);
            }
        });

        playerField.setOnContextMenuRequested(contextMenuEvent -> {
            int rci = (int) contextMenuEvent.getY() / Cell.SIZE,
                rcj = (int) contextMenuEvent.getX() / Cell.SIZE;
            if (logic.getState().equals(GameState.PREPARATION1)) {
                if (playerField.getCell(rci, rcj).getCellColor().equals(Color.RED) || playerField.getCell(rci, rcj).getCellColor().equals(Color.ORANGE)) {
                    deleteMenu.show(playerField, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
                }
            }
            itemDelete.setOnAction(actionEvent -> deleteShip(rci, rcj, playerField));
        });

        readyButton.setOnAction(actionEvent -> {
            if (logic.getGameMode().equals(GameMode.ONE_PLAYER)) {
                logic.setGameState(GameState.PLAYING);
                enemyField = new GameField(GameField.ENEMY_MODE);
                enemyField.setId(ENEMY_FIELD_ID);
                toggleRightField(TO_ENEMY_FIELD);
                logic.setShips(logic.autoShipGenerate(enemyField), Logic.ENEMY_SHIPS);
                settingsButton.setDisable(true);
                logic.setFightState(FightState.PLAYER_MOVE);
                statusLabel.setText(FIGHT);

                enemyField.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                        if (logic.getState().equals(GameState.PLAYING) && logic.getFightState().equals(FightState.PLAYER_MOVE) && !isEnd) {
                            int plsi = (int) mouseEvent.getY() / Cell.SIZE;
                            int plsj = (int) mouseEvent.getX() / Cell.SIZE;
                            if (enemyField.getCell(plsi, plsj).isShot() && (enemyField.getCell(plsi, plsj).isDeck() || enemyField.getCell(plsi, plsj).getCellColor().equals(Color.TURQUOISE))) {
                                return;
                            }
                            makeFieldShot(plsi, plsj, enemyField);
                            while (logic.getFightState().equals(FightState.ENEMY_MOVE) && !isEnd) {
                                Cell aiShot = logic.makeAiAttack();
                                makeFieldShot(aiShot.getI(), aiShot.getJ(), playerField);
                            }
                        }
                    }
                });
            }
            else if (logic.getGameMode().equals(GameMode.TWO_PLAYERS)) {
                if (logic.getState().equals(GameState.PREPARATION1)) { //если готовился, то должен начать готовиться второй
                    logic.setGameState(GameState.PREPARATION2);
                    enemyField = new GameField(GameField.ENEMY_MODE);
                    enemyField.setId(ENEMY_FIELD_ID);
                    toggleRightField(TO_ENEMY_FIELD);
                    toggleLeftField(TO_BUTTON_PANE);
                    statusLabel.setText(PREPARE_SECOND);
                    updateEnableLabels();
                    setDisableToEnableButtons(false);
                    settingsButton.setDisable(true);
                    enemyField.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getButton() == MouseButton.PRIMARY && logic.getState().equals(GameState.PREPARATION2)) {
                            int cli = (int) mouseEvent.getY(), clj = (int) mouseEvent.getX();
                            cli /= Cell.SIZE;
                            clj /= Cell.SIZE;
                            setShipToClickedField(enemyField, cli, clj);
                            hideDeleteMenu();
                        }
                    });
                    enemyField.setOnContextMenuRequested(contextMenuEvent -> {
                        int rci = (int) contextMenuEvent.getY() / Cell.SIZE, rcj = (int) contextMenuEvent.getX() / Cell.SIZE;
                        if (logic.getState().equals(GameState.PREPARATION2)) {
                            if (enemyField.getCell(rci, rcj).getCellColor().equals(Color.RED) || enemyField.getCell(rci, rcj).getCellColor().equals(Color.ORANGE)) {
                                deleteMenu.show(enemyField, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
                            }
                        }
                        itemDelete.setOnAction(actionEvent1 -> deleteShip(rci, rcj, enemyField));
                    });
                }
                else if (logic.getState().equals(GameState.PREPARATION2)) { //приготовится второй - начинаем игру
                    logic.setGameState(GameState.PLAYING);
                    logic.setFightState(FightState.PLAYER_MOVE);
                    statusLabel.setText(MOVE_FIRST);
                    toggleLeftField(TO_ENEMY_FIELD);
                    playerField.redraw();
                    enemyField.redraw();
                    enemyField.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getButton() == MouseButton.PRIMARY && !isEnd && logic.getFightState().equals(FightState.PLAYER_MOVE) && logic.getGameMode().equals(GameMode.TWO_PLAYERS) && logic.getState().equals(GameState.PLAYING)) {
                            int plsi = (int) mouseEvent.getY() / Cell.SIZE;
                            int plsj = (int) mouseEvent.getX() / Cell.SIZE;
                            if (enemyField.getCell(plsi, plsj).isShot() && (enemyField.getCell(plsi, plsj).isDeck() || enemyField.getCell(plsi, plsj).getCellColor().equals(Color.TURQUOISE))) {
                                return;
                            }
                            makeFieldShot(plsi, plsj, enemyField);
                        }
                    });
                }
            }

        });
        autoGenerateButton.setOnAction(actionEvent -> {
            if (logic.getGameMode().equals(GameMode.ONE_PLAYER)) {
                generateShipsOnField(playerField);
            }
            else if (logic.getGameMode().equals(GameMode.TWO_PLAYERS)) {
                if (logic.getState().equals(GameState.PREPARATION1)) {
                    generateShipsOnField(playerField);
                }
                else if (logic.getState().equals(GameState.PREPARATION2)) {
                    generateShipsOnField(enemyField);
                }
            }
        });
    }

    private void generateShipsOnField(GameField field) {
        field.update();
        if (field.equals(playerField)) {
            logic.setShips(logic.autoShipGenerate(playerField), Logic.PLAYER_SHIPS);
            field.drawShips(logic.getShips(Logic.PLAYER_SHIPS), Color.RED);
        }
        else if (field.equals(enemyField)) {
            logic.setShips(logic.autoShipGenerate(enemyField), Logic.ENEMY_SHIPS);
            field.drawShips(logic.getShips(Logic.ENEMY_SHIPS), Color.RED);
        }
        setZeroToEnableLabelsAndCounts();
        setDisableToEnableButtons(true);
        readyButton.setDisable(false);
        statusLabel.setText(YOU_ARE_READY);
    }

    private void setShipToClickedField(GameField clickedField, int cli, int clj) {
        int INCREASE_BUSY = 1;
        if (getTrigger() == 1) {
            if (clickedField.getCell(cli, clj).isBusy()) {
                statusLabel.setText(CELL_IS_BUSY);
                return;
            }
            clickedField.getCell(cli, clj).drawShipDeck(clickedField.getGraphicsContext2D(), Color.RED);
            clickedField.getCell(cli, clj).setCellColor(Color.RED);
            Ship ship1 = new Ship(1);
            ship1.build(clickedField.getCell(cli, clj));
            if (clickedField.equals(playerField)) {
                logic.addPlayerShip(ship1);
            }
            else if (clickedField.equals(enemyField)) {
                logic.addEnemyShip(ship1);
            }
            clickedField.setBusyAroundCell(cli, clj, INCREASE_BUSY);
            decreaseShipsToGo();
            setLabelAfterSettingShip();
            setTrigger(1, false);
            if (logic.checkPreparation()) {
                readyButton.setDisable(false);
            }
        }
        if (getTrigger() != 1 && getTrigger() != 0) {
            clickCount++;
            if (clickCount == 1) {
                if (clickedField.getCell(cli, clj).isBusy()) {
                    statusLabel.setText(CELL_IS_BUSY);
                    clickCount--;
                    return;
                }
                statusLabel.setText(SET_DIR);
                clickedField.getCell(cli, clj).drawShipDeck(clickedField.getGraphicsContext2D(), Color.ORANGE);
                clickedField.getCell(cli, clj).setCellColor(Color.ORANGE);
                Ship nShip = new Ship(getTrigger());
                nShip.build(clickedField.getCell(cli, clj));
                if (clickedField.equals(playerField)) {
                    logic.addPlayerShip(nShip);
                }
                else if (clickedField.equals(enemyField)) {
                    logic.addEnemyShip(nShip);
                }
            }
            if (clickCount == 2) {
                Ship prevShip = new Ship();
                if (clickedField.equals(playerField)) {
                    prevShip = logic.getShips(Logic.PLAYER_SHIPS).get(logic.getShips(Logic.PLAYER_SHIPS).size() - 1);
                }
                else if (clickedField.equals(enemyField)) {
                    prevShip = logic.getShips(Logic.ENEMY_SHIPS).get(logic.getShips(Logic.ENEMY_SHIPS).size() - 1);
                }
                int pi = prevShip.getDecks().get(0).getY() / Cell.SIZE,  pj = prevShip.getDecks().get(0).getX() / Cell.SIZE;
                clickedField.getCell(pi, pj).setCellColor(Color.RED);
                int di = Math.abs(pi - cli),
                        dj = Math.abs(pj - clj);
                if (cli == pi - di && pj == clj) {
                    if (pi - prevShip.getLength() + 1 < 0) {
                        statusLabel.setText(CELL_IS_BUSY);
                        clickCount--;
                        clickedField.getCell(pi, pj).setCellColor(Color.ORANGE);
                        return;
                    }
                    for (int i = 0; i < prevShip.getLength(); i++) {
                        if (clickedField.getCell(pi - i, pj).isBusy()) {
                            statusLabel.setText(CELL_IS_BUSY);
                            clickCount--;
                            clickedField.getCell(pi, pj).setCellColor(Color.ORANGE);
                            return;
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
                        statusLabel.setText(CELL_IS_BUSY);
                        clickCount--;
                        clickedField.getCell(pi, pj).setCellColor(Color.ORANGE);
                        return;
                    }
                    for (int i = 0; i < prevShip.getLength(); i++) {
                        if (clickedField.getCell(pi + i, pj).isBusy()) {
                            statusLabel.setText(CELL_IS_BUSY);
                            clickCount--;
                            clickedField.getCell(pi, pj).setCellColor(Color.ORANGE);
                            return;
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
                        statusLabel.setText(CELL_IS_BUSY);
                        clickCount--;
                        clickedField.getCell(pi, pj).setCellColor(Color.ORANGE);
                        return;
                    }
                    for (int i = 0; i < prevShip.getLength(); i++) {
                        if (clickedField.getCell(pi, pj - i).isBusy()) {
                            statusLabel.setText(CELL_IS_BUSY);
                            clickCount--;
                            clickedField.getCell(pi, pj).setCellColor(Color.ORANGE);
                            return;
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
                        statusLabel.setText(CELL_IS_BUSY);
                        clickCount--;
                        clickedField.getCell(pi, pj).setCellColor(Color.ORANGE);
                        return;
                    }
                    for (int i = 0; i < prevShip.getLength(); i++) {
                        if (clickedField.getCell(pi, pj + i).isBusy()) {
                            statusLabel.setText(CELL_IS_BUSY);
                            clickCount--;
                            clickedField.getCell(pi, pj).setCellColor(Color.ORANGE);
                            return;
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
                else {
                    clickCount--;
                    if (deleteMenu.isShowing()) {
                        deleteMenu.hide();
                    }
                    return;
                }
                clickCount = 0;
                decreaseShipsToGo();
                setTrigger(2, false);
                setLabelAfterSettingShip();
                if (logic.checkPreparation()) {
                    readyButton.setDisable(false);
                }
            }
        }
    }

    private void makeFieldShot(int plsi, int plsj, GameField field) {
        field.getCell(plsi, plsj).setShot(true);
        if (field.getCell(plsi, plsj).isDeck()) {
            Ship firedShip = new Ship();
            if (field.equals(playerField)) {
                firedShip = logic.getShipByDeck(field.getCell(plsi, plsj), logic.getShips(Logic.PLAYER_SHIPS));
            }
            else if (field.equals(enemyField)) {
                firedShip = logic.getShipByDeck(field.getCell(plsi, plsj), logic.getShips(Logic.ENEMY_SHIPS));
            }
            firedShip.getDamage();
            if (firedShip.isDestroyed()) {
                if (field.equals(playerField)) {
                    if (logic.getGameMode().equals(GameMode.ONE_PLAYER)) {
                        field.drawShip(firedShip, Color.DARKOLIVEGREEN);
                    }
                    else if (logic.getGameMode().equals(GameMode.TWO_PLAYERS)) {
                        field.drawShip(firedShip, Color.RED);
                    }
                    logic.decreaseShips(Logic.PLAYER_SHIPS);
                    if (logic.getGameMode().equals(GameMode.ONE_PLAYER)) {
                        logic.sendToAiSignalAboutDeadShip(true);
                    }
                    decreaseLabelHP(DECREASE_PLAYER);
                    if (logic.getPlayerShipsLeft() == 0) {
                        if (logic.getGameMode().equals(GameMode.ONE_PLAYER)) {
                            statusLabel.setText(YOU_LOSE);
                        }
                        else if (logic.getGameMode().equals(GameMode.TWO_PLAYERS)) {
                            statusLabel.setText(SECOND_WON);
                        }
                        isEnd = true;
                    }
                }
                else if (field.equals(enemyField)) {
                    field.drawShip(firedShip, Color.RED);
                    logic.decreaseShips(Logic.ENEMY_SHIPS);
                    decreaseLabelHP(DECREASE_ENEMY);
                    if (logic.getEnemyShipsLeft() == 0) {
                        if (logic.getGameMode().equals(GameMode.ONE_PLAYER)) {
                            statusLabel.setText(YOU_WON);
                        }
                        else if (logic.getGameMode().equals(GameMode.TWO_PLAYERS)){
                            statusLabel.setText(FIRST_WON);
                        }
                        isEnd = true;
                    }
                }
            }
            else {
                field.getCell(plsi, plsj).drawDamaged(field.getGraphicsContext2D());
                if (logic.getGameMode().equals(GameMode.ONE_PLAYER)){
                    logic.sendToAiSignalAboutDeadShip(false);
                }
            }
        }
        else {
            field.getCell(plsi, plsj).drawWater(field.getGraphicsContext2D());
            if (field.equals(playerField)) {
                if (logic.getGameMode().equals(GameMode.TWO_PLAYERS)) {
                    statusLabel.setText(MOVE_FIRST);
                }
                logic.setFightState(FightState.PLAYER_MOVE);
            }
            else if (field.equals(enemyField)) {
                if (logic.getGameMode().equals(GameMode.TWO_PLAYERS)) {
                    statusLabel.setText(MOVE_SECOND);
                }
                logic.setFightState(FightState.ENEMY_MOVE);

            }
        }
    }
    private void setZeroToEnableLabelsAndCounts() {
        oneShipToGo = twoShipToGo = threeShipToGo = fourShipToGo = 0;
        oneShipToGoLab.setText("0");
        twoShipToGoLab.setText("0");
        threeShipToGoLab.setText("0");
        fourShipToGoLab.setText("0");
    }

    private void setLabelAfterSettingShip() {
        if (oneShipToGo == 0 && twoShipToGo == 0 && threeShipToGo == 0 && fourShipToGo == 0) {
            statusLabel.setText(YOU_ARE_READY);
        }
        else {
            statusLabel.setText(CHOOSE_SHIP);
        }
    }

    private void setDisableToButtonsOnSecondField(boolean state) {
        enable1Ship.setDisable(state);
        enable2Ship.setDisable(state);
        enable3Ship.setDisable(state);
        enable4Ship.setDisable(state);
        readyButton.setDisable(state);
    }

    private void setDisableToEnableButtons(boolean state) {
        enable1Ship.setDisable(state);
        enable2Ship.setDisable(state);
        enable3Ship.setDisable(state);
        enable4Ship.setDisable(state);
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

    private void hideDeleteMenu() {
        if (deleteMenu.isShowing()) {
            deleteMenu.hide();
        }
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

    private void decreaseShipsToGo() {
        switch (getTrigger()) {
            case 1: {
                oneShipToGoLab.setText(Integer.toString(--oneShipToGo));
                if (oneShipToGo <= 0) {
                    enable1Ship.setDisable(true);
                }
                break;
            }
            case 2: {
                twoShipToGoLab.setText(Integer.toString(--twoShipToGo));
                if (twoShipToGo <= 0) {
                    enable2Ship.setDisable(true);
                }
                break;
            }
            case 3: {
                threeShipToGoLab.setText(Integer.toString(--threeShipToGo));
                if (threeShipToGo <= 0) {
                    enable3Ship.setDisable(true);
                }
                break;
            }
            case 4: {
                fourShipToGoLab.setText(Integer.toString(--fourShipToGo));
                if (fourShipToGo <= 0) {
                    enable4Ship.setDisable(true);
                }
                break;
            }
        }
    }

    private void updateEnableLabels() {
        oneShipToGo = 4;
        twoShipToGo = 3;
        threeShipToGo = 2;
        fourShipToGo = 1;
        oneShipToGoLab.setText("4");
        twoShipToGoLab.setText("3");
        threeShipToGoLab.setText("2");
        fourShipToGoLab.setText("1");
    }

    private void updateTriggers() {
        captureTriggers[0] = captureTriggers[1] = captureTriggers[2] = captureTriggers[3] = captureTriggers[4] = false;
    }

    private void deleteAllDecks(int di, int dj, GameField field) {
        if (field.getCell(di, dj).getCellColor().equals(Color.RED)) {
            field.getCell(di, dj).setCellColor(Color.WHITE);
            field.getCell(di, dj).setDeck(false);
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
            field.getCell(di, dj).setCellColor(Color.WHITE);
            field.getCell(di, dj).draw(field.getGraphicsContext2D(), true);
        }
    }

    private void toggleRightField(int mode) {
        switch (mode) {
            case TO_BUTTON_PANE: {
                anchorPane.getChildren().remove(enemyField);
                anchorPane.getChildren().add(enemyPane);
                break;
            }
            case TO_ENEMY_FIELD: {
                anchorPane.getChildren().remove(enemyPane);
                anchorPane.getChildren().add(enemyField);
                break;
            }
        }
    }

    private void toggleLeftField(int mode) {
        switch (mode) {
            case TO_BUTTON_PANE: {
                anchorPane.getChildren().remove(playerField);
                enemyPane.setLayoutX(95);
                enemyPane.setLayoutY(126);
                anchorPane.getChildren().add(enemyPane);
                break;
            }
            case TO_ENEMY_FIELD: {
                enemyPane.setLayoutX(367);
                enemyPane.setLayoutY(126);
                anchorPane.getChildren().remove(enemyPane);
                anchorPane.getChildren().add(playerField);
                break;
            }
        }
    }

    private void deleteShip(int di, int dj, GameField field) {
        if (field.getCell(di, dj).getCellColor().equals(Color.ORANGE)) {
            deleteAllDecks(di, dj, field);
            if (field.equals(playerField)) {
                logic.getShips(Logic.PLAYER_SHIPS).remove(logic.getShips(Logic.PLAYER_SHIPS).size() - 1);
            }
            else if (field.equals(enemyField)) {
                logic.getShips(Logic.ENEMY_SHIPS).remove(logic.getShips(Logic.ENEMY_SHIPS).size() - 1);
            }
            clickCount--;
            return;
        }
        int shipLength = 0;
        if (field.equals(playerField)) {
            for (Ship ship : logic.getShips(Logic.PLAYER_SHIPS)) {
                if (ship.hasDeckWithThisCoordinates(di, dj)) {
                    shipLength = ship.getLength();
                    logic.getShips(Logic.PLAYER_SHIPS).remove(ship);
                    break;
                }
            }
        }
        else if (field.equals(enemyField)){
            for (Ship ship : logic.getShips(Logic.ENEMY_SHIPS)) {
                if (ship.hasDeckWithThisCoordinates(di, dj)) {
                    shipLength = ship.getLength();
                    logic.getShips(Logic.ENEMY_SHIPS).remove(ship);
                    break;
                }
            }
        }

        switch (shipLength) {
            case 1: {
                oneShipToGoLab.setText(Integer.toString(++oneShipToGo));
                enable1Ship.setDisable(oneShipToGo <= 0);
                break;
            }
            case 2: {
                twoShipToGoLab.setText(Integer.toString(++twoShipToGo));
                enable2Ship.setDisable(twoShipToGo <= 0);
                break;
            }
            case 3: {
                threeShipToGoLab.setText(Integer.toString(++threeShipToGo));
                enable3Ship.setDisable(threeShipToGo <= 0);
                break;
            }
            case 4: {
                fourShipToGoLab.setText(Integer.toString(++fourShipToGo));
                enable4Ship.setDisable(fourShipToGo <= 0);
                break;
            }
        }
        if (statusLabel.getText().equals(YOU_ARE_READY)) {
            statusLabel.setText(CHOOSE_SHIP);
        }
        readyButton.setDisable(true);
        deleteAllDecks(di, dj, field );
    }

    private void decreaseLabelHP(int mode) {
        switch (mode) {
            case DECREASE_PLAYER: {
                int playerHPD = Integer.parseInt(playerShipsLeft.getText()) - 1;
                playerShipsLeft.setText(Integer.toString(playerHPD));
                break;
            }
            case  DECREASE_ENEMY: {
                int enemyHPD = Integer.parseInt(enemyShipsLeft.getText()) - 1;
                enemyShipsLeft.setText(Integer.toString(enemyHPD));
                break;
            }
        }
    }

    private boolean showDialogEditAi() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(SETTINGS_PATH));
        try {
            settingsPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Stage dialogStage = new Stage();
        dialogStage.setTitle(EDIT_AI);
        dialogStage.getIcons().add(new Image(getClass().getResourceAsStream(ICON_PATH)));
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(settingsButton.getScene().getWindow());
        Scene scene = new Scene(settingsPane);
        dialogStage.setScene(scene);

        SettingsWindow controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setObservableLogic(logic);

        dialogStage.showAndWait();
        return controller.isOkClicked();

    }

    private void gameStart(GameMode mode) {
        isEnd = false;
        if (logic != null) {
            if (logic.getGameMode().equals(GameMode.ONE_PLAYER)) {
                if (logic.getState().equals(GameState.PLAYING)) {
                    toggleRightField(TO_BUTTON_PANE);
                }
            }
            else if (logic.getGameMode().equals(GameMode.TWO_PLAYERS)) {
                if (logic.getState().equals(GameState.PREPARATION2)) {
                    toggleLeftField(TO_ENEMY_FIELD);
                    toggleRightField(TO_BUTTON_PANE);
                }
                else if (logic.getState().equals(GameState.PLAYING)) {
                    toggleRightField(TO_BUTTON_PANE);
                }
            }
        }
        playerShipsLeft.setText("10");
        enemyShipsLeft.setText("10");
        if (mode.equals(GameMode.ONE_PLAYER)) {
            statusLabel.setText(PREPARE);
        }
        else if (mode.equals(GameMode.TWO_PLAYERS)) {
            statusLabel.setText(PREPARE_FIRST);
        }
        autoGenerateButton.setDisable(false);
        setDisableToButtonsOnSecondField(false);
        readyButton.setDisable(true);
        if (logic != null) {
            if (!logic.getShips(Logic.PLAYER_SHIPS).isEmpty()) {
                playerField.update();
                updateEnableLabels();
                updateTriggers();
            }
        }
        logic = new Logic(mode);
        if (mode.equals(GameMode.ONE_PLAYER)) {
            logic.initAI(playerField, levelToSend);
        }
        logic.setGameState(GameState.PREPARATION1);
        if (mode.equals(GameMode.ONE_PLAYER)) {
            settingsButton.setDisable(false);
        }
        else if (mode.equals(GameMode.TWO_PLAYERS)) {
            settingsButton.setDisable(true);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
