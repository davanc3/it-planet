package ru.vantsyn.it.planet.model.service;

import ru.vantsyn.it.planet.model.dto.animal.AnimalCreateDTO;
import ru.vantsyn.it.planet.model.dto.animal.AnimalResultDTO;
import ru.vantsyn.it.planet.model.dto.animal.AnimalSearchFilterDTO;
import ru.vantsyn.it.planet.model.dto.animal.AnimalUpdateDTO;
import ru.vantsyn.it.planet.model.dto.animalType.AnimalTypeIdsDTO;
import ru.vantsyn.it.planet.model.entity.Animal;

import java.util.List;

public interface AnimalService {
    AnimalResultDTO getAnimalById(long id);
    List<AnimalResultDTO> searchAnimals(AnimalSearchFilterDTO animalFilter);
    AnimalResultDTO createAnimal(AnimalCreateDTO animalCreateDTO);
    AnimalResultDTO updateAnimal(long id, AnimalUpdateDTO animalUpdateDTO);
    void deleteAnimal(long id);
    AnimalResultDTO addAnimalTypeToAnimal(long animalId, long animalTypeId);
    AnimalResultDTO updateAnimalTypeToAnimal(long animalId, AnimalTypeIdsDTO animalTypeIdsDTO);
    AnimalResultDTO deleteAnimalTypeToAnimal(long animalId, long typeId);
    Animal findAnimalById(long id);
}
