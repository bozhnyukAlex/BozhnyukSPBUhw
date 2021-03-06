package org.app;

import javafx.scene.control.Label;
import org.game.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SettingsWindow {
    @FXML
    private RadioButton lowMode;
    @FXML
    private RadioButton mediumMode;
    @FXML
    private RadioButton highMode;
    @FXML
    private Button okBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Label chooseLabel;

    private Stage dialogStage;
    private ToggleGroup group;
    private Logic observableLogic;
    private boolean okClicked;


    @FXML
    public void initialize() {
        setLocale();
        group = new ToggleGroup();
        lowMode.setToggleGroup(group);
        mediumMode.setToggleGroup(group);
        highMode.setToggleGroup(group);
        okClicked = false;
        okBtn.setDisable(true);

        group.selectedToggleProperty().addListener((observableValue, toggle, t1) -> okBtn.setDisable(false));

        okBtn.setOnAction(actionEvent -> {
            RadioButton selected = (RadioButton) group.getSelectedToggle();
            if (selected.equals(lowMode)) {
                observableLogic.setDifficulty(IntelligenceLevel.LOW);
            }
            else if (selected.equals(mediumMode)) {
                observableLogic.setDifficulty(IntelligenceLevel.MEDIUM);
            }
            else if (selected.equals(highMode)) {
                observableLogic.setDifficulty(IntelligenceLevel.HIGH);
            }
            okClicked = true;
            dialogStage.close();
        });

        cancelBtn.setOnAction(actionEvent -> dialogStage.close());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    private void setLocale() {
        cancelBtn.setText(StringConst.CANCEL);
        lowMode.setText(StringConst.LOW_DIFF);
        mediumMode.setText(StringConst.MEDIUM_DIFF);
        highMode.setText(StringConst.HIGH_DIFF);
        chooseLabel.setText(StringConst.CHOOSE_DIFF);
    }

    public void setObservableLogic(Logic observableLogic) {
        this.observableLogic = observableLogic;
    }
}
