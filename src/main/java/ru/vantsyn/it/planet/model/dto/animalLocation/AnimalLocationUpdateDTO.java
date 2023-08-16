package ru.vantsyn.it.planet.model.dto.animalLocation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AnimalLocationUpdateDTO {
    @NotNull
    @Min(1)
    private Long visitedLocationPointId;

    @NotNull
    @Min(1)
    private Long locationPointId;

    public AnimalLocationUpdateDTO() {
    }

    public Long getVisitedLocationPointId() {
        return visitedLocationPointId;
    }

    public void setVisitedLocationPointId(Long visitedLocationPointId) {
        this.visitedLocationPointId = visitedLocationPointId;
    }

    public Long getLocationPointId() {
        return locationPointId;
    }

    public void setLocationPointId(Long locationPointId) {
        this.locationPointId = locationPointId;
    }
}
