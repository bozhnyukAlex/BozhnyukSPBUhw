package org.localePlugins;

import org.app.LocaleService;
import org.app.StringConst;

public class EnglishLocale implements LocaleService {
    @Override
    public void locale() {
        StringConst.CHOOSE_GAME_MODE = "Choose Game Mode";
        StringConst.TITLE = "BattleShip";
        StringConst.CELL_IS_BUSY = "This place is busy!";
        StringConst.SET_DIR = "Press on the field again for setting direction";
        StringConst.CHOOSE_SHIP = "Choose ship";
        StringConst.DELETE_SHIP = "Delete ship";
        StringConst.YOU_ARE_READY = "You are ready to fight!";
        StringConst.YOUR_MOVE = "Your move!";
        StringConst.ENEMY_MOVE = "Enemy's move!";
        StringConst.YOU_WON = "You win!";
        StringConst.YOU_LOSE = "You lose!";
        StringConst.FIGHT = "Fight!";
        StringConst.PREPARE = "Preparation";
        StringConst.PREPARE_FIRST = "Preparation of the first player";
        StringConst.PREPARE_SECOND = "Preparation of the second player";
        StringConst.MOVE_FIRST = "First player move";
        StringConst.MOVE_SECOND = "Second player move";
        StringConst.FIRST_WON = "First player won!";
        StringConst.SECOND_WON = "Second player won!";
        StringConst.EDIT_AI = "Difficulty level";
        StringConst.LEVEL_EDITED = "Difficulty changed";
        StringConst.SET_SHIP_1 = "Set ship with 1 deck";
        StringConst.SET_SHIP_2 = "Set ship with 2 deck";
        StringConst.SET_SHIP_3 = "Set ship with 3 deck";
        StringConst.SET_SHIP_4 = "Set ship with 4 deck";
        StringConst.ONE_PLAYER_GAME = "One Player";
        StringConst.TWO_PLAYER_GAME = "Two Player";
        StringConst.DIFF_SETTINGS = "Difficulty";
        StringConst.SHIP4 = "4 decks";
        StringConst.SHIP3 = "3 decks";
        StringConst.SHIP2 = "2 decks";
        StringConst.SHIP1 = "1 deck";
        StringConst.AUTOMATIC = "Auto set";
        StringConst.READY = "Ready";
        StringConst.SHIPS_LEFT = "Ships left:";
        StringConst.EXIT = "Exit";
        StringConst.LOW_DIFF = "I want to heat";
        StringConst.MEDIUM_DIFF = "Ultra-heating";
        StringConst.HIGH_DIFF = "Nightmare!";
        StringConst.CANCEL = "Close";
        StringConst.LEFT_ABC = "      A    B    C   D   E    F   G   H   I   K";
        StringConst.RIGHT_ABC = " A    B    C   D   E    F   G   H   I   K";
        StringConst.CHOOSE_DIFF = "Choose Difficulty";
        StringConst.LANGUAGE_EDITED = "Language edited";
        StringConst.SET_LANG = "Language";
    }

    @Override
    public String getName() {
        return "English";
    }

}
