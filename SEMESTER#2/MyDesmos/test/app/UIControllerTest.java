package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit.ApplicationTest;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class UIControllerTest extends ApplicationTest {

    private Button bntDraw;
    private Button incScaleBtn, decScaleBtn;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UIController.class.getResource(Main.MENU_PATH));
        AnchorPane pane = loader.load();
        stage.setScene(new Scene(pane));
        stage.show();
    }

    @BeforeEach
    void setUp() {
    }


}