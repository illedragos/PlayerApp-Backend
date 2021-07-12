package com.example.demo.player;

import java.util.HashMap;
import java.util.Map;

public enum RankEnum {
    AMATEUR("A"),
    PRO("P"),
    SEMI_PRO("SP"),
    WORLD_CLASS("W"),
    BEGINNER("B"),
    LEGEND("L");
    private String name;

    RankEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private static final Map<String, RankEnum> lookup = new HashMap<>();

    static {
        for (RankEnum rankEnum : RankEnum.values()) {
            lookup.put(rankEnum.getName(), rankEnum);
        }
    }

    public static RankEnum get(String name) {
        return lookup.get(name);
    }
}
