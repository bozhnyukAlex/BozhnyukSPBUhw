package org.app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
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

    private boolean isEnd = true; //пойдет в Logic, либо потом заменим

    @FXML
    public void initialize() {
        context = new AnnotationConfigApplicationContext(Config.class);

    }


    @FXML
    public void onePlayerButtonClick() {
        gameStart(GameMode.ONE_PLAYER);
    }

    @FXML
    public void twoPlayerButtonClick() {
        gameStart(GameMode.TWO_PLAYERS);
    }

    @FXML
    public void exitButtonClick() {
        System.exit(0);
    }

    @FXML
    public void settingsButtonClick() {

    }

    @FXML
    public void enable4ShipClick() {
        setStatusLabel(StringConst.SET_SHIP_4);
        logic.processShipEnableClick(4);
    }

    @FXML
    public void enable3ShipClick() {
        setStatusLabel(StringConst.SET_SHIP_3);
        logic.processShipEnableClick(3);
    }

    @FXML
    public void enable2ShipClick() {
        setStatusLabel(StringConst.SET_SHIP_2);
        logic.processShipEnableClick(2);
    }

    @FXML
    public void enable1ShipClick() {
        setStatusLabel(StringConst.SET_SHIP_1);
        logic.processShipEnableClick(1);
    }

    @FXML
    public void readyButtonClick() {

    }

    @FXML
    public void autoGenerateButtonClick() {

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





}
