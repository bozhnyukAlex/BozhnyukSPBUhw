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

    private Label playerShipsLeft;

    private Label enemyShipsLeft;

    private Pane enemyPane; //вся панель с кнопками выборов

    private Label oneShipToGoLab;

    private Label twoShipToGoLab;

    private Label threeShipToGoLab;

    private Label fourShipToGoLab;

    private Label lSLeft;

    private Label rSLeft;

    private ComboBox langBox;

    private Label leftABC;

    private Label rightABC;


}
