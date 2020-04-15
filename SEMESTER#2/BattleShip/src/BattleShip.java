import GamePack.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BattleShip extends Application {
    private static final int FIELD_SIZE = 10;
    @FXML
    private VBox pane;
    @FXML
    private AnchorPane anchorPane;
    private Scene startScene;
    @FXML
    private Button autoGenerateButton;
    @FXML
    private Button startButton;
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
    private boolean firstClickOnPlayerField, secondClickOnPlayerField;
    private int clickCount;

    private Logic logic;
    private int oneShipToGo, twoShipToGo, threeShipToGo, fourShipToGo;
    private int playerHP, enemyHP;
    private boolean isEnd;

    private final String NO_SHIPS_LEFT = "Кораблей этого типа не осталось!";
    private final String CELL_IS_BUSY = "Сюда ставить нельзя";
    private final String SET_DIR = "Нажмите на поле еще раз для установки направления";
    private final String CHOOSE_SHIP = "Выберите корабль";
    private final String DELETE_SHIP = "Удалить корабль";
    private final String YOU_ARE_READY = "Вы готовы к бою!";
    private final String YOUR_MOVE = "Ваш ход!";
    private final String ENEMY_MOVE = "Ход противника!";
    private final String YOU_WON = "Вы победили!";
    private final String YOU_LOSE = "Вы проиграли!";
    private final String FIGHT = "Бой!";

    private final int INCREASE_BUSY = 1;
    private final int DECREASE_BUSY = -1;
    private final int PLAYER_FIELD = 3;
    private final int ENEMY_FIELD = 4;
    private final int TO_BUTTON_PANE = 5;
    private final int TO_ENEMY_FIELD = 6;
    private final int DECREASE_PLAYER = 7;
    private final int DECREASE_ENEMY = 8;

    @Override
    public void start(Stage stage) throws Exception {
        pane = FXMLLoader.load(getClass().getResource("battlemenu.fxml"));
        startScene = new Scene(pane, 810, 435);
        stage.setScene(startScene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("BattleShip");
        stage.show();
    }

    @FXML
    public void initialize() {
        captureTriggers = new boolean[] {false, false, false, false, false};
        setDisableToButtonsOnSecondField(true);
        playerField = new GameField(PLAYER_FIELD);
        enemyField = new GameField();
        anchorPane.getChildren().add(playerField);
        deleteMenu = new ContextMenu();
        itemDelete = new MenuItem(DELETE_SHIP);
        deleteMenu.getItems().add(itemDelete);
        autoGenerateButton.setDisable(true);

        oneShipToGo = Integer.parseInt(oneShipToGoLab.getText());
        twoShipToGo = Integer.parseInt(twoShipToGoLab.getText());
        threeShipToGo = Integer.parseInt(threeShipToGoLab.getText());
        fourShipToGo = Integer.parseInt(fourShipToGoLab.getText());
        startButton.setOnAction(actionEvent -> {
            if (logic != null && logic.getState().equals(GameState.PLAYING)) {
                toggleRightField(TO_BUTTON_PANE);
            }
            isEnd = false;
            playerShipsLeft.setText("10");
            enemyShipsLeft.setText("10");
            statusLabel.setText("Подготовка");
            autoGenerateButton.setDisable(false);
            setDisableToButtonsOnSecondField(false);
            readyButton.setDisable(true);
            if (logic != null) {
                if (!logic.getPlayerShips().isEmpty()) {
                    playerField.update();
                    updateEnableLabels();
                    updateTriggers();
                }
            }
            logic = new Logic();
            logic.preparation();
        });
        settingsButton.setOnAction(actionEvent -> {
            //anchorPane.getChildren().remove(enemyPane); - Вот так можно убирать элементы
        });
        exitButton.setOnAction(actionEvent -> System.exit(0));

        enable1Ship.setOnAction(actionEvent -> {
            statusLabel.setText("Поставьте 1 палубный корабль");
            clickCount = 0;
            setTrigger(1, true);
        });

        enable2Ship.setOnAction(actionEvent -> {
            statusLabel.setText("Поставьте 2-х палубный корабль");
            clickCount = 0;
            setTrigger(2, true);
        });
        enable3Ship.setOnAction(actionEvent -> {
            statusLabel.setText("Поставьте 3-х палубный корабль");
            setTrigger(3, true);
        });
        enable4Ship.setOnAction(actionEvent -> {
            statusLabel.setText("Поставьте 4-х палубный корабль");
            setTrigger(4,true);
        });
        playerField.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && logic.getState().equals(GameState.PREPARATION)) {
                int cli = (int) mouseEvent.getY(), clj = (int) mouseEvent.getX();
                cli /= Cell.SIZE;
                clj /= Cell.SIZE;
                if (getTrigger() == 1) {
                    if (playerField.getCell(cli, clj).isBusy()) {
                        statusLabel.setText(CELL_IS_BUSY);
                        return;
                    }
                    playerField.getCell(cli, clj).drawShipDeck(playerField.getGraphicsContext2D(), Color.RED);
                    playerField.getCell(cli, clj).setCellColor(Color.RED);
                    Ship ship1 = new Ship(1);
                    ship1.build(playerField.getCell(cli, clj));
                    logic.addPlayerShip(ship1);
                    playerField.setBusyAroundCell(cli, clj, INCREASE_BUSY);
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
                        if (playerField.getCell(cli, clj).isBusy()) {
                            statusLabel.setText(CELL_IS_BUSY);
                            clickCount--;
                            return;
                        }
                        statusLabel.setText(SET_DIR);
                        playerField.getCell(cli, clj).drawShipDeck(playerField.getGraphicsContext2D(), Color.ORANGE);
                        playerField.getCell(cli, clj).setCellColor(Color.ORANGE);
                        Ship nShip = new Ship(getTrigger());
                        nShip.build(playerField.getCell(cli, clj));
                        logic.addPlayerShip(nShip);
                    }
                    if (clickCount == 2) {
                        Ship prevShip = logic.getPlayerShips().get(logic.getPlayerShips().size() - 1);
                        int pi = prevShip.getDecks().get(0).getY() / Cell.SIZE,  pj = prevShip.getDecks().get(0).getX() / Cell.SIZE;
                        playerField.getCell(pi, pj).setCellColor(Color.RED);
                        int di = Math.abs(pi - cli),
                                dj = Math.abs(pj - clj);
                        if (cli == pi - di && pj == clj) {
                            if (pi - prevShip.getLength() + 1 < 0) {
                                statusLabel.setText(CELL_IS_BUSY);
                                clickCount--;
                                playerField.getCell(pi, pj).setCellColor(Color.ORANGE);
                                return;
                            }
                            for (int i = 0; i < prevShip.getLength(); i++) {
                                if (playerField.getCell(pi - i, pj).isBusy()) {
                                    statusLabel.setText(CELL_IS_BUSY);
                                    clickCount--;
                                    playerField.getCell(pi, pj).setCellColor(Color.ORANGE);
                                    return;
                                }
                            }
                            for (int i = 0; i < prevShip.getLength(); i++) {
                                playerField.getCell(pi - i, pj).drawShipDeck(playerField.getGraphicsContext2D(), Color.RED);
                                playerField.getCell(pi - i, pj).setCellColor(Color.RED);
                                playerField.setBusyAroundCell(pi - i, pj, INCREASE_BUSY);
                                if (i != 0) {
                                    prevShip.build(playerField.getCell(pi - i, pj));
                                }
                            }
                        }
                        else if (cli == pi + di && pj == clj) {
                            if (pi + prevShip.getLength() - 1 >= FIELD_SIZE) {
                                statusLabel.setText(CELL_IS_BUSY);
                                clickCount--;
                                playerField.getCell(pi, pj).setCellColor(Color.ORANGE);
                                return;
                            }
                            for (int i = 0; i < prevShip.getLength(); i++) {
                                if (playerField.getCell(pi + i, pj).isBusy()) {
                                    statusLabel.setText(CELL_IS_BUSY);
                                    clickCount--;
                                    playerField.getCell(pi, pj).setCellColor(Color.ORANGE);
                                    return;
                                }
                            }
                            for (int i = 0; i < prevShip.getLength(); i++) {
                                playerField.getCell(pi + i, pj).drawShipDeck(playerField.getGraphicsContext2D(), Color.RED);
                                playerField.getCell(pi + i, pj).setCellColor(Color.RED);
                                playerField.setBusyAroundCell(pi + i, pj, INCREASE_BUSY);
                                if (i != 0) {
                                    prevShip.build(playerField.getCell(pi + i, pj));
                                }
                            }
                        }
                        else if (cli == pi && clj == pj - dj) {
                            if (pj - prevShip.getLength() + 1 < 0) {
                                statusLabel.setText(CELL_IS_BUSY);
                                clickCount--;
                                playerField.getCell(pi, pj).setCellColor(Color.ORANGE);
                                return;
                            }
                            for (int i = 0; i < prevShip.getLength(); i++) {
                                if (playerField.getCell(pi, pj - i).isBusy()) {
                                    statusLabel.setText(CELL_IS_BUSY);
                                    clickCount--;
                                    playerField.getCell(pi, pj).setCellColor(Color.ORANGE);
                                    return;
                                }
                            }
                            for (int i = 0; i < prevShip.getLength(); i++) {
                                playerField.getCell(pi, pj - i).drawShipDeck(playerField.getGraphicsContext2D(), Color.RED);
                                playerField.getCell(pi, pj - i).setCellColor(Color.RED);
                                playerField.setBusyAroundCell(pi, pj - i, INCREASE_BUSY);
                                if (i != 0) {
                                    prevShip.build(playerField.getCell(pi, pj - i));
                                }
                            }
                        }
                        else if (cli == pi && clj == pj + dj) {
                            if (pj + prevShip.getLength() - 1 >= FIELD_SIZE) {
                                statusLabel.setText(CELL_IS_BUSY);
                                clickCount--;
                                playerField.getCell(pi, pj).setCellColor(Color.ORANGE);
                                return;
                            }
                            for (int i = 0; i < prevShip.getLength(); i++) {
                                if (playerField.getCell(pi, pj + i).isBusy()) {
                                    statusLabel.setText(CELL_IS_BUSY);
                                    clickCount--;
                                    playerField.getCell(pi, pj).setCellColor(Color.ORANGE);
                                    return;
                                }
                            }
                            for (int i = 0; i < prevShip.getLength(); i++) {
                                playerField.getCell(pi, pj + i).drawShipDeck(playerField.getGraphicsContext2D(), Color.RED);
                                playerField.getCell(pi, pj + i).setCellColor(Color.RED);
                                playerField.setBusyAroundCell(pi, pj + i, INCREASE_BUSY);
                                if (i != 0) {
                                    prevShip.build(playerField.getCell(pi, pj + i));
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
                hideDeleteMenu();
            }
        });

        playerField.setOnContextMenuRequested(contextMenuEvent -> {
            int rci = (int) contextMenuEvent.getY(),
                rcj = (int) contextMenuEvent.getX();
            rci /= Cell.SIZE;
            rcj /= Cell.SIZE;
            if (playerField.getCell(rci, rcj).getCellColor().equals(Color.RED) || playerField.getCell(rci, rcj).getCellColor().equals(Color.ORANGE)) {
                deleteMenu.show(playerField, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            }

            int finalRci = rci;
            int finalRcj = rcj;
            itemDelete.setOnAction(actionEvent -> deleteShip(finalRci, finalRcj));
        });

        readyButton.setOnAction(actionEvent -> {
            logic.play();
            enemyField = new GameField(ENEMY_FIELD);
            toggleRightField(TO_ENEMY_FIELD);
            logic.setEnemyShips(logic.autoShipGenerate(enemyField));
            logic.initAI(playerField, IntelligenceLevel.LOW);
            logic.setFightState(FightState.PLAYER_MOVE);
            statusLabel.setText(FIGHT);

            enemyField.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (logic.getState().equals(GameState.PLAYING) && logic.getFightState().equals(FightState.PLAYER_MOVE) && !isEnd) {
                        int plsi = (int) mouseEvent.getY() / Cell.SIZE;
                        int plsj = (int) mouseEvent.getX() / Cell.SIZE;
                        if (enemyField.getCell(plsi, plsj).isShot()) {
                            return;
                        }
                        makeFieldShot(plsi, plsj, enemyField);
                        while (logic.getFightState().equals(FightState.ENEMY_MOVE)) {
                            Cell aiShotted = logic.makeAiAttack();
                            makeFieldShot(aiShotted.getY() / Cell.SIZE, aiShotted.getX() / Cell.SIZE, playerField);
                        }
                    }
                }
            });
        });
        autoGenerateButton.setOnAction(actionEvent -> {
            playerField.update();
            logic.setPlayerShips(logic.autoShipGenerate(playerField));
            playerField.drawShips(logic.getPlayerShips(), Color.RED);
            setZeroToEnableLabelsAndCounts();
            setDisableToEnableButtons(true);
            readyButton.setDisable(false);
            statusLabel.setText(YOU_ARE_READY);
        });

    }

    private void makeFieldShot(int plsi, int plsj, GameField field) {
        field.getCell(plsi, plsj).setShot(true);
        if (field.getCell(plsi, plsj).isDeck()) {
            Ship firedShip = new Ship();
            if (field.equals(playerField)) {
                firedShip = logic.getShipByDeck(field.getCell(plsi, plsj), logic.getPlayerShips());
            }
            else if (field.equals(enemyField)) {
                firedShip = logic.getShipByDeck(field.getCell(plsi, plsj), logic.getEnemyShips());
            }
            firedShip.getDamage();
            if (firedShip.isDestroyed()) {
                if (field.equals(playerField)) {
                    field.drawShip(firedShip, Color.DARKOLIVEGREEN);
                    logic.decreasePlayerShips();
                    decreaseLabelHP(DECREASE_PLAYER);
                    if (logic.getPlayerShipsLeft() == 0) {
                        statusLabel.setText(YOU_LOSE);
                        isEnd = true;
                    }
                }
                else if (field.equals(enemyField)) {
                    field.drawShip(firedShip, Color.RED);
                    logic.decreaseEnemyShips();
                    decreaseLabelHP(DECREASE_ENEMY);
                    if (logic.getEnemyShipsLeft() == 0) {
                        statusLabel.setText(YOU_WON);
                        isEnd = true;
                    }
                }
            }
            else {
                field.getCell(plsi, plsj).drawDamaged(field.getGraphicsContext2D());
            }
        }
        else {
            field.getCell(plsi, plsj).drawWater(field.getGraphicsContext2D());
            if (field.equals(playerField)) {
                logic.setFightState(FightState.PLAYER_MOVE);
            }
            else if (field.equals(enemyField)) {
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

    private void setDisableExceptNthButton(int num, boolean state) {
        switch (num) {
            case 1: {
                enable2Ship.setDisable(state);
                enable3Ship.setDisable(state);
                enable4Ship.setDisable(state);
                break;
            }
            case 2: {
                enable1Ship.setDisable(state);
                enable3Ship.setDisable(state);
                enable4Ship.setDisable(state);
                break;
            }
            case 3: {
                enable1Ship.setDisable(state);
                enable2Ship.setDisable(state);
                enable4Ship.setDisable(state);
                break;

            }
            case 4: {
                enable1Ship.setDisable(state);
                enable2Ship.setDisable(state);
                enable3Ship.setDisable(state);
                break;
            }

        }
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

    public void hideDeleteMenu() {
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


    public void decreaseShipsToGo() {
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

    public void updateEnableLabels() {
        oneShipToGo = 4;
        twoShipToGo = 3;
        threeShipToGo = 2;
        fourShipToGo = 1;
        oneShipToGoLab.setText("4");
        twoShipToGoLab.setText("3");
        threeShipToGoLab.setText("2");
        fourShipToGoLab.setText("1");
    }
    public void updateTriggers() {
        captureTriggers[0] = captureTriggers[1] = captureTriggers[2] = captureTriggers[3] = captureTriggers[4] = false;
    }


    public void deleteAllDecks(int di, int dj) {
        if (playerField.getCell(di, dj).getCellColor().equals(Color.RED)) {
            playerField.getCell(di, dj).setCellColor(Color.WHITE);
            playerField.getCell(di, dj).draw(playerField.getGraphicsContext2D(), true);
            playerField.setBusyAroundCell(di, dj, DECREASE_BUSY);
            if (di - 1 >= 0 && playerField.getCell(di - 1, dj).getCellColor().equals(Color.RED)) {
                deleteAllDecks(di - 1, dj);
            }
            if (di + 1 < FIELD_SIZE && playerField.getCell(di + 1, dj).getCellColor().equals(Color.RED)) { ///don't write else!!!!!
                deleteAllDecks(di + 1, dj);
            }
            if (dj + 1 < FIELD_SIZE && playerField.getCell(di, dj + 1).getCellColor().equals(Color.RED)) {
                deleteAllDecks(di, dj + 1);
            }
            if (dj - 1 >= 0 && playerField.getCell(di, dj - 1).getCellColor().equals(Color.RED)) {
                deleteAllDecks(di, dj - 1);
            }
        }
        else if (playerField.getCell(di, dj).getCellColor().equals(Color.ORANGE)) {
            playerField.getCell(di, dj).setCellColor(Color.WHITE);
            playerField.getCell(di, dj).draw(playerField.getGraphicsContext2D(), true);
        }
    }

    public void toggleRightField(int mode) {
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



    public void deleteShip(int di, int dj) {
        if (playerField.getCell(di, dj).getCellColor().equals(Color.ORANGE)) {
            deleteAllDecks(di, dj);
            logic.getPlayerShips().remove(logic.getPlayerShips().size() - 1);
            clickCount--;
            return;
        }
        int shipLength = 0;
        for (Ship ship : logic.getPlayerShips()) {
            if (ship.hasDeckWithThisCoordinates(di, dj)) {
                shipLength = ship.getLength();
                logic.getPlayerShips().remove(ship);
                break;
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
        deleteAllDecks(di, dj);
    }

    public void decreaseLabelHP(int mode) {
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



    public static void main(String[] args) {
        launch(args);
    }
}
