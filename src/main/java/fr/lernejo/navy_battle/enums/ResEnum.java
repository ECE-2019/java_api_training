package fr.lernejo.navy_battle.enums;

import java.util.Arrays;

public enum ResEnum {
    MISS("miss"), HIT("hit"), SUNK("sunk");

    private final String apiString;

    ResEnum(String res) {
        this.apiString = res;
    }


    public static ResEnum fromAPI(String value) {
        var res = Arrays.stream(ResEnum.values()).filter(f -> f.apiString.equals(value)).findFirst();

        if (res.isEmpty())
            throw new RuntimeException("Invalid value!");

        return res.get();
    }

    public String toAPI() {
        return apiString;
    }
}
