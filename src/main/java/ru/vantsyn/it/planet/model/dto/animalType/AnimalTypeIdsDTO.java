package ru.vantsyn.it.planet.model.dto.animalType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AnimalTypeIdsDTO {
    @NotNull
    @Min(1)
    private Long oldTypeId;
    @NotNull
    @Min(1)
    private Long newTypeId;

    public AnimalTypeIdsDTO() {
    }

    public Long getOldTypeId() {
        return oldTypeId;
    }

    public void setOldTypeId(Long oldTypeId) {
        this.oldTypeId = oldTypeId;
    }

    public Long getNewTypeId() {
        return newTypeId;
    }

    public void setNewTypeId(Long newTypeId) {
        this.newTypeId = newTypeId;
    }
}
