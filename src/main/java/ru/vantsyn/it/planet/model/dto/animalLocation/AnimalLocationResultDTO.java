package ru.vantsyn.it.planet.model.dto.animalLocation;

import java.time.OffsetDateTime;

public class AnimalLocationResultDTO {
    private long id;
    private OffsetDateTime dateTimeOfVisitLocationPoint;
    private long locationPointId;

    public AnimalLocationResultDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OffsetDateTime getDateTimeOfVisitLocationPoint() {
        return dateTimeOfVisitLocationPoint;
    }

    public void setDateTimeOfVisitLocationPoint(OffsetDateTime dateTimeOfVisitLocationPoint) {
        this.dateTimeOfVisitLocationPoint = dateTimeOfVisitLocationPoint;
    }

    public long getLocationPointId() {
        return locationPointId;
    }

    public void setLocationPointId(long locationPointId) {
        this.locationPointId = locationPointId;
    }
}
