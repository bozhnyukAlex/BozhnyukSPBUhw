package GamePack;

import java.util.ArrayList;
import java.util.Random;

public class AI {
    private GameField opponentField;
    private IntelligenceLevel iLevel;

    public AI(GameField opponentField, IntelligenceLevel iLevel) {
        this.opponentField = opponentField;
        this.iLevel = iLevel;
    }

    public Cell makeShot() {
        int shi, shj;
        switch (iLevel) {
            case LOW:{
                Random rnd = new Random();
                while (true) {
                    shi = rnd.nextInt(GameField.SIZE);
                    shj = rnd.nextInt(GameField.SIZE);
                    if (!opponentField.getCell(shi, shj).isShot()) {
                        opponentField.getCell(shi, shj).setShot(true);
                        return opponentField.getCell(shi, shj);
                    }
                }
            }
            case MEDIUM: {

                break;
            }
            case HIGH: {
                break;
            }
        }
        return null;
    }


    public GameField getOpponentField() {
        return opponentField;
    }

    public void setOpponentField(GameField opponentField) {
        this.opponentField = opponentField;
    }

    public IntelligenceLevel getiLevel() {
        return iLevel;
    }

    public void setiLevel(IntelligenceLevel iLevel) {
        this.iLevel = iLevel;
    }
}
