import GamePack.GameState;
import GamePack.IntelligenceLevel;
import GamePack.Logic;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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


    @FXML
    public void initialize() {
        group = new ToggleGroup();
        lowMode.setToggleGroup(group);
        mediumMode.setToggleGroup(group);
        highMode.setToggleGroup(group);
        difficultyLevel = IntelligenceLevel.LOW;


        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton selectedButton = (RadioButton) t1;
                if (selectedButton.equals(lowMode)) {
                    difficultyLevel = IntelligenceLevel.LOW;
                }
                else if (selectedButton.equals(mediumMode)) {
                    difficultyLevel = IntelligenceLevel.MEDIUM;
                }
                else if (selectedButton.equals(highMode)) {
                    difficultyLevel = IntelligenceLevel.HIGH;
                }
            }
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


}
