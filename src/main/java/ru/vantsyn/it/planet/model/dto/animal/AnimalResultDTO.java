package ru.vantsyn.it.planet.model.dto.animal;

import ru.vantsyn.it.planet.model.entity.AnimalLocation;
import ru.vantsyn.it.planet.model.entity.AnimalType;
import ru.vantsyn.it.planet.model.entity.Location;

import java.time.OffsetDateTime;
import java.util.*;

public class AnimalResultDTO {
    private long id;
    private Set<Long> animalTypes;
    private float weight;
    private float length;
    private float height;
    private String gender;
    private String lifeStatus;
    private OffsetDateTime chippingDateTime;
    private int chipperId;
    private long chippingLocationId;
    private List<Long> visitedLocations;
    private OffsetDateTime deathDateTime;

    public AnimalResultDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Long> getAnimalTypes() {
        return animalTypes;
    }

    public void setAnimalTypes(Set<AnimalType> animalTypes) {
        Set<Long> animalTypeIds = new HashSet<>();
        if (animalTypes != null) {
            for (AnimalType animalType : animalTypes) {
                animalTypeIds.add(animalType.getId());
            }
        }
        this.animalTypes = animalTypeIds;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public OffsetDateTime getChippingDateTime() {
        return chippingDateTime;
    }

    public void setChippingDateTime(OffsetDateTime chippingDateTime) {
        this.chippingDateTime = chippingDateTime;
    }

    public OffsetDateTime getDeathDateTime() {
        return deathDateTime;
    }

    public void setDeathDateTime(OffsetDateTime deathDateTime) {
        this.deathDateTime = deathDateTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLifeStatus() {
        return lifeStatus;
    }

    public void setLifeStatus(String lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    public int getChipperId() {
        return chipperId;
    }

    public void setChipperId(int chipperId) {
        this.chipperId = chipperId;
    }

    public long getChippingLocationId() {
        return chippingLocationId;
    }

    public void setChippingLocationId(long chippingLocationId) {
        this.chippingLocationId = chippingLocationId;
    }

    public List<Long> getVisitedLocations() {
        return visitedLocations;
    }

    public void setVisitedLocations(List<AnimalLocation> visitedLocations) {
        List<Long> visitedLocationIds = new ArrayList<>();
        if (visitedLocations != null) {
            for (AnimalLocation location: visitedLocations) {
                visitedLocationIds.add(location.getId());
            }
        }
        this.visitedLocations = visitedLocationIds;
    }
}
