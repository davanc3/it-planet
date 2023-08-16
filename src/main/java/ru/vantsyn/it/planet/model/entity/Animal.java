package ru.vantsyn.it.planet.model.entity;

import jakarta.persistence.*;
import ru.vantsyn.it.planet.model.entity.enums.Gender;
import ru.vantsyn.it.planet.model.entity.enums.LifeStatus;

import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Table(name = "animal")
public class Animal {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "weight")
    private double weight;

    @Column(name = "length")
    private double length;

    @Column(name = "height")
    private double height;

    @Column(name = "chipping_date_time", columnDefinition = "TIMESTAMP (6)")
    private OffsetDateTime chippingDateTime;

    @Column(name = "death_date_time", columnDefinition="TIMESTAMP (6)")
    private OffsetDateTime deathDateTime;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "life_status")
    @Enumerated(EnumType.STRING)
    private LifeStatus lifeStatus;

    @ManyToOne
    @JoinColumn(name="chipper_id")
    private Account chipperId;

    @ManyToOne
    @JoinColumn(name = "chipping_location_id")
    private Location chippingLocationId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "animal_to_animal_type",
            joinColumns = {@JoinColumn(name = "animal_id")},
            inverseJoinColumns = {@JoinColumn(name = "animal_type_id")})
    private Set<AnimalType> animalTypes = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "animal_to_location",
            joinColumns = {@JoinColumn(name = "animal_id")},
            inverseJoinColumns = {@JoinColumn(name = "location_id")})
    private List<Location> animalsLocations = new ArrayList<>();

    @OneToMany(mappedBy = "animal")
    private List<AnimalLocation> visitedLocations = new ArrayList<>();

    public Animal() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public OffsetDateTime getChippingDateTime() {
        return chippingDateTime;
    }

    public void setChippingDateTime(OffsetDateTime chippingDateTime) {
        this.chippingDateTime = chippingDateTime;
    }

    public void setChippingDateTimeByNow() {
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        int nano = offsetDateTime.getNano();
        this.chippingDateTime = offsetDateTime.minusNanos(nano % 1000);
    }

    public OffsetDateTime getDeathDateTime() {
        return deathDateTime;
    }

    public void setDeathDateTimeByNow() {
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        int nano = offsetDateTime.getNano();
        this.deathDateTime = offsetDateTime.minusNanos(nano % 1000);
    }

    public void setDeathDateTime(OffsetDateTime deathDateTime) {
        this.deathDateTime = deathDateTime;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LifeStatus getLifeStatus() {
        return lifeStatus;
    }

    public void setLifeStatus(LifeStatus lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    public Account getChipperId() {
        return chipperId;
    }

    public void setChipperId(Account chipperId) {
        this.chipperId = chipperId;
    }

    public Location getChippingLocationId() {
        return chippingLocationId;
    }

    public void setChippingLocationId(Location chippingLocationId) {
        this.chippingLocationId = chippingLocationId;
    }

    public Set<AnimalType> getAnimalTypes() {
        return animalTypes;
    }

    public void setAnimalTypes(Set<AnimalType> animalTypes) {
        this.animalTypes = animalTypes;
    }

    public void addAnimalType(AnimalType animalType) {
        this.animalTypes.add(animalType);
    }

    public void removeAnimalType(AnimalType animalType) {
        animalTypes.remove(animalType);
        animalType.getAnimals().remove(this);
    }

    public boolean isAnimalTypeDuplicate(AnimalType animalType) {
        return this.animalTypes.contains(animalType);
    }

    @PrePersist
    public void addAnimalTypes() {
        animalTypes.forEach(animalType -> animalType.getAnimals().add(this));
        animalsLocations.forEach(visitedLocation -> visitedLocation.getAnimals().add(this));
    }

    @PreRemove
    public void removeAnimalTypes() {
        animalTypes.forEach(animalType -> animalType.getAnimals().remove(this));
        animalsLocations.forEach(visitedLocation -> visitedLocation.getAnimals().remove(this));
    }

    public List<Location> getAnimalsLocations() {
        return animalsLocations;
    }

    public void setAnimalsLocations(List<Location> animalsLocations) {
        this.animalsLocations = animalsLocations;
    }

    public void addVisitedLocation(Location location) {
        if (this.animalsLocations == null) {
            this.animalsLocations = new ArrayList<>();
        }
        this.animalsLocations.add(location);
    }

    public Location getVisitedLocationByIndex(int index) {
        Location location = null;
        if (this.animalsLocations != null && !this.animalsLocations.isEmpty()) {
            location = this.animalsLocations.get(index);
        }
        return location;
    }

    public List<AnimalLocation> getVisitedLocations() {
        return visitedLocations;
    }

    public void setVisitedLocations(List<AnimalLocation> visitedLocations) {
        this.visitedLocations = visitedLocations;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", weight=" + weight +
                ", length=" + length +
                ", height=" + height +
                ", chippingDateTime=" + chippingDateTime +
                ", deathDateTime=" + deathDateTime +
                '}';
    }
}
