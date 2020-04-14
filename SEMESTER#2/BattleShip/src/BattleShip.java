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

  //  private Cell[][] cells;
    private Logic logic;
    private int oneShipToGo, twoShipToGo, threeShipToGo, fourShipToGo;

    private final String NO_SHIPS_LEFT = "Кораблей этого типа не осталось!";
    private final String CELL_IS_BUSY = "Сюда ставить нельзя";
    private final String SET_DIR = "Нажмите на поле еще раз для установки направления";
    private final String CHOOSE_SHIP = "Выберите корабль";
    private final String DELETE_SHIP = "Удалить корабль";
    private final String YOU_ARE_READY = "Вы готовы к бою!";

    private final int INCREASE_BUSY = 1;
    private final int DECREASE_BUSY = -1;
    private final int PLAYER_FIELD = 3;
    private final int ENEMY_FIELD = 4;
    private final int TO_BUTTON_PANE = 5;
    private final int TO_ENEMY_FIELD = 6;








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
    public void initialize() throws Exception {
       // super.init();
        captureTriggers = new boolean[] {false, false, false, false, false};
        setDisableToButtonsOnSecondField(true);
        //cells = new Cell[FIELD_SIZE][FIELD_SIZE];
        //drawField(playerField);
        playerField = new GameField(PLAYER_FIELD);
        anchorPane.getChildren().add(playerField);
        deleteMenu = new ContextMenu();
        itemDelete = new MenuItem(DELETE_SHIP);
        deleteMenu.getItems().add(itemDelete);


        oneShipToGo = Integer.parseInt(oneShipToGoLab.getText());
        twoShipToGo = Integer.parseInt(twoShipToGoLab.getText());
        threeShipToGo = Integer.parseInt(threeShipToGoLab.getText());
        fourShipToGo = Integer.parseInt(fourShipToGoLab.getText());
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (logic != null && logic.getState().equals(GameState.PLAYING)) {
                    toggleRightField(TO_BUTTON_PANE);
                }
                statusLabel.setText("Подготовка");
                setDisableToButtonsOnSecondField(false); // it looks weird, need to fix it later
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
            }
        });
        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //anchorPane.getChildren().remove(enemyPane); - Вот так можно убирать элементы
            }
        });
        exitButton.setOnAction(actionEvent -> System.exit(0));

        enable1Ship.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                statusLabel.setText("Поставьте 1 палубный корабль");
                clickCount = 0;
                setTrigger(1, true);
            }
        });

        enable2Ship.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                statusLabel.setText("Поставьте 2-х палубный корабль");
                clickCount = 0;
                setTrigger(2, true);
            }
        });
        enable3Ship.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                statusLabel.setText("Поставьте 3-х палубный корабль");
                setTrigger(3, true);
            }
        });
        enable4Ship.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                statusLabel.setText("Поставьте 4-х палубный корабль");
                setTrigger(4,true);
            }
        });
        playerField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY && logic.getState().equals(GameState.PREPARATION)) {
                    int cli = (int) mouseEvent.getY(), clj = (int) mouseEvent.getX();
                    cli /= Cell.SIZE;
                    clj /= Cell.SIZE;
                    if (getTrigger() == 1) {
                        if (playerField.getCell(cli, clj).isBusy()) {
                            statusLabel.setText(CELL_IS_BUSY);
                            return;
                        }
                        playerField.getCell(cli, clj).drawShipDeck(playerField.getGraphicsContext2D(), false);
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
                            playerField.getCell(cli, clj).drawShipDeck(playerField.getGraphicsContext2D(), true);
                            playerField.getCell(cli, clj).setCellColor(Color.ORANGE);
                            Ship nShip = new Ship(getTrigger());
                            nShip.build(playerField.getCell(cli, clj));
                            logic.addPlayerShip(nShip);
                        }
                        if (clickCount == 2) {
                            Ship prevShip = logic.getPlayerShips().get(logic.getPlayerShips().size() - 1);
                            int pi = prevShip.getDecks().get(0).getY() / Cell.SIZE,
                                    pj = prevShip.getDecks().get(0).getX() / Cell.SIZE;
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
                                    playerField.getCell(pi - i, pj).drawShipDeck(playerField.getGraphicsContext2D(), false);
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
                                    playerField.getCell(pi + i, pj).drawShipDeck(playerField.getGraphicsContext2D(), false);
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
                                    playerField.getCell(pi, pj - i).drawShipDeck(playerField.getGraphicsContext2D(), false);
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
                                    playerField.getCell(pi, pj + i).drawShipDeck(playerField.getGraphicsContext2D(), false);
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
            }
        });


        playerField.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {
                int rci = (int) contextMenuEvent.getY(),
                    rcj = (int) contextMenuEvent.getX();
                rci /= Cell.SIZE;
                rcj /= Cell.SIZE;
                if (playerField.getCell(rci, rcj).getCellColor().equals(Color.RED) || playerField.getCell(rci, rcj).getCellColor().equals(Color.ORANGE)) {
                    deleteMenu.show(playerField, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
                }

                int finalRci = rci;
                int finalRcj = rcj;
                itemDelete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        deleteShip(finalRci, finalRcj);
                    }
                });
            }
        });

        readyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                enemyField = new GameField(ENEMY_FIELD);
                toggleRightField(TO_ENEMY_FIELD);
            }
        });

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

 /*   public void drawField(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE ; j++) {
                cells[i][j] = new Cell(j * Cell.SIZE, i * Cell.SIZE); /// ЭТО ПЛОХО, В ОТДЕЛЬНЫЙ МЕТОД ЛУЧШЕ\
                cells[i][j].setCellColor(Color.WHITE);
                cells[i][j].draw(gc, false);
            }
        }
    }*/

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

    /*public void setBusyAroundCell(int i, int j, int mode) {
        for (int w = -1; w <= 1; w++) {
            for (int v = -1; v <= 1; v++) {
                if (i + w >= 0 && i + w < FIELD_SIZE && j + v >= 0 && j + v < FIELD_SIZE) {
                    cells[i + w][j + v].changeBusyCount(mode);
                }
            }
        }
    }*/

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

    /*public void updateField(Canvas field) {
        GraphicsContext gc = field.getGraphicsContext2D();
        gc.clearRect(0,0, field.getWidth(), field.getHeight());
        playerField.draw();
    }*/


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
            }
            case TO_ENEMY_FIELD: {
                anchorPane.getChildren().remove(enemyPane);
                anchorPane.getChildren().add(enemyField);
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




    public static void main(String[] args) {
        launch(args);
    }
}
