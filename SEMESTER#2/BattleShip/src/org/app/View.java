package org.app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.game.GameField;
import org.game.Logic;

public class View extends VBox {
    private Logic logic;
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

    private Button enable4Ship;

    private Button enable3Ship;

    private Button enable2Ship;

    private Button enable1Ship;

    private Button readyButton;

    private Label playerShipsLeft;

    private Label enemyShipsLeft;

    private Pane enemyPane;

    private Label oneShipToGoLab;

    private Label twoShipToGoLab;

    private Label threeShipToGoLab;

    private Label fourShipToGoLab;

    private Label lSLeft;

    private Label rSLeft;

    private ComboBox langBox;

    private Label leftABC;

    private Label rightABC;

    public View() {

    }

    @FXML
    public void initialize() {

    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public void setSettingsPane(AnchorPane settingsPane) {
        this.settingsPane = settingsPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public void setAutoGenerateButton(Button autoGenerateButton) {
        this.autoGenerateButton = autoGenerateButton;
    }

    public void setOnePlayerButton(Button onePlayerButton) {
        this.onePlayerButton = onePlayerButton;
    }

    public void setTwoPlayersButton(Button twoPlayersButton) {
        this.twoPlayersButton = twoPlayersButton;
    }

    public void setSettingsButton(Button settingsButton) {
        this.settingsButton = settingsButton;
    }

    public void setExitButton(Button exitButton) {
        this.exitButton = exitButton;
    }

    public void setStatusLabel(Label statusLabel) {
        this.statusLabel = statusLabel;
    }

    public void setPlayerField(GameField playerField) {
        this.playerField = playerField;
    }

    public void setEnemyField(GameField enemyField) {
        this.enemyField = enemyField;
    }

    public void setEnable4Ship(Button enable4Ship) {
        this.enable4Ship = enable4Ship;
    }

    public void setEnable3Ship(Button enable3Ship) {
        this.enable3Ship = enable3Ship;
    }

    public void setEnable2Ship(Button enable2Ship) {
        this.enable2Ship = enable2Ship;
    }

    public void setEnable1Ship(Button enable1Ship) {
        this.enable1Ship = enable1Ship;
    }

    public void setReadyButton(Button readyButton) {
        this.readyButton = readyButton;
    }

    public void setPlayerShipsLeft(Label playerShipsLeft) {
        this.playerShipsLeft = playerShipsLeft;
    }

    public void setEnemyShipsLeft(Label enemyShipsLeft) {
        this.enemyShipsLeft = enemyShipsLeft;
    }

    public void setEnemyPane(Pane enemyPane) {
        this.enemyPane = enemyPane;
    }

    public void setOneShipToGoLab(Label oneShipToGoLab) {
        this.oneShipToGoLab = oneShipToGoLab;
    }

    public void setTwoShipToGoLab(Label twoShipToGoLab) {
        this.twoShipToGoLab = twoShipToGoLab;
    }

    public void setThreeShipToGoLab(Label threeShipToGoLab) {
        this.threeShipToGoLab = threeShipToGoLab;
    }

    public void setFourShipToGoLab(Label fourShipToGoLab) {
        this.fourShipToGoLab = fourShipToGoLab;
    }

    public void setlSLeft(Label lSLeft) {
        this.lSLeft = lSLeft;
    }

    public void setrSLeft(Label rSLeft) {
        this.rSLeft = rSLeft;
    }

    public void setLangBox(ComboBox langBox) {
        this.langBox = langBox;
    }

    public void setLeftABC(Label leftABC) {
        this.leftABC = leftABC;
    }
}
