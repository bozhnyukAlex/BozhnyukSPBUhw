package org.localePlugins;

import org.app.LocaleService;
import org.app.StringConst;

public class RussianLocale implements LocaleService {
    @Override
    public void locale() {
        StringConst.CHOOSE_GAME_MODE = "Выберите режим игры";
        StringConst.TITLE = "Морской бой";
        StringConst.CELL_IS_BUSY = "Сюда ставить нельзя";
        StringConst.SET_DIR = "Нажмите на поле еще раз для установки направления";
        StringConst.CHOOSE_SHIP = "Выберите корабль";
        StringConst.DELETE_SHIP = "Удалить корабль";
        StringConst.YOU_ARE_READY = "Вы готовы к бою!";
        StringConst.YOUR_MOVE = "Ваш ход!";
        StringConst.ENEMY_MOVE = "Ход противника!";
        StringConst.YOU_WON = "Вы победили!";
        StringConst.YOU_LOSE = "Вы проиграли!";
        StringConst.FIGHT = "Бой!";
        StringConst.PREPARE = "Подготовка";
        StringConst.PREPARE_FIRST = "Подготовка первого игрока";
        StringConst.PREPARE_SECOND = "Подготовка второго игрока";
        StringConst.MOVE_FIRST = "Ход первого игрока";
        StringConst.MOVE_SECOND = "Ход второго игрока";
        StringConst.FIRST_WON = "Выиграл первый игрок";
        StringConst.SECOND_WON = "Выиграл второй игрок";
        StringConst.EDIT_AI = "Уровень сложности";
        StringConst.LEVEL_EDITED = "Уровень сложности изменен";
        StringConst.SET_SHIP_1 = "Поставьте 1 палубный корабль";
        StringConst.SET_SHIP_2 = "Поставьте 2-х палубный корабль";
        StringConst.SET_SHIP_3 = "Поставьте 3-х палубный корабль";
        StringConst.SET_SHIP_4 = "Поставьте 4-х палубный корабль";
        StringConst.ONE_PLAYER_GAME = "Одиночная игра";
        StringConst.TWO_PLAYER_GAME = "Два игрока";
        StringConst.DIFF_SETTINGS = "Сложность";
        StringConst.SHIP4 = "4-х палубный";
        StringConst.SHIP3 = "3-х палубный";
        StringConst.SHIP2 = "2-х палубный";
        StringConst.SHIP1 = "1 палубный";
        StringConst.AUTOMATIC = "Расставить автоматически";
        StringConst.READY = "Готово";
        StringConst.SHIPS_LEFT = "Осталось кораблей:";
        StringConst.EXIT = "Выход";
        StringConst.LOW_DIFF = "Я просто хочу топить";
        StringConst.MEDIUM_DIFF = "Ультра-потоп";
        StringConst.HIGH_DIFF = "Кошмар!";
        StringConst.CANCEL = "Закрыть";
        StringConst.LEFT_ABC = "      А    Б    В   Г   Д    Е   Ж   З   И   К";
        StringConst.RIGHT_ABC = " А    Б    В   Г   Д    Е   Ж   З   И   К";
        StringConst.LANGUAGE_EDITED = "Язык изменен";
        StringConst.SET_LANG = "Язык";
        StringConst.CHOOSE_DIFF = "Выберите уровень сложности";
    }

    @Override
    public String getName() {
        return "Русский";
    }
}
