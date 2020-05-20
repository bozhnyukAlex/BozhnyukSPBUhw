package org.app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import org.game.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BattleController2 extends View {


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
    private ContextMenu deleteMenu;
    private MenuItem itemDelete;
    public static final String DELETE_MENU_ID = "deleteMenu";

    private boolean isEnd = true; //пойдет в Logic, либо потом заменим

    @FXML
    public void initialize() {
        context = new AnnotationConfigApplicationContext(Config.class);
        initField(GameField.PLAYER_MODE);
        anchorPane.getChildren().add(playerField);
        initContextMenu();
        setStatusLabel(StringConst.CHOOSE_GAME_MODE);
        onePlayerButton.setOnAction(actionEvent -> gameStart(GameMode.ONE_PLAYER));
        twoPlayersButton.setOnAction(actionEvent -> gameStart(GameMode.TWO_PLAYERS));
        exitButton.setOnAction(actionEvent -> System.exit(0));
        settingsButton.setOnAction(actionEvent -> {

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
                            if (logic.getState().equals(GameState.PLAYING) && logic.getFightState().equals(FightState.PLAYER_MOVE)) {
                                int plsi = (int) mouseEvent.getY() / Cell.SIZE;
                                int plsj = (int) mouseEvent.getX() / Cell.SIZE;
                                if (enemyField.getCell(plsi, plsj).isShot() &&
                                        (enemyField.getCell(plsi, plsj).isDeck() || enemyField.getCell(plsi, plsj).getCellColor().equals(Color.TURQUOISE))) {
                                    return;
                                }
                                makeFieldShot(plsi, plsj, enemyField);
                                while (logic.isEnemyMove() && logic.isPlaying()) {
                                    Cell aiShot = logic.makeAiAttack();
                                    makeFieldShot(aiShot.getI(), aiShot.getJ(), playerField);
                                }
                            }
                        }
                    });

                    break;
                }

                case TWO_PLAYERS: {


                    break;
                }
            }
        });




    }



    @FXML
    public void onPlayerFieldClick() {

    }

    @FXML
    public void onEnemyFieldClick() {

    }

    private void gameStart(GameMode mode) {
        isEnd = false;
        if (logic != null) {
            switch (logic.getGameMode()) {
                case ONE_PLAYER: {
                    if (logic.getState().equals(GameState.PLAYING)) {
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
                        case PLAYING: {
                            toggleRightField(TO_BUTTON_PANE);
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
                logic.initAiWithContainer(playerField, IntelligenceLevel.MEDIUM); // это временно
                settingsButton.setDisable(false);
                setStatusLabel(StringConst.PREPARE);
                break;
            }
            case TWO_PLAYERS: {
                logic = context.getBean("logicTwoPlayers", Logic.class);
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
            //logic.setShips(logic.autoShipGenerate(playerField), Logic.PLAYER_SHIPS);
            logic.autoGenerate(playerField);
           // field.drawShips(logic.getShips(Logic.PLAYER_SHIPS), Color.RED);
            drawShips(playerField);
        }
        else if (field.equals(enemyField)) {
         //   logic.setShips(logic.autoShipGenerate(enemyField), Logic.ENEMY_SHIPS);
            logic.autoGenerate(enemyField);
            //field.drawShips(logic.getShips(Logic.ENEMY_SHIPS), Color.RED);
            drawShips(enemyField);
        }
      //  setZeroToEnableLabelsAndCounts();
        logic.updateParams();
        updateEnableLabels();
        setDisableToButtonsOnSecondField(true);
        readyButton.setDisable(false);
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
        if (shot.getCellColor().equals(Color.TURQUOISE)) { //промах
            if (field.ofPlayer() && logic.isTwoPlayersMode()) {
                setStatusLabel(StringConst.MOVE_FIRST);
            }
        }
        else if (shot.getCellColor().equals(Color.RED)) {
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
        else if (shot.getCellColor().equals(Color.DARKOLIVEGREEN)) {
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





}
