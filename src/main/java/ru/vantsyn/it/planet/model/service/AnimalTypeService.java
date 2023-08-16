package ru.vantsyn.it.planet.model.service;

import ru.vantsyn.it.planet.model.dto.animalType.AnimalTypeDTO;
import ru.vantsyn.it.planet.model.entity.AnimalType;

public interface AnimalTypeService {
    AnimalType createAnimalType(AnimalType animalType);
    AnimalType getAnimalTypeById(long id);
    AnimalType updateAnimalType(long id, String type);
    void deleteAnimalType(long id);
}
