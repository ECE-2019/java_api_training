package fr.lernejo.navy_battle.enums;

public enum CellEnum {
    EMPTY("."),
    MISSED_FIRE("-"),
    SUCCESSFUL_FIRE("X"),
    BOAT("B");

    private final String letter;

    CellEnum(String letter) {
        this.letter = letter;
    }

}
