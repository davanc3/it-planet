package ru.vantsyn.it.planet.model.entity.enums;

public enum LifeStatus {
    ALIVE,
    DEAD;

    public static LifeStatus getInstance(String lifeStatus) {
        if (lifeStatus == null) {
            return null;
        }
        if (lifeStatus.equals("ALIVE")) {
            return ALIVE;
        }

        return lifeStatus.equals("DEAD") ? DEAD : null;
    }
}
