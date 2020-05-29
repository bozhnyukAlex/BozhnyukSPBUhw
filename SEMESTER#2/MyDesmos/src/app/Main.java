package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static final String MENU_PATH = "/view/uiMenu.fxml";
    public static final String ICON_PATH = "/images/icon.png";
    public static final String TITLE = "CurveDraw";
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MENU_PATH));
        Parent root = loader.load();
        Scene startScene = new Scene(root, 920, 620);
        stage.setScene(startScene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle(TITLE);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
