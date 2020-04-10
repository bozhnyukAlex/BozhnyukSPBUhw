import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JavaSweeper extends JFrame {

    private Game game;

    private JPanel panel; //аналог Scene, как я понимаю
    private JLabel label;
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args) {
        new JavaSweeper();
    }

    private JavaSweeper() {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages(); // подгрузка всех картинок
        initLabel();
        initPanel();
        initFrame();
    }
    private void initLabel() {
        label = new JLabel("Welcome!");
        add(label, BorderLayout.SOUTH);
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE,
                    y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.pressedLeftButton(coord);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    game.pressedRightButton(coord);
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    game.start();
                }
                label.setText(getMessage());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));
        add(panel);
    }

    private String getMessage() {
        switch (game.getState()){
            case PLAYED: {
                return "Think about your next step, comrade!";
            }
            case BOMBED: {
                return "See you on skies, comrade, you are DEAD!";
            }
            case WINNER: {
                return "Congratulations, comrade, you made it!";
            }
            default: {
                return "You are gay, comrade!";
            }
        }
    }

    private void initFrame () {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //чтобы прога вырубалась при нажатии на крестик
        setTitle("Main Sweeper");
        setResizable(false); //чтобы окно не меняло размер
        setVisible(true); //показать окно
        pack(); //установить минимальный размер контейнера, достаточный для отобраэения всех компонентов
        setLocationRelativeTo(null); //чтобы окно устанавливалось по центру
        setIconImage(getImage("icon"));
    }

    private void setImages() {
        //перебор всех элементов enum
        for (Box box : Box.values()) { //box.values() возвращает массив всех констант
            box.image = getImage(box.name().toLowerCase()); // мы вызываем поле image у каждого элемента box
        }
    }

    private Image getImage(String name) {
        String filename = "img/" + name.toLowerCase() + ".png";
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(filename));
        return imageIcon.getImage();
    }
}
