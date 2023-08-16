package ru.vantsyn.it.planet.model.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vantsyn.it.planet.model.entity.AnimalType;
import ru.vantsyn.it.planet.model.exception.AnimalTypeExistException;
import ru.vantsyn.it.planet.model.exception.AnimalTypeNotFoundException;
import ru.vantsyn.it.planet.model.exception.IllegalDataException;
import ru.vantsyn.it.planet.model.repository.AnimalTypeRepository;
import ru.vantsyn.it.planet.model.service.AnimalTypeService;

@Service
public class AnimalTypeServiceImpl implements AnimalTypeService {
    /**
     * Репозиторий для взаимодействия с таблицей animal_type из БД
     */
    private final AnimalTypeRepository animalTypeRepository;

    /**
     * Конструктор с параметрами, для реализации DI
     *
     * @param animalTypeRepository Репозиторий для взаимодействия с таблицей animal_type из БД
     */
    public AnimalTypeServiceImpl(AnimalTypeRepository animalTypeRepository) {
        this.animalTypeRepository = animalTypeRepository;
    }

    /**
     * Создание нового типа животных
     *
     * @param animalType тип животных
     * @return информация о созданном типе животных
     */
    @Override
    public AnimalType createAnimalType(AnimalType animalType) {
        AnimalType checkIsAnimalTypeExist = animalTypeRepository.findFirstByType(animalType.getType());
        if (checkIsAnimalTypeExist != null) {
            throw new AnimalTypeExistException(HttpStatus.CONFLICT);
        }
        return animalTypeRepository.save(animalType);
    }

    /**
     * Получение типа животных по id
     *
     * @param id id типа животных
     * @return тип животных
     */
    @Override
    public AnimalType getAnimalTypeById(long id) {
        if (id <= 0) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }
        AnimalType animalType = animalTypeRepository.findFirstById(id);
        if (animalType == null) {
            throw new AnimalTypeNotFoundException(HttpStatus.NOT_FOUND);
        }

        return animalType;
    }

    /**
     * Обновление данных о типе животных
     *
     * @param id id типа животных
     * @param type обновляемые данные у типа животных
     * @return обновлённая информация о типе животных
     */
    @Override
    public AnimalType updateAnimalType(long id, String type) {
        AnimalType animalType = getAnimalTypeById(id);

        AnimalType checkAnimalType = animalTypeRepository.findFirstByType(type);
        if (checkAnimalType != null) {
            throw new IllegalDataException(HttpStatus.CONFLICT);
        }

        animalType.setType(type);
        animalTypeRepository.save(animalType);

        return animalType;
    }

    /**
     * Удаление типа животных
     *
     * @param id id типа животных
     */
    @Override
    public void deleteAnimalType(long id) {
        AnimalType animalType = getAnimalTypeById(id);
        if (!animalType.getAnimals().isEmpty()) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }
        animalTypeRepository.delete(animalType);
    }
}
