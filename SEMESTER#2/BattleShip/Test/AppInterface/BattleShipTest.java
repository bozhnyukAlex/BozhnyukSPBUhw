package AppInterface;

import GamePack.Cell;
import GamePack.GameField;
import com.sun.tools.javac.Main;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Random;

import static org.junit.Assert.*;

public class BattleShipTest extends ApplicationTest {
    private VBox pane;
    private String ONE_PLAYER_BUTTON_ID = "#onePlayerButton";
    private String TWO_PLAYERS_BUTTON_ID = "#twoPlayersButton";
    private String AUTO_SHIPS_ID = "#autoGenerateButton";
    private String ENABLE_1 = "#enable1Ship";
    private String ENABLE_2 = "#enable2Ship";
    private String ENABLE_3 = "#enable3Ship";
    private String ENABLE_4 = "#enable4Ship";
    private String READY_BUTTON = "#readyButton";
    private String STATUS_LBL = "#statusLabel";
    private String SETTINGS_LBL = "#settingsButton";
    private String PLAYER_FIELD = "#playerField";
    private String ONE_SHIP_GO = "#oneShipToGoLab";
    private String TWO_SHIP_GO = "#twoShipToGoLab";
    private String THREE_SHIP_GO = "#threeShipToGoLab";
    private String FOUR_SHIP_GO = "#fourShipToGoLab";
    private int zeroX = 660, zeroY = 340;


    Button onePlayerBtn, twoPlayerBtn, settingsBtn;
    Button enable1, enable2, enable3, enable4;
    Button autoGenerate, readyBtn;
    Label statusLbl, en1, en2, en3, en4;
    Label playerLeft, enemyLeft;
    GameField playerField;


    @Override
    public void start(Stage stage) throws Exception {
        pane = FXMLLoader.load(BattleShip.class.getResource("/view/battlemenu.fxml"));
        stage.setScene(new Scene(pane));
        stage.show();
    }

    public <T extends Node> T find(final String query) {
        return lookup(query).query();
    }

    @Before
    public void setUp() throws Exception {
        Random rnd = new Random();
        onePlayerBtn = find(ONE_PLAYER_BUTTON_ID);
        twoPlayerBtn = find(TWO_PLAYERS_BUTTON_ID);
        enable1 = find(ENABLE_1);
        enable2 = find(ENABLE_2);
        enable3 = find(ENABLE_3);
        enable4 = find(ENABLE_4);
        autoGenerate = find(AUTO_SHIPS_ID);
        readyBtn = find(READY_BUTTON);
        statusLbl = find(STATUS_LBL);
        settingsBtn = find(SETTINGS_LBL);
        playerField = find(PLAYER_FIELD);
        en1 = find(ONE_SHIP_GO);
        en2 = find(TWO_SHIP_GO);
        en3 = find(THREE_SHIP_GO);
        en4 = find(FOUR_SHIP_GO);
    }

    @Test
    public void onePlayerButtonPressTest() {
        assertTrue(enable1.isDisable());
        assertTrue(enable2.isDisable());
        assertTrue(enable3.isDisable());
        assertTrue(enable4.isDisable());
        assertTrue(autoGenerate.isDisable());
        assertTrue(settingsBtn.isDisable());
        clickOn(onePlayerBtn);
        assertFalse(enable1.isDisable());
        assertFalse(enable2.isDisable());
        assertFalse(enable3.isDisable());
        assertFalse(enable4.isDisable());
        assertFalse(settingsBtn.isDisable());
        assertEquals(statusLbl.getText(), "Подготовка");
    }

    @Test
    public void shipSettingTest() {
        clickOn(onePlayerBtn);
        clickOn(enable4);
        assertEquals(statusLbl.getText(), BattleShip.SET_SHIP_4);
        //clickOn(new Point2D(3, 2),)

        clickOn(zeroX, zeroY); ///КООРДИНАТЫ НА ЭКРАНЕ (ХЕРНЯ РЕШЕНИЕ, НАДО БУДЕТ НОВОЕ ЧТО-ТО)
        assertEquals(statusLbl.getText(), BattleShip.SET_DIR);
        clickOn(zeroX, zeroY + Cell.SIZE);
        assertEquals(statusLbl.getText(), BattleShip.CHOOSE_SHIP);
        assertEquals(en4.getText(), "0");
        assertTrue(enable4.isDisable());
        for (int i = 0; i < 4; i++) {
            assertTrue(playerField.getCell(i, 0).isDeck());
            assertTrue(playerField.getCell(i, 0).isBusy());
            assertEquals(playerField.getCell(i, 0).getCellColor(), Color.RED);
            checkAllNeighbours(i, 0);
        }

        for (int k = 0; k < 2; k++) { //продолжение позже, надо высчитать точно все коорды
            clickOn(enable3);

        }



    }

    public void checkAllNeighbours(int i, int j) {
        for (int w = -1; w <= 1; w++) {
            for (int v = -1; v <= 1; v++) {
                if (w == i && v == j) {
                    continue;
                }
                if (GameField.inRange(i + w, j + v)) {
                    assertTrue(playerField.getCell(i + w, j + v).isBusy());
                }
            }
        }
    }


    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }
}