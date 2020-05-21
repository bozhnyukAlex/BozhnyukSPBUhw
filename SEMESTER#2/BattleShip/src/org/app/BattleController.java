package org.app;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.game.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ServiceLoader;

public class BattleController extends View {


    private AnnotationConfigApplicationContext context;
    private Logic logic;
    @FXML
    private Button onePlayerButton;
    @FXML
    private Button twoPlayersButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button enable4Ship;
    @FXML
    private Button enable3Ship;
    @FXML
    private Button enable2Ship;
    @FXML
    private Button enable1Ship;
    @FXML
    private Button autoGenerateButton;
    @FXML
    private Button readyButton;
    @FXML
    private ComboBox langBox;
    @FXML
    private AnchorPane settingsPane;

    public static final String DELETE_MENU_ID = "deleteMenu";
    public static final String SETTINGS_PATH = "/view/settings.fxml";
    public static final String ICON_PATH = "/images/icon.png";
    private static ServiceLoader<LocaleService> serviceLoader;
    private static ArrayList<String> plugNames;


    @FXML
    public void initialize() {
        initAllPlugins();
        context = new AnnotationConfigApplicationContext(Config.class);
        initField(GameField.PLAYER_MODE);
        anchorPane.getChildren().add(playerField);
        initContextMenu();
        setStatusLabel(StringConst.CHOOSE_GAME_MODE);
        langBox.setItems(FXCollections.observableArrayList(plugNames));
        langBox.setPromptText(StringConst.SET_LANG);
        onePlayerButton.setOnAction(actionEvent -> gameStart(GameMode.ONE_PLAYER));
        twoPlayersButton.setOnAction(actionEvent -> gameStart(GameMode.TWO_PLAYERS));
        exitButton.setOnAction(actionEvent -> System.exit(0));
        langBox.setOnAction(actionEvent -> setLocale(langBox.getValue().toString()));
        settingsButton.setOnAction(actionEvent -> {
            boolean okClicked  = showDialogEditAi();
            if (okClicked) {
                setStatusLabel(StringConst.LEVEL_EDITED);
            }
        });
        enable1Ship.setOnAction(actionEvent -> {
            setStatusLabel(StringConst.SET_SHIP_1);
            logic.processShipEnableClick(1);
        });
        enable2Ship.setOnAction(actionEvent -> {
            setStatusLabel(StringConst.SET_SHIP_2);
            logic.processShipEnableClick(2);
        });
        enable3Ship.setOnAction(actionEvent -> {
            setStatusLabel(StringConst.SET_SHIP_3);
            logic.processShipEnableClick(3);
        });
        enable4Ship.setOnAction(actionEvent -> {
            setStatusLabel(StringConst.SET_SHIP_4);
            logic.processShipEnableClick(4);
        });

        autoGenerateButton.setOnAction(actionEvent -> {
            switch (logic.getGameMode()) {
                case ONE_PLAYER: {
                    generateShipsByClicking(playerField);
                    break;
                }
                case TWO_PLAYERS: {
                    switch (logic.getState()) {
                        case PREPARATION1: {
                            generateShipsByClicking(playerField);
                            break;
                        }
                        case PREPARATION2: {
                            generateShipsByClicking(enemyField);
                            break;
                        }
                    }
                    break;
                }
            }
        });

        readyButton.setOnAction(actionEvent -> {
            switch (logic.getGameMode()) {
                case ONE_PLAYER: {
                    initField(GameField.ENEMY_MODE);
                    toggleRightField(TO_ENEMY_FIELD);
                    logic.setGameState(GameState.PLAYING);
                    logic.autoGenerate(enemyField);
                    logic.setFightState(FightState.PLAYER_MOVE);
                    settingsButton.setDisable(true);
                    setStatusLabel(StringConst.FIGHT);

                    enemyField.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                            if (logic.playing() && logic.isPlayerMove()) {
                                int plsI = (int) mouseEvent.getY() / Cell.SIZE;
                                int plsJ = (int) mouseEvent.getX() / Cell.SIZE;
                                if (!canShoot(plsI, plsJ, enemyField)) {
                                    return;
                                }
                                makeFieldShot(plsI, plsJ, enemyField);
                                while (logic.isEnemyMove() && logic.playing()) {
                                    Cell aiShot = logic.makeAiAttack();
                                    makeFieldShot(aiShot.getI(), aiShot.getJ(), playerField);
                                }
                            }
                        }
                    });
                    break;
                }

                case TWO_PLAYERS: {
                    if (logic.firstPreparing()) { //если готовился, то должен начать готовиться второй
                        logic.setGameState(GameState.PREPARATION2);
                        initField(GameField.ENEMY_MODE);
                        toggleRightField(TO_ENEMY_FIELD);
                        toggleLeftField(TO_BUTTON_PANE);
                        setStatusLabel(StringConst.PREPARE_SECOND);
                        logic.updateParams();
                        updateEnableLabels();
                        setDisableToEnableButtons(false);
                        settingsButton.setDisable(true);
                        enemyField.setOnMouseClicked(mouseEvent -> {
                            if (mouseEvent.getButton() == MouseButton.PRIMARY && logic.secondPreparing()) {
                                int cli = (int) mouseEvent.getY(), clj = (int) mouseEvent.getX();
                                cli /= Cell.SIZE;
                                clj /= Cell.SIZE;
                                setShipToClickedField(cli, clj, enemyField);
                                hideDeleteMenu();
                            }
                        });

                        enemyField.setOnContextMenuRequested(contextMenuEvent -> {
                            int rci = (int) contextMenuEvent.getY() / Cell.SIZE, rcj = (int) contextMenuEvent.getX() / Cell.SIZE;
                            menuAction(rci, rcj, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY(), enemyField);
                        });


                    }
                    else if (logic.secondPreparing()) {
                        logic.setGameState(GameState.PLAYING);
                        logic.setFightState(FightState.PLAYER_MOVE);
                        setStatusLabel(StringConst.MOVE_FIRST);
                        toggleLeftField(TO_ENEMY_FIELD);
                        playerField.redraw();
                        enemyField.redraw();
                        enemyField.setOnMouseClicked(mouseEvent -> {
                            if (mouseEvent.getButton() == MouseButton.PRIMARY && logic.playerMove() && logic.isTwoPlayersMode() && logic.playing()) {
                                int plsI = (int) mouseEvent.getY() / Cell.SIZE;
                                int plsj = (int) mouseEvent.getX() / Cell.SIZE;
                                if (!canShoot(plsI, plsj, enemyField)) {
                                    return;
                                }
                                makeFieldShot(plsI, plsj, enemyField);
                            }
                        });
                    }
                    break;
                }
            }
        });


        playerField.setOnMouseClicked(mouseEvent ->  {
            if (logic == null || (logic.firstPreparing() && logic.noEnableButtonsClicked()) || logic.secondPreparing()
                    || (logic.playing() && logic.isOnePlayerMode()) || (logic.playing() && logic.isTwoPlayersMode() && logic.playerMove())) {
                return;
            }
            if (mouseEvent.getButton() == MouseButton.PRIMARY && logic.firstPreparing()) {
                int cli = (int) mouseEvent.getY(), clj = (int) mouseEvent.getX();
                cli /= Cell.SIZE;
                clj /= Cell.SIZE;
                setShipToClickedField(cli, clj, playerField);
                hideDeleteMenu();
            }
            else if (mouseEvent.getButton() == MouseButton.PRIMARY && logic.playing() && logic.isTwoPlayersMode() && logic.enemyMove()) {
                int plsI = (int) mouseEvent.getY() / Cell.SIZE;
                int plsJ = (int) mouseEvent.getX() / Cell.SIZE;
                if (!canShoot(plsI, plsJ, playerField)) {
                    return;
                }
                makeFieldShot(plsI, plsJ, playerField);
            }
        });

        playerField.setOnContextMenuRequested(contextMenuEvent -> {
            int rci = (int) contextMenuEvent.getY() / Cell.SIZE, rcj = (int) contextMenuEvent.getX() / Cell.SIZE;
            menuAction(rci, rcj, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY(), playerField);
        });
    }



    private void gameStart(GameMode mode) {
        //isEnd = false;
        IntelligenceLevel levelToSend = (logic == null || !logic.aiInitialized()) ? IntelligenceLevel.MEDIUM : logic.getDifficulty();
        if (logic != null) {
            switch (logic.getGameMode()) {
                case ONE_PLAYER: {
                    if (logic.playing() || logic.end()) {
                        toggleRightField(TO_BUTTON_PANE);
                    }
                    break;
                }
                case TWO_PLAYERS: {
                    switch (logic.getState()) {
                        case PREPARATION2: {
                            toggleLeftField(TO_ENEMY_FIELD);
                            toggleRightField(TO_BUTTON_PANE);
                            break;
                        }
                        case END:
                        case PLAYING: {
                            toggleRightField(TO_BUTTON_PANE);
                            break;
                        }
                    }
                    break;
                }
            }
        }
        initShipsLeftLabels();
        setDisableToButtonsOnSecondField(false);
        readyButton.setDisable(true);
        if (logic != null) {
            if (!logic.getShips(Logic.PLAYER_SHIPS).isEmpty()) {
                playerField.update();
                logic.updateParams();
                updateEnableLabels();
            }
        }
        switch (mode) {
            case ONE_PLAYER: {
                logic = context.getBean("logicOnePlayer", Logic.class);
                logic.setContext(context);
                // logic.initAI(playerField, levelToSend);
                logic.initAiWithContainer(playerField, levelToSend);
                settingsButton.setDisable(false);
                setStatusLabel(StringConst.PREPARE);
                break;
            }
            case TWO_PLAYERS: {
                logic = context.getBean("logicTwoPlayers", Logic.class);
                logic.setContext(context);
                settingsButton.setDisable(true);
                setStatusLabel(StringConst.PREPARE_FIRST);
                break;
            }
        }
        logic.setGameState(GameState.PREPARATION1);

    }

    private void setDisableToButtonsOnSecondField(boolean state) {
        enable1Ship.setDisable(state);
        enable2Ship.setDisable(state);
        enable3Ship.setDisable(state);
        enable4Ship.setDisable(state);
        readyButton.setDisable(state);
        autoGenerateButton.setDisable(state);
    }

    private void generateShipsByClicking(GameField field) {
        field.update();
        if (field.equals(playerField)) {
            logic.autoGenerate(playerField);
            drawShips(playerField, logic.getShips(Logic.PLAYER_SHIPS));
        }
        else if (field.equals(enemyField)) {
            logic.autoGenerate(enemyField);
            drawShips(enemyField, logic.getShips(Logic.ENEMY_SHIPS));
        }
       // logic.updateParams();
        setZeroToEnableShipsLabels();
        setDisableToButtonsOnSecondField(true);
        readyButton.setDisable(false);
        autoGenerateButton.setDisable(false);
        setStatusLabel(StringConst.YOU_ARE_READY);
    }

    private void initField(int mode) {
        switch (mode) {
            case GameField.PLAYER_MODE: {
                playerField = context.getBean("playerField", GameField.class);
                playerField.setId("playerField");
                break;
            }
            case GameField.ENEMY_MODE: {
                enemyField = context.getBean("enemyField", GameField.class);
                enemyField.setId("enemyField");
                break;
            }
        }
    }

    private void initContextMenu() {
        deleteMenu = new ContextMenu();
        deleteMenu.setId(DELETE_MENU_ID);
        itemDelete = new MenuItem(StringConst.DELETE_SHIP);
        deleteMenu.getItems().add(itemDelete);
    }

    private void makeFieldShot(int shotI, int shotJ, GameField field) {
        logic.processShot(shotI, shotJ, field);
        Cell shot = field.getCell(shotI, shotJ);
        if (shot.isShotWater()) { //промах
            if (logic.isTwoPlayersMode()) {
                if (field.ofPlayer()) {
                    setStatusLabel(StringConst.MOVE_FIRST);
                }
                else if (field.ofEnemy()) {
                    setStatusLabel(StringConst.MOVE_SECOND);
                }
            }
        }
        else if (shot.isShipKilledEnemy()) {
            decreaseLabelHP(DECREASE_ENEMY);
            if (logic.enemyLose()) {
                if (logic.isOnePlayerMode()) {
                    setStatusLabel(StringConst.YOU_WON);
                }
                else if (logic.isTwoPlayersMode()) {
                    setStatusLabel(StringConst.FIRST_WON);
                }
            }
        }
        else if (shot.isShipKilledPlayerTwo()) {
            decreaseLabelHP(DECREASE_PLAYER);
            if (logic.playerLose()) {
                setStatusLabel(StringConst.SECOND_WON);
            }
        }
        else if (shot.isShipKilledPlayer()) {
            decreaseLabelHP(DECREASE_PLAYER);
            if (logic.playerLose()) {
                if (logic.isOnePlayerMode()) {
                    setStatusLabel(StringConst.YOU_LOSE);
                }
                else if (logic.isTwoPlayersMode()) {
                    setStatusLabel(StringConst.SECOND_WON);
                }
            }
        }
    }

    public void setDisableToEnableButtons(boolean state) {
        enable1Ship.setDisable(state);
        enable2Ship.setDisable(state);
        enable3Ship.setDisable(state);
        enable4Ship.setDisable(state);
    }

    private void deleteShip(int di, int dj, GameField field) {
        int shipLength = logic.deleteProcessing(di, dj, field);
        if (shipLength == -1) {
            return;
        }
        //setDisableToEnableButtons(logic.getEnableCounts(shipLength) <= 0);
        setDisableToEnableButton(shipLength, logic.getEnableCounts(shipLength) <= 0);
        increaseShipsToGo(shipLength);
        if (status().equals(StringConst.YOU_ARE_READY)) {
            setStatusLabel(StringConst.CHOOSE_SHIP);
        }
        readyButton.setDisable(true);
    }

    private void menuAction(int rci, int rcj, double scrX, double scrY, GameField field) {
        if (field.ofEnemy()) {
            if (logic.secondPreparing()) {
                if (enemyField.getCell(rci, rcj).isNotShotDeck() || enemyField.getCell(rci, rcj).isFirstClickedDeck()) {
                    deleteMenu.show(enemyField, scrX, scrY);
                }
            }
            itemDelete.setOnAction(actionEvent1 -> deleteShip(rci, rcj, enemyField));
        }
        else if (field.ofPlayer()) {
            if (logic.firstPreparing()) {
                if (playerField.getCell(rci, rcj).isNotShotDeck()|| playerField.getCell(rci, rcj).isFirstClickedDeck()) {
                    deleteMenu.show(playerField, scrX, scrY);
                }
            }
            itemDelete.setOnAction(actionEvent -> deleteShip(rci, rcj, playerField));
        }
    }

    private void setShipToClickedField(int cli, int clj, GameField clickedField) {
        String message = logic.processSettingShip(cli, clj, clickedField);
        setStatusLabel(message);
        setLabelToGo(logic.getEnableCounts());
        disableButtonsIfNeed();
        if (logic.allShipsAreReady()) {
            readyButton.setDisable(false);
        }

    }

    private void disableButtonsIfNeed() {
        enable1Ship.setDisable(logic.getEnableCounts(1) <= 0);
        enable2Ship.setDisable(logic.getEnableCounts(2) <= 0);
        enable3Ship.setDisable(logic.getEnableCounts(3) <= 0);
        enable4Ship.setDisable(logic.getEnableCounts(4) <= 0);
    }

    private void setDisableToEnableButton(int num, boolean cond) {
        switch (num) {
            case 1: {
                enable1Ship.setDisable(cond);
                break;
            }
            case 2: {
                enable2Ship.setDisable(cond);
                break;
            }
            case 3: {
                enable3Ship.setDisable(cond);
                break;
            }
            case 4: {
                enable4Ship.setDisable(cond);
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
        dialogStage.setTitle(StringConst.EDIT_AI);
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

    private void setLocale(String localeStr) {
        for (LocaleService service : serviceLoader) {
            if (service.getName().equals(localeStr)) {
                service.locale();
                break;
            }
        }
        localeButtons();
        localeLabels();
        setStatusLabel(StringConst.LANGUAGE_EDITED);
        if (logic != null) {
            if (logic.getState().equals(GameState.PLAYING)) {
                setStatusLabel(StringConst.FIGHT);
            }
        }
    }

    private boolean canShoot(int i, int j, GameField field) {
        Cell shot = field.getCell(i, j);
        return shot.isEmpty() || shot.isNotShotDeck();
    }

    private void localeButtons() {
        onePlayerButton.setText(StringConst.ONE_PLAYER_GAME);
        twoPlayersButton.setText(StringConst.TWO_PLAYER_GAME);
        settingsButton.setText(StringConst.DIFF_SETTINGS);
        exitButton.setText(StringConst.EXIT);
        enable1Ship.setText(StringConst.SHIP1);
        enable2Ship.setText(StringConst.SHIP2);
        enable3Ship.setText(StringConst.SHIP3);
        enable4Ship.setText(StringConst.SHIP4);
        readyButton.setText(StringConst.READY);
        autoGenerateButton.setText(StringConst.AUTOMATIC);
    }

    private void initAllPlugins() {
        serviceLoader = ServiceLoader.load(LocaleService.class);
        plugNames = new ArrayList<String>();
        for (LocaleService localeService : serviceLoader) {
            plugNames.add(localeService.getName());
        }
    }







}
