import GamePack.GameState;
import GamePack.IntelligenceLevel;
import GamePack.Logic;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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

    private Stage dialogStage;
    private ToggleGroup group;
    private Logic observableLogic;
    private IntelligenceLevel difficultyLevel;
    private boolean okClicked;


    @FXML
    public void initialize() {
        group = new ToggleGroup();
        lowMode.setToggleGroup(group);
        mediumMode.setToggleGroup(group);
        highMode.setToggleGroup(group);
        difficultyLevel = IntelligenceLevel.MEDIUM;
        okClicked = false;
        okBtn.setDisable(true);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                okBtn.setDisable(false);
            }
        });

        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
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
            }
        });

        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dialogStage.close();
            }
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setDifficultyLevel(IntelligenceLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Logic getObservableLogic() {
        return observableLogic;
    }

    public void setObservableLogic(Logic observableLogic) {
        this.observableLogic = observableLogic;
    }
}
