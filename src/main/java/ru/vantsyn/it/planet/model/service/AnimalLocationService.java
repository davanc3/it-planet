package ru.vantsyn.it.planet.model.service;

import ru.vantsyn.it.planet.model.dto.animalLocation.AnimalLocationResultDTO;
import ru.vantsyn.it.planet.model.dto.animalLocation.AnimalLocationSearchDTO;
import ru.vantsyn.it.planet.model.dto.animalLocation.AnimalLocationUpdateDTO;
import ru.vantsyn.it.planet.model.entity.AnimalLocation;

import java.util.List;

public interface AnimalLocationService {
    List<AnimalLocationResultDTO> getVisitedLocationsByAnimal(long animalId, AnimalLocationSearchDTO animalLocationSearchDTO);
    AnimalLocation addVisitedLocationToAnimal(long animalId, long locationId);
    AnimalLocation updateVisitedLocationToAnimal(long animalId, AnimalLocationUpdateDTO updateDTO);
    void deleteAnimalLocation(long animalId, long visitedLocationId);
}
