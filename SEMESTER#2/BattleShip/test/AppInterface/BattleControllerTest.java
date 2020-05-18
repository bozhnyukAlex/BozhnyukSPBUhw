package AppInterface;

import org.app.BattleController;
import org.app.StringConst;
import org.game.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Random;

import static org.junit.Assert.*;

public class BattleControllerTest extends ApplicationTest {
    private String DELETE = "Удалить корабль";
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
    private String ENEMY_FIELD = "#enemyField";
    private String ONE_SHIP_GO = "#oneShipToGoLab";
    private String TWO_SHIP_GO = "#twoShipToGoLab";
    private String THREE_SHIP_GO = "#threeShipToGoLab";
    private String FOUR_SHIP_GO = "#fourShipToGoLab";
    private String PL_LEFT_ID = "#playerShipsLeft";
    private String EN_LEFT_ID = "#enemyShipsLeft";
    private int zeroXPl, zeroYPL, zeroXEN, zeroYEN;

    private Random rnd;
    private Rectangle2D primScreenBounds;
    private Stage prStage;
    private Button onePlayerBtn, twoPlayerBtn, settingsBtn;
    private Button enable1, enable2, enable3, enable4;
    private Button autoGenerate, readyBtn;
    private Label statusLbl, en1, en2, en3, en4;
    private Label playerLeft, enemyLeft;
    private GameField playerField, enemyField;
    private BattleController controller;
    private Logic logic;


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BattleController.class.getResource(BattleController.MENU_PATH));
        //loader.setController(controller);
        prStage = stage;
        //VBox pane = FXMLLoader.load(BattleShip.class.getResource(BattleShip.MENU_PATH));
        VBox pane = loader.load();
      //  controller = (BattleShip) loader.getController();
        stage.setScene(new Scene(pane));
        stage.show();
        primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public <T extends Node> T find(final String query) {
        return lookup(query).query();
    }

    @Before
    public void setUp() {
        rnd = new Random();
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
        playerLeft = find(PL_LEFT_ID);
        enemyLeft = find(EN_LEFT_ID);
        zeroXPl = zeroXEN = (int) (primScreenBounds.getWidth() - prStage.getWidth()) / 2;
        zeroXPl += playerField.getLayoutX() + 10;
        zeroYPL = zeroYEN = (int) (primScreenBounds.getHeight() - prStage.getHeight()) / 2;
        zeroYPL += playerField.getLayoutY() + 10;
       // logic = controller.getLogicForTest();
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
        assertEquals(statusLbl.getText(), StringConst.PREPARE);
    }

    @Test
    public void shipSettingTest() throws InterruptedException {
       // Thread.sleep(30000);
        clickOn(onePlayerBtn);
     //   logic = controller.getLogicForTest();
        //assertNotNull(logic);
        clickOn(enable4);
        assertEquals(statusLbl.getText(), StringConst.SET_SHIP_4);
        clickOn(zeroXPl, zeroYPL);
        assertEquals(statusLbl.getText(), StringConst.SET_DIR);
        clickOn(zeroXPl, zeroYPL + Cell.SIZE);
        assertEquals(statusLbl.getText(), StringConst.CHOOSE_SHIP);
        assertEquals(en4.getText(), "0");
        assertTrue(enable4.isDisable());
        assertTrue(playerField.getCell(0, 0).isDeck() && playerField.getCell(1, 0).isDeck() && playerField.getCell(2, 0).isDeck() && playerField.getCell(3, 0).isDeck());

        clickOn(enable3);
        assertEquals(statusLbl.getText(), StringConst.SET_SHIP_3);
        clickOn(zeroXPl + Cell.SIZE * 5, zeroYPL + Cell.SIZE * 6);
        assertEquals(statusLbl.getText(), StringConst.SET_DIR);
        clickOn(zeroXPl + Cell.SIZE * 5, zeroYPL + Cell.SIZE * 5);
        assertEquals(en3.getText(), "1");
        assertEquals(statusLbl.getText(), StringConst.CHOOSE_SHIP);
        assertTrue(playerField.getCell(6, 5).isDeck() && playerField.getCell(5,5).isDeck() && playerField.getCell(4,5).isDeck());

        clickOn(enable2);
        assertEquals(statusLbl.getText(), StringConst.SET_SHIP_2);
        clickOn(zeroXPl + 7 * Cell.SIZE, zeroYPL + Cell.SIZE * 3);
        assertEquals(statusLbl.getText(), StringConst.SET_DIR);
        clickOn(zeroXPl + 8 * Cell.SIZE, zeroYPL + Cell.SIZE * 3);
        assertEquals(en2.getText(), "2");
        assertEquals(statusLbl.getText(), StringConst.CHOOSE_SHIP);
        assertTrue(playerField.getCell(3,7).isDeck() && playerField.getCell(3, 8).isDeck());

        clickOn(enable1);
        assertEquals(statusLbl.getText(), StringConst.SET_SHIP_1);
        clickOn(zeroXPl + Cell.SIZE * 9, zeroYPL);
        assertEquals(en1.getText(), "3");
        assertEquals(statusLbl.getText(), StringConst.CHOOSE_SHIP);
        assertTrue(playerField.getCell(0, 9).isDeck());

    }

    @Test
    public void isBusyTest() {
        clickOn(onePlayerBtn);
        clickOn(enable4);
        clickOn(zeroXPl + Cell.SIZE * 5, zeroYPL + Cell.SIZE * 5);
        clickOn(zeroXPl + Cell.SIZE * 5, zeroYPL + Cell.SIZE * 6);
        clickOn(enable1);
        clickOn(zeroXPl + Cell.SIZE * 4, zeroYPL + Cell.SIZE * 5);
        assertEquals(statusLbl.getText(), StringConst.CELL_IS_BUSY);
    }

    @Test
    public void inRangeTest() {
        clickOn(onePlayerBtn);
        clickOn(enable4);
        clickOn(zeroXPl + Cell.SIZE, zeroYPL);
        clickOn(zeroXPl, zeroYPL);
        assertEquals(statusLbl.getText(), StringConst.CELL_IS_BUSY);

    }

    @Test
    public void deleteShipTest() {
        clickOn(onePlayerBtn);
        clickOn(enable4);
        clickOn(zeroXPl + Cell.SIZE * 5, zeroYPL + Cell.SIZE * 6);
        clickOn(zeroXPl + Cell.SIZE * 5, zeroYPL + Cell.SIZE * 5);
        rightClickOn(zeroXPl + Cell.SIZE * 5, zeroYPL + Cell.SIZE * 6);
        clickOn(DELETE);
        assertTrue(!playerField.getCell(6, 5).isDeck() && !playerField.getCell(5,5).isDeck() && !playerField.getCell(4,5).isDeck() && !playerField.getCell(3,5).isDeck());
    }

    @Test
    public void autoSetTest() {
        clickOn(onePlayerBtn);
        clickOn(autoGenerate);
        int deckCnt = 0;
        for (int i = 0; i < GameField.SIZE; i++) {
            for (int j = 0; j < GameField.SIZE; j++) {
                if (playerField.getCell(i, j).isDeck()) {
                    deckCnt++;
                    checkAllNeighbours(i, j);
                }
            }
        }
        assertEquals(deckCnt, 20);
        assertEquals(statusLbl.getText(), StringConst.YOU_ARE_READY);
        assertFalse(readyBtn.isDisable());
    }

    @Test
    public void settingsTest() {
        clickOn(onePlayerBtn);
        clickOn(settingsBtn);
        clickOn("Кошмар!");
        clickOn("OK");
        assertEquals(statusLbl.getText(), StringConst.LEVEL_EDITED);

    }

    @Test
    public void readyAndFightTest() {
        clickOn(onePlayerBtn);
        clickOn(autoGenerate);
        clickOn(readyBtn);
        enemyFieldInit();
        boolean isLose = false, isWin = false;
        assertEquals(statusLbl.getText(), StringConst.FIGHT);
        for (int i = 0; i < GameField.SIZE / 2 + 2; i++) {
            for (int j = 0; j < GameField.SIZE; j++) {
                clickOn(zeroXEN + Cell.SIZE * j, zeroYEN + Cell.SIZE * i);
                if (statusLbl.getText().equals(StringConst.YOU_LOSE)) {
                    isLose = true;
                    break;
                }
                else if (statusLbl.getText().equals(StringConst.YOU_WON)) {
                    isWin = true;
                    break;
                }
            }
        }
        if (isLose) {
            assertEquals(playerLeft.getText(), "0");
        }
        else if (isWin) {
            assertEquals(enemyLeft.getText(), "0");
        }
        assertNotSame("10", playerLeft.getText());
        assertNotSame("10", enemyLeft.getText());
    }

    @Test
    public void setOnSecondPlayerShipTest() {
        clickOn(twoPlayerBtn);
        clickOn(autoGenerate);
        clickOn(readyBtn);
        enemyFieldInit();
        clickOn(enable4);
        assertEquals(statusLbl.getText(), StringConst.SET_SHIP_4);
        clickOn(zeroXEN, zeroYEN);
        assertEquals(statusLbl.getText(), StringConst.SET_DIR);
        clickOn(zeroXEN, zeroYEN + Cell.SIZE);
        assertEquals(statusLbl.getText(), StringConst.CHOOSE_SHIP);
        assertEquals(en4.getText(), "0");
        assertTrue(enable4.isDisable());
        assertTrue(enemyField.getCell(0, 0).isDeck() && enemyField.getCell(1, 0).isDeck() && enemyField.getCell(2, 0).isDeck() && enemyField.getCell(3, 0).isDeck());

        clickOn(enable3);
        assertEquals(statusLbl.getText(), StringConst.SET_SHIP_3);
        clickOn(zeroXEN + Cell.SIZE * 5, zeroYEN + Cell.SIZE * 6);
        assertEquals(statusLbl.getText(), StringConst.SET_DIR);
        clickOn(zeroXEN + Cell.SIZE * 5, zeroYEN + Cell.SIZE * 5);
        assertEquals(en3.getText(), "1");
        assertEquals(statusLbl.getText(), StringConst.CHOOSE_SHIP);
        assertTrue(enemyField.getCell(6, 5).isDeck() && enemyField.getCell(5,5).isDeck() && enemyField.getCell(4,5).isDeck());

        clickOn(enable2);
        assertEquals(statusLbl.getText(), StringConst.SET_SHIP_2);
        clickOn(zeroXEN + 7 * Cell.SIZE, zeroYEN + Cell.SIZE * 3);
        assertEquals(statusLbl.getText(), StringConst.SET_DIR);
        clickOn(zeroXEN + 8 * Cell.SIZE, zeroYEN + Cell.SIZE * 3);
        assertEquals(en2.getText(), "2");
        assertEquals(statusLbl.getText(), StringConst.CHOOSE_SHIP);
        assertTrue(enemyField.getCell(3,7).isDeck() && enemyField.getCell(3, 8).isDeck());

        clickOn(enable1);
        assertEquals(statusLbl.getText(), StringConst.SET_SHIP_1);
        clickOn(zeroXEN + Cell.SIZE * 9, zeroYEN);
        assertEquals(en1.getText(), "3");
        assertEquals(statusLbl.getText(), StringConst.CHOOSE_SHIP);
        assertTrue(enemyField.getCell(0, 9).isDeck());
    }

    @Test
    public void twoPlayersStepsTest() {
        clickOn(twoPlayerBtn);
        assertTrue(settingsBtn.isDisable());
        clickOn(autoGenerate);
        clickOn(readyBtn);
        enemyFieldInit();
        clickOn(autoGenerate);
        clickOn(readyBtn);
        int di, dj;
        while (true) {
            di = rnd.nextInt(GameField.SIZE);
            dj = rnd.nextInt(GameField.SIZE);
            clickOn(zeroXEN + dj * Cell.SIZE + 10, zeroYEN + di * Cell.SIZE + 10);
            if (!enemyField.getCell(di, dj).isDeck()) {
                break;
            }
            assertTrue(enemyField.getCell(di, dj).isShot());
        }
        while (true) {
            di = rnd.nextInt(GameField.SIZE);
            dj = rnd.nextInt(GameField.SIZE);
            clickOn(zeroXPl + dj * Cell.SIZE + 10, zeroYPL + di * Cell.SIZE + 10);
            if (!playerField.getCell(di, dj).isDeck()) {
                break;
            }
            assertTrue(playerField.getCell(di, dj).isShot());
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

    public void enemyFieldInit() {
        enemyField = find(ENEMY_FIELD);
        zeroXEN += enemyField.getLayoutX() + 10;
        zeroYEN += enemyField.getLayoutY() + 10;
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }
}