package org.app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.game.GameField;
import org.game.GameMode;
import org.game.Logic;
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

    private boolean isEnd = true;

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

    }

    @FXML
    public void enable3ShipClick() {

    }

    @FXML
    public void enable2ShipClick() {

    }

    @FXML
    public void enable1ShipClick() {

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
                    break;
                }
                case TWO_PLAYERS: {
                    break;
                }
            }
        }

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
