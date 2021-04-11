package org.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static final String MENU_PATH = "/view/battleMenu.fxml";
    public static final String ICON_PATH = "/images/icon.png";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MENU_PATH));
        Parent root = loader.load();
        Scene startScene = new Scene(root, 810, 435);
        stage.setScene(startScene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle(StringConst.TITLE);
        stage.getIcons().add(new Image(getClass().getResourceAsStream(ICON_PATH)));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
