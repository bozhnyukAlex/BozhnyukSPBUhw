import GamePack.Cell;
import GamePack.Logic;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BattleShip extends Application {
    @FXML
    private VBox pane;
    private Scene startScene;
    @FXML
    private Button startButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Canvas playerField;
    @FXML
    private Canvas enemyField;
    @FXML
    private Button enable4Ship;
    @FXML
    private Button enable3Ship;
    @FXML
    private Button enable2Ship;
    @FXML
    private Button enable1Ship;
    @FXML
    private Button readyButton;
    @FXML
    private Label playerShipsLeft;
    @FXML
    private Label enemyShipsLeft;
    @FXML
    private Pane enemyPane;
    @FXML
    private Label oneShipToGo;
    @FXML
    private Label twoShipToGo;
    @FXML
    private Label threeShipToGo;
    @FXML
    private Label fourShipToGo;

    private Cell[][] cells;
    private Logic logic;





    @Override
    public void start(Stage stage) throws Exception {
        pane = FXMLLoader.load(getClass().getResource("battlemenu.fxml"));
        startScene = new Scene(pane, 810, 435);
        stage.setScene(startScene);
        stage.centerOnScreen();
        stage.setTitle("BattleShip");
        stage.show();


    }

    @FXML
    public void initialize() throws Exception {
       // super.init();
        cells = new Cell[10][10];
        drawField(playerField);
        logic = new Logic();
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                statusLabel.setText("Подготовка");
            }
        });
        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });



    }

    public void drawField(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10 ; j++) {
                cells[i][j] = new Cell(i * Cell.SIZE, j * Cell.SIZE);
                cells[i][j].draw(gc, i * Cell.SIZE, j * Cell.SIZE);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
