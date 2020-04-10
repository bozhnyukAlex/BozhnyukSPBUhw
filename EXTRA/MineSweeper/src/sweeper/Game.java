package sweeper;

public class Game { //основной класс игры, класс-фасад

    //Matrix bombMap;
    private Bomb bomb;
    private Flag flag;
    private GameState state;



    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows)); //устанавливаем размер поля
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public GameState getState() {
        return state;
    }

    public void start() {
        bomb.start();
        flag.start();
        state = GameState.PLAYED;
    }

    public Box getBox(Coord coord) {
        if (flag.get(coord) == Box.OPENED) {
            return bomb.get(coord);
        }
        else {
            return flag.get(coord);
        }
    }

    public void pressedLeftButton(Coord coord) {
        if (gameOver()) {
            return;
        }
        openBox(coord);
        checkWinner();
    }

    private void checkWinner() {
        if (state == GameState.PLAYED) {
            if(flag.getCountOfClosedBoxes() == bomb.getTotalBombs()) {
                state = GameState.WINNER;
            }
        }
    }

    private void openBox(Coord coord) {
        switch (flag.get(coord)) {
            case OPENED: {
                setOpenedToCloseBoxesAroundNumber(coord);
                return;
            }
            case FLAGED: {
                return;
            }
            case CLOSED: {
                switch (bomb.get(coord)) {
                    case ZERO: {
                        openBoxesAround(coord);
                        return;

                    }
                    case BOMB: {
                        openBombs(coord);
                        return;
                    }
                    default: {
                        flag.setOpenedToBox(coord);
                        return;
                    }
                }
            }
        }
    }

    private void openBombs(Coord bombed) {
        state = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coord coord : Ranges.getAllCoords()) {
            if (bomb.get(coord) == Box.BOMB) {
                flag.setOpenedToClosedBombBox(coord);
            }
            else {
                flag.setNoBombToFlaggedSafeBox(coord);
            }
        }
    }

    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord)) {
            openBox(around);
        }
    }

    public void pressedRightButton(Coord coord) {
        if (gameOver()) {
            return;
        }
        flag.toggleFlaggedToBox(coord);
    }

    private boolean gameOver() {
        if (state == GameState.PLAYED) {
            return false;
        }
        start();
        return true;
    }

    private void setOpenedToCloseBoxesAroundNumber(Coord coord) {
        if(bomb.get(coord) != Box.BOMB) {
            if (flag.getCountOfFlaggedBoxesAround(coord) == bomb.get(coord).getNumber()) {
                for (Coord around : Ranges.getCoordsAround(coord)) {
                    if (flag.get(around) == Box.CLOSED) {
                        openBox(around);
                    }
                }
            }
        }

    }
}
