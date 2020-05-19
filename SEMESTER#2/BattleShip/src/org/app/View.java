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
    private VBox pane;
    @FXML
    private AnchorPane settingsPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label statusLabel;

    private GameField playerField;

    private GameField enemyField; // здесь полня только для оображения самих иконок
    @FXML
    private Label playerShipsLeft;
    @FXML
    private Label enemyShipsLeft;
    @FXML
    private Pane enemyPane; //вся панель с кнопками выборов
    @FXML
    private Label oneShipToGoLab;
    @FXML
    private Label twoShipToGoLab;
    @FXML
    private Label threeShipToGoLab;
    @FXML
    private Label fourShipToGoLab;
    @FXML
    private Label lSLeft;
    @FXML
    private Label rSLeft;
    @FXML
    private ComboBox langBox;
    @FXML
    private Label leftABC;
    @FXML
    private Label rightABC;



}
