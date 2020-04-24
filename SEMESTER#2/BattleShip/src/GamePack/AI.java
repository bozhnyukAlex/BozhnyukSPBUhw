package GamePack;

import java.util.Random;

public class AI {
    private GameField opponentField;
    private IntelligenceLevel iLevel;
    private Cell previousShot;
    private Cell firstHit;
    private boolean isShipFired, isShipDead, isFirstShot;
    private int findingFourShipState, findingThreeTwoShipState;
    private int bigFindingState;


    public AI (GameField opponentField, IntelligenceLevel iLevel) {
        this.opponentField = opponentField;
        this.iLevel = iLevel;
        previousShot = new Cell(-1,-1);
        isShipFired = false;
        isShipDead = false;
        isFirstShot = true;
        findingFourShipState = 1;
        findingThreeTwoShipState = 1;
        bigFindingState = 1;
    }

    public Cell makeShot() {
        int shi, shj;
        Random rnd = new Random();
        switch (iLevel) {
            case LOW:{
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
                if (isFirstShot) { //первый выстрел рандомный
                    isFirstShot = false;
                    return makeRandomShot();
                }
                else { ///к этому времени мы уже знаем инфу о предыдущем выстреле и знаем, горит ли корабль, мертв ли корабль
                    if (isShipFired) { //если так вышло, что при предыдущем выстреле корабль был ранен
                        if (isShipDead) { //если прошлый выстрел убил корабль, то можем стрелять рандомно
                            isShipDead = isShipFired = false;
                            return makeRandomShot();
                        }
                        else {
                            return makeShotToKill();
                        }
                    }
                    else { //если в при предыдущем выстреле корабль не горел, то опять стреляем рандомно
                        return makeRandomShot();
                    }
                }
            }
            case HIGH: {
                if (isFirstShot) {
                    isFirstShot = false;
                    return makeSmartShot();
                }
                else {
                    if (isShipFired) {
                        if (isShipDead) {
                            isShipFired = isShipDead = false;
                            return makeSmartShot();
                        }
                        else {
                            return makeShotToKill();
                        }
                    }
                    else {
                        return makeSmartShot();
                    }
                }
            }
        }
        return null;
    }

    private Cell makeRandomShot() {
        int shi, shj;
        Random rnd = new Random();
        while (true) {
            shi = rnd.nextInt(GameField.SIZE);
            shj = rnd.nextInt(GameField.SIZE);
            if (!opponentField.getCell(shi, shj).isShot()) {
                if (!opponentField.hasFiredShipAround(shi, shj)) {
                    if (!opponentField.getCell(shi, shj).isDeck()){
                        isShipFired = false;
                        previousShot = opponentField.getCell(shi, shj);
                    }
                    else if (opponentField.getCell(shi, shj).isDeck()){
                        isShipFired = true;
                        previousShot = opponentField.getCell(shi, shj);
                        firstHit = previousShot;
                    }
                    opponentField.getCell(shi, shj).setShot(true);
                    return opponentField.getCell(shi, shj);
                }
            }
        }
    }

    private Cell makeShotToKill() {
        Cell resShot = previousShot;
        int prevI = previousShot.getI(), prevJ = previousShot.getJ();
        if (previousShot == firstHit) { // если предыдущий выстрел был первым ранением, то начинаем стрелять в разные стороны
            if (prevI - 1 >= 0 && !opponentField.getCell(prevI - 1, prevJ).isShot()) {
                previousShot = opponentField.getCell(prevI - 1, prevJ);
                resShot = opponentField.getCell(prevI - 1, prevJ);
            }
            else if (prevI + 1 < GameField.SIZE && !opponentField.getCell(prevI + 1, prevJ).isShot()) {
                previousShot = opponentField.getCell(prevI + 1, prevJ);
                resShot = opponentField.getCell(prevI + 1, prevJ);
            }
            else if (prevJ - 1 >= 0 && !opponentField.getCell(prevI, prevJ - 1).isShot()) {
                previousShot = opponentField.getCell(prevI, prevJ - 1);
                resShot = opponentField.getCell(prevI, prevJ - 1);
            }
            else if (prevJ + 1 < GameField.SIZE && !opponentField.getCell(prevI, prevJ + 1).isShot()) {
                previousShot = opponentField.getCell(prevI, prevJ + 1);
                resShot = opponentField.getCell(prevI, prevJ + 1);
            }
        }
        else { ///если нет, то по логике мы должны были стрелять в стороны, нужно определить по предыдущему выстрелу, в каком напралении начинали двигаться
            if (prevI < firstHit.getI()) { // если стреляли вверх, то надо проверить, а не промахнулись ли мы ранее, а потом стрелять в нужное место
                if (previousShot.isDeck() && prevI - 1 >= 0 && !opponentField.getCell(prevI - 1, prevJ).isShot()) { //если в прошлый раз все же попали, то стреляем дальше вверх
                    previousShot = opponentField.getCell(prevI - 1, prevJ);
                    resShot = opponentField.getCell(prevI - 1, prevJ);
                }
                else { // иначе стреляем в другую сторону
                    if (firstHit.getI() + 1 < GameField.SIZE && !opponentField.getCell(firstHit.getI() + 1, firstHit.getJ()).isShot()) {
                        previousShot = opponentField.getCell(firstHit.getI() + 1, firstHit.getJ());
                        resShot =  opponentField.getCell(firstHit.getI() + 1, firstHit.getJ());
                    }
                    else if (firstHit.getJ() - 1 >= 0 && !opponentField.getCell(firstHit.getI(), firstHit.getJ() - 1).isShot()) {
                        previousShot = opponentField.getCell(firstHit.getI(), firstHit.getJ() - 1);
                        resShot = opponentField.getCell(firstHit.getI(), firstHit.getJ() - 1);
                    }
                    else if (firstHit.getJ() + 1 < GameField.SIZE && !opponentField.getCell(firstHit.getI(), firstHit.getJ() + 1).isShot()) {
                        previousShot = opponentField.getCell(firstHit.getI(), firstHit.getJ() + 1);
                        resShot = opponentField.getCell(firstHit.getI(), firstHit.getJ() + 1);
                    }
                }
            }
            else if (prevI > firstHit.getI()) {
                if (previousShot.isDeck() && prevI + 1 < GameField.SIZE && !opponentField.getCell(prevI + 1, prevJ).isShot()) { //если в прошлый раз все же попали, то стреляем дальше вниз
                    previousShot = opponentField.getCell(prevI + 1, prevJ);
                    resShot = opponentField.getCell(prevI + 1, prevJ);
                }
                else {
                    if (firstHit.getI() - 1 >= 0 && !opponentField.getCell(firstHit.getI() - 1, firstHit.getJ()).isShot()) {
                        previousShot = opponentField.getCell(firstHit.getI() - 1, firstHit.getJ());
                        resShot = opponentField.getCell(firstHit.getI() - 1, firstHit.getJ());
                    }
                    else if (firstHit.getJ() - 1 >= 0 && !opponentField.getCell(firstHit.getI(), firstHit.getJ() - 1).isShot()) {
                        previousShot = opponentField.getCell(firstHit.getI(), firstHit.getJ() - 1);
                        resShot = opponentField.getCell(firstHit.getI(), firstHit.getJ() - 1);
                    }
                    else if (firstHit.getJ() + 1 < GameField.SIZE && !opponentField.getCell(firstHit.getI(), firstHit.getJ() + 1).isShot()) {
                        previousShot = opponentField.getCell(firstHit.getI(), firstHit.getJ() + 1);
                        resShot = opponentField.getCell(firstHit.getI(), firstHit.getJ() + 1);
                    }

                }
            }
            else if (prevJ < firstHit.getJ()) { //влево
                if (previousShot.isDeck() && prevJ - 1 >= 0 && !opponentField.getCell(prevI, prevJ - 1).isShot()) {
                    previousShot = opponentField.getCell(prevI, prevJ - 1);
                    resShot = opponentField.getCell(prevI, prevJ - 1);
                }
                else {
                    if (firstHit.getJ() + 1 < GameField.SIZE && !opponentField.getCell(firstHit.getI(), firstHit.getJ() + 1).isShot()) {
                        previousShot = opponentField.getCell(firstHit.getI(), firstHit.getJ() + 1);
                        resShot = opponentField.getCell(firstHit.getI(), firstHit.getJ() + 1);
                    }
                    else if (firstHit.getI() - 1 >= 0 && !opponentField.getCell(firstHit.getI() - 1, firstHit.getJ()).isShot()) {
                        previousShot = opponentField.getCell(firstHit.getI() - 1, firstHit.getJ());
                        resShot = opponentField.getCell(firstHit.getI() - 1, firstHit.getJ());
                    }
                    else if (firstHit.getI() + 1 < GameField.SIZE && !opponentField.getCell(firstHit.getI() + 1, firstHit.getJ()).isShot()) {
                        previousShot = opponentField.getCell(firstHit.getI() + 1, firstHit.getJ());
                        resShot = opponentField.getCell(firstHit.getI() + 1, firstHit.getJ());
                    }

                }
            }
            else if (prevJ > firstHit.getJ()) { //вправо
                if (previousShot.isDeck() && prevJ + 1 < GameField.SIZE && !opponentField.getCell(prevI, prevJ + 1).isShot()) {
                    previousShot = opponentField.getCell(prevI, prevJ + 1);
                    resShot = opponentField.getCell(prevI, prevJ + 1);
                }
                else {
                    if (firstHit.getJ() - 1 >= 0 && opponentField.getCell(firstHit.getI(), firstHit.getJ() - 1).isShot()) {
                        previousShot = opponentField.getCell(firstHit.getI(), firstHit.getJ() - 1);
                        resShot = opponentField.getCell(firstHit.getI(), firstHit.getJ() - 1);
                    }
                    else if (firstHit.getI() - 1 >= 0 && !opponentField.getCell(firstHit.getI() - 1, firstHit.getJ()).isShot()) {
                        previousShot = opponentField.getCell(firstHit.getI() - 1, firstHit.getJ());
                        resShot = opponentField.getCell(firstHit.getI() - 1, firstHit.getJ());
                    }
                    else if (firstHit.getI() + 1 < GameField.SIZE && !opponentField.getCell(firstHit.getI() + 1, firstHit.getJ()).isShot()) {
                        previousShot = opponentField.getCell(firstHit.getI() + 1, firstHit.getJ());
                        resShot = opponentField.getCell(firstHit.getI() + 1, firstHit.getJ());
                    }
                }
            }
        }
        resShot.setShot(true);
        return resShot;
    }

    private Cell makeSmartShot() { // стратегия описана тут: https://habr.com/ru/post/180995/
        Cell resShot = previousShot;
        if (bigFindingState == 1) { // если мы еще не нашли четырехпалубный, бьем вверх по диагоналям
            int curI = 3, curJ = 0;
            findingFourShipState = 1;
            M1: while (true) {
                if (!opponentField. getCell(curI, curJ).isShot() && !opponentField.hasFiredShipAround(curI, curJ)) {
                    resShot = previousShot = opponentField.getCell(curI, curJ);
                    if (resShot.isDeck()) {
                        isShipFired = true;
                        firstHit = resShot;
                    }
                    else {
                        isShipFired = false;
                    }
                    break;
                }
                curI--;
                curJ++;
                if (curI < 0 || curJ >= GameField.SIZE) {
                    findingFourShipState++;
                    switch (findingFourShipState) {
                        case 2: {
                            curI = 7;
                            curJ = 0;
                            break;
                        }
                        case 3: {
                            curI = 9;
                            curJ = 2;
                            break;
                        }
                        case 4: {
                            curI = 9;
                            curJ = 6;
                            break;
                        }
                        case 5: {
                            bigFindingState++;
                            break M1;
                        }
                    }
                }
            }
        }
        if (bigFindingState == 2) {
            int curI = 1, curJ = 0;
            findingThreeTwoShipState = 1;
        M2:    while (true) {
                if (!opponentField.getCell(curI, curJ).isShot() && !opponentField.hasFiredShipAround(curI, curJ)) {
                    resShot = previousShot = opponentField.getCell(curI, curJ);
                    if (resShot.isDeck()) {
                        isShipFired = true;
                        firstHit = resShot;
                    }
                    else {
                        isShipFired = false;
                    }
                    break;
                }
                curI--;
                curJ++;
                if (curI < 0|| curJ >= GameField.SIZE) {
                    findingThreeTwoShipState++;
                    switch (findingThreeTwoShipState) {
                        case 2: {
                            curI = 5;
                            curJ = 0;
                            break;
                        }
                        case 3: {
                            curI = 9;
                            curJ = 0;
                            break;
                        }
                        case 4: {
                            curI = 9;
                            curJ = 4;
                            break;
                        }
                        case 5: {
                            curI = 9;
                            curJ = 8;
                            break;
                        }
                        case 6: {
                            bigFindingState++;
                            break M2;
                        }
                    }
                }
            }
        }
        if (bigFindingState == 3) { //остались только однопалубные - стреляем рандомно по оставшимся клеткам
            previousShot = resShot = makeRandomShot();
        }
        resShot.setShot(true);
        return resShot;
    }

    public void setShipDead(boolean shipDead) {
        isShipDead = shipDead;
    }

    public IntelligenceLevel getILevel() {
        return iLevel;
    }

    public void setILevel(IntelligenceLevel iLevel) {
        this.iLevel = iLevel;
    }
}
