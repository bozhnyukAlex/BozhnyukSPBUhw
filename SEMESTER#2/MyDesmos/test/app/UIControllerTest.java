package app;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
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
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UIControllerTest extends ApplicationTest {

    private Button incScaleBtn, decScaleBtn;
    private Label scaleLabel;
    private ComboBox<Curve> curveBox;
    private DrawingField drawingField;
    private AnchorPane anchorPane;
    private Pane curvePane;


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

    @BeforeEach
    void setUp() {
        incScaleBtn = find("#incScaleBtn");
        decScaleBtn = find("#decScaleBtn");
        scaleLabel = find("#scaleLabel");
        curveBox = find("#curveBox");
        anchorPane = find("#pane");
        curvePane = find("#curvePane");
        drawingField = new DrawingField();
        anchorPane.getChildren().add(drawingField);
        drawingField.setParentPane(curvePane);
        initCurves();

    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    public void initCurves() {
        ArrayList<Curve> toBox = new ArrayList<>();
        toBox.add(new Ellipse(4,3, (float) (-drawingField.getWidth() / 2), (float) drawingField.getWidth() / 2));
        toBox.add(new Ellipse(5,5, (float) (-drawingField.getWidth() / 2), (float) drawingField.getWidth() / 2));
        toBox.add(new Ellipse(1, 2,(float) (-drawingField.getWidth() / 2), (float) drawingField.getWidth() / 2));
        toBox.add(new Hyperbola(3.5f, 2.5f, (float) (-drawingField.getWidth() / 2), (float) drawingField.getWidth() / 2));
        toBox.add(new Hyperbola(2, 4, (float) (-drawingField.getWidth() / 2), (float) drawingField.getWidth() / 2));
        toBox.add(new Parabola(2, (float) (-drawingField.getWidth() / 2), (float) drawingField.getWidth() / 2));
        toBox.add(new Parabola(-4, (float) (-drawingField.getWidth() / 2), (float) drawingField.getWidth() / 2));
        curveBox.setItems(FXCollections.observableArrayList(toBox));
    }


}