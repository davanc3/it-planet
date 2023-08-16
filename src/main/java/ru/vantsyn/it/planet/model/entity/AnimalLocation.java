package ru.vantsyn.it.planet.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "animal_to_location")
public class AnimalLocation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date_time_of_visit_location_point", columnDefinition = "TIMESTAMP (6)")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime dateTimeOfVisitLocationPoint;

    @ManyToOne
    @JoinColumn(name="animal_id")
    @JsonIgnore
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location locationPointId;

    public AnimalLocation() {
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

    public void setDateTimeOfVisitLocationPointByNow() {
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        int nano = offsetDateTime.getNano();
        this.dateTimeOfVisitLocationPoint = offsetDateTime.minusNanos(nano % 1000);
    }

    public void setDateTimeOfVisitLocationPoint(OffsetDateTime dateTimeOfVisitLocationPoint) {
        this.dateTimeOfVisitLocationPoint = dateTimeOfVisitLocationPoint;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Location getLocationPointId() {
        return locationPointId;
    }

    public void setLocationPointId(Location locationPointId) {
        this.locationPointId = locationPointId;
    }
}
