package app;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VerticalDirection;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import math.Curve;
import math.Ellipse;
import math.Hyperbola;
import math.Parabola;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class UIControllerTest extends ApplicationTest {
    private Button incScaleBtn, decScaleBtn;
    private Label scaleLabel;
    private ComboBox<Curve> curveBox;
    private DrawingField drawingField;
    private AnchorPane anchorPane;
    private Pane curvePane;

    @Before
    public void setUp() {
        incScaleBtn = find("#incScaleBtn");
        decScaleBtn = find("#decScaleBtn");
        scaleLabel = find("#scaleLabel");
        curveBox = find("#curveBox");
        anchorPane = find("#pane");
        curvePane = find("#curvePane");
        drawingField = find("#drawingField");
        initCurves();
    }



    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UIController.class.getResource(Main.MENU_PATH));
        AnchorPane pane = loader.load();
        stage.setScene(new Scene(pane));
        stage.show();
    }

    public <T extends Node> T find(final String query) {
        return lookup(query).query();
    }


    @Test
    public void testDrawingEnable() {
        assertTrue(decScaleBtn.isDisable());
        assertTrue(incScaleBtn.isDisable());
        clickOn(curveBox);
        clickOn("x^2/16 + y^2/9 = 1");
        assertFalse(decScaleBtn.isDisable());
        assertFalse(incScaleBtn.isDisable());
        clickOn(curveBox);
        clickOn("x^2/6.25 + y^2/6.25 = 1");
        clickOn(curveBox);
        clickOn("x^2/1 + y^2/4 = 1");
        clickOn(curveBox);
        clickOn("x^2/12.25 - y^2/6.25 = 1");
        clickOn(curveBox);
        clickOn("x^2/4 - y^2/16 = 1");
        clickOn(curveBox);
        clickOn("y^2 = 4*x");
        clickOn(curveBox);
        clickOn("y^2 = -8*x");
    }

    @Test
    public void testChangingScaleBtn() {
        clickOn(curveBox);
        clickOn("x^2/16 + y^2/9 = 1");
        clickOn(decScaleBtn);
        assertEquals("0.9", scaleLabel.getText());
        clickOn(incScaleBtn);
        assertEquals("1.0", scaleLabel.getText());
        int clickCnt = 40;
        for (int i = 0; i < clickCnt; i++) {
            clickOn(decScaleBtn);
        }
        assertEquals("0.1", scaleLabel.getText());
        for (int i = 0; i < clickCnt; i++) {
            clickOn(incScaleBtn);
        }
        assertEquals("3.0", scaleLabel.getText());
    }
    @Test
    public void testScrolling() {
        clickOn(curveBox);
        clickOn("x^2/16 + y^2/9 = 1");
        moveTo(drawingField);
        scroll(VerticalDirection.UP);
        assertEquals("1.1", scaleLabel.getText());
        scroll(VerticalDirection.DOWN);
        assertEquals("1.0", scaleLabel.getText());
        int clickCnt = 40;
        for (int i = 0; i < clickCnt; i++) {
            scroll(VerticalDirection.DOWN);
        }
        assertEquals("0.1", scaleLabel.getText());
        for (int i = 0; i < clickCnt; i++) {
            scroll(VerticalDirection.UP);
        }
        assertEquals("3.0", scaleLabel.getText());
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    public void initCurves() {
        ArrayList<Curve> toBox = new ArrayList<>();
        toBox.add(new Ellipse(4,3, (float) (-drawingField.getWidth() / 2), (float) drawingField.getWidth() / 2));
        toBox.add(new Ellipse(2.5f, 2.5f, (float) (-drawingField.getWidth() / 2), (float) drawingField.getWidth() / 2));
        toBox.add(new Ellipse(1, 2,(float) (-drawingField.getWidth() / 2), (float) drawingField.getWidth() / 2));
        toBox.add(new Hyperbola(3.5f, 2.5f, (float) (-drawingField.getWidth() / 2), (float) drawingField.getWidth() / 2));
        toBox.add(new Hyperbola(2, 4, (float) (-drawingField.getWidth() / 2), (float) drawingField.getWidth() / 2));
        toBox.add(new Parabola(2, (float) (-drawingField.getWidth() / 2), (float) drawingField.getWidth() / 2));
        toBox.add(new Parabola(-4, (float) (-drawingField.getWidth() / 2), (float) drawingField.getWidth() / 2));
        curveBox.setItems(FXCollections.observableArrayList(toBox));
    }
}