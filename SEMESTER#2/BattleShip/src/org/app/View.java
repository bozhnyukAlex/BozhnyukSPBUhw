package org.app;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.game.GameField;
import org.game.Logic;
import org.game.Ship;

import java.util.ArrayList;

public class View {

    private final String PLAYER_FIELD_ID = "playerField";
    private final String ENEMY_FIELD_ID = "enemyField";
    public static final int TO_BUTTON_PANE = 5;
    public static final int TO_ENEMY_FIELD = 6;
    public static final int DECREASE_PLAYER = 7;
    public static final int DECREASE_ENEMY = 8;
    @FXML
    private VBox pane;
    @FXML
    private AnchorPane settingsPane;
    @FXML
    public AnchorPane anchorPane; // отвечает за размещение полей и области с кнопками
    @FXML
    private Label statusLabel;

    public GameField playerField;

    public GameField enemyField; // здесь полня только для оображения самих иконок
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

    public ContextMenu deleteMenu;
    public MenuItem itemDelete;

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

    public void toggleLeftField(int mode) {
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

    public void initShipsLeftLabels() {
        playerShipsLeft.setText("10");
        enemyShipsLeft.setText("10");
    }

    public void setStatusLabel(String statusText) {
        statusLabel.setText(statusText);
    }



    public void updateEnableLabels() {
        oneShipToGoLab.setText("4");
        twoShipToGoLab.setText("3");
        threeShipToGoLab.setText("2");
        fourShipToGoLab.setText("1");
    }

    public void setZeroToEnableShipsLabels() {
        oneShipToGoLab.setText("0");
        twoShipToGoLab.setText("0");
        threeShipToGoLab.setText("0");
        fourShipToGoLab.setText("0");
    }

    public void drawShips(GameField field, ArrayList<Ship> ships) {
        field.drawShips(ships, Color.RED);
    }

    public void decreaseLabelHP(int mode) {
        switch (mode) {
            case DECREASE_PLAYER: {
                int playerHPD = Integer.parseInt(playerShipsLeft.getText()) - 1;
                playerShipsLeft.setText(Integer.toString(playerHPD));
                break;
            }
            case DECREASE_ENEMY: {
                int enemyHPD = Integer.parseInt(enemyShipsLeft.getText()) - 1;
                enemyShipsLeft.setText(Integer.toString(enemyHPD));
                break;
            }
        }
    }

    public void hideDeleteMenu() {
        if (deleteMenu.isShowing()) {
            deleteMenu.hide();
        }
    }

    public void increaseShipsToGo(int num) {
        int val;
        switch (num) {
            case 1: {
                val = Integer.parseInt(oneShipToGoLab.getText()) + 1;
                oneShipToGoLab.setText(Integer.toString(val));
                break;
            }
            case 2: {
                val = Integer.parseInt(twoShipToGoLab.getText()) + 1;
                twoShipToGoLab.setText(Integer.toString(val));
                break;
            }
            case 3: {
                val = Integer.parseInt(threeShipToGoLab.getText()) + 1;
                threeShipToGoLab.setText(Integer.toString(val));
                break;
            }
            case 4: {
                val = Integer.parseInt(fourShipToGoLab.getText()) + 1;
                fourShipToGoLab.setText(Integer.toString(val));
                break;
            }
        }
    }

    public void setLabelToGo(int ... values) {
        oneShipToGoLab.setText(Integer.toString(values[1]));
        twoShipToGoLab.setText(Integer.toString(values[2]));
        threeShipToGoLab.setText(Integer.toString(values[3]));
        fourShipToGoLab.setText(Integer.toString(values[4]));
    }

    public String status() {
        return statusLabel.getText();
    }

    public void localeLabels() {
        lSLeft.setText(StringConst.SHIPS_LEFT);
        rSLeft.setText(StringConst.SHIPS_LEFT);
        leftABC.setText(StringConst.LEFT_ABC);
        rightABC.setText(StringConst.RIGHT_ABC);
    }






}
