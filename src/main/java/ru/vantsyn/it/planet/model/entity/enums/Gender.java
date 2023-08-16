package ru.vantsyn.it.planet.model.entity.enums;

public enum Gender {
    MALE,
    FEMALE,
    OTHER;

    public static Gender getInstance(String gender) {
        if (gender == null) {
            return null;
        }
        if (gender.equals("MALE")) {
            return MALE;
        } else if (gender.equals("FEMALE")) {
            return FEMALE;
        }

        return gender.equals("OTHER") ? OTHER : null;
    }
}
