package ru.vantsyn.it.planet.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @OneToMany(mappedBy = "chippingLocationId")
    @JsonIgnore
    private List<Animal> animals = new ArrayList<>();

    @OneToMany(mappedBy = "locationPointId")
    @JsonIgnore
    private List<AnimalLocation> animalLocations = new ArrayList<>();

    public Location() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public List<AnimalLocation> getAnimalLocations() {
        return animalLocations;
    }

    public void setAnimalLocations(List<AnimalLocation> animalLocations) {
        this.animalLocations = animalLocations;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", animals=" + animals +
                '}';
    }
}
