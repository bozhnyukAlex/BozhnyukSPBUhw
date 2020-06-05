package org.game;

public enum Condition {
    EMPTY, // yes
    SHIP_DECK, //yes
    SHIP_AROUND, //устанавливать после подбития корабля
    SHOT_WATER, // yes
    SHIP_DAMAGED, // yes
    SHIP_KILLED_PLAYER, // yes
    SHIP_KILLED_ENEMY, // yes
    SHIP_KILLED_TWO_PLAYERS,// yes
    SHIP_FIRST_CLICK,
    SHIP_REDRAW
}
