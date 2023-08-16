package ru.vantsyn.it.planet.model.dto.animalType;

import jakarta.validation.constraints.NotBlank;

public class AnimalTypeDTO {
    @NotBlank
    private String type;

    public AnimalTypeDTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
