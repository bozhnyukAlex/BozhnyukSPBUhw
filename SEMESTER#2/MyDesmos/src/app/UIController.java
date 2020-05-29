package app;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import math.Curve;
import math.Ellipse;
import math.Hyperbola;
import math.Parabola;

import java.util.ArrayList;

public class UIController {
    DrawingField drawingField;

    @FXML
    private AnchorPane pane;

    @FXML
    private Pane curvePane;

    @FXML
    private ComboBox<Curve> curveBox;

    @FXML
    private Button incScaleBtn;

    @FXML
    private Button decScaleBtn;

    @FXML
    private Label scaleLabel;

    @FXML
    private Button drawBtn;

    @FXML
    public void initialize() {
        drawingField = new DrawingField();
        pane.getChildren().add(drawingField);
        drawingField.setParentPane(curvePane);
        initCurves();
        curvePane.setStyle("-fx-border-color: black");
        scaleLabel.setStyle("-fx-border-color: black");
        incScaleBtn.setOnAction(actionEvent -> {
            incScale();
        });
        decScaleBtn.setOnAction(actionEvent -> {
            decScale();
        });
        drawBtn.setOnAction(actionEvent -> {
            if (curveBox.getValue() == null) {
                return;
            }
            incScaleBtn.setDisable(false);
            decScaleBtn.setDisable(false);
            drawingField.draw(curveBox.getValue());
        });
        drawingField.setOnScroll(scrollEvent -> {
            if (incScaleBtn.isDisable() || decScaleBtn.isDisable()) {
                return;
            }
            double deltaY = scrollEvent.getDeltaY();
            if (deltaY < 0) {
                decScale();
            }
            else if (deltaY > 0) {
                incScale();
            }
        });


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


    private void changeScaleLabel() {
        scaleLabel.setText(Float.toString(drawingField.getScale()));
    }

    private void incScale() {
        drawingField.incScale();
        drawingField.draw(curveBox.getValue());
        changeScaleLabel();
    }
    private void decScale() {
        drawingField.decScale();
        drawingField.draw(curveBox.getValue());
        changeScaleLabel();
    }

}
