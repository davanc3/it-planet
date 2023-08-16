package ru.vantsyn.it.planet.model.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vantsyn.it.planet.model.dto.animalLocation.AnimalLocationResultDTO;
import ru.vantsyn.it.planet.model.dto.animalLocation.AnimalLocationSearchDTO;
import ru.vantsyn.it.planet.model.dto.animalLocation.AnimalLocationUpdateDTO;
import ru.vantsyn.it.planet.model.entity.Animal;
import ru.vantsyn.it.planet.model.entity.AnimalLocation;
import ru.vantsyn.it.planet.model.entity.Location;
import ru.vantsyn.it.planet.model.entity.enums.LifeStatus;
import ru.vantsyn.it.planet.model.exception.AnimalDeadException;
import ru.vantsyn.it.planet.model.exception.AnimalLocationNotFound;
import ru.vantsyn.it.planet.model.exception.AnimalNotFoundException;
import ru.vantsyn.it.planet.model.exception.IllegalDataException;
import ru.vantsyn.it.planet.model.repository.AnimalLocationRepository;
import ru.vantsyn.it.planet.model.service.AnimalLocationService;
import ru.vantsyn.it.planet.model.service.AnimalService;
import ru.vantsyn.it.planet.model.service.LocationService;
import ru.vantsyn.it.planet.model.utils.ChunkRequest;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalLocationServiceImpl implements AnimalLocationService {
    /**
     * Репозиторий для взаимодействия с таблицей БД animal_to_location
     */
    private final AnimalLocationRepository animalLocationRepository;

    /**
     * Сервис для реализации бизнес логики связанной с животными
     */
    private final AnimalService animalService;

    /**
     * Сервис для реализации бизнес логики связанной с точками локации
     */
    private final LocationService locationService;

    /**
     * Класс для автоматического преобразования Entity в DTO и наоборот
     */
    private final ModelMapper mapper;

    /**
     * Конструктор с параметрами, для реализации DI
     *
     * @param animalLocationRepository Репозиторий для взаимодействия с таблицей БД animal_to_location
     * @param animalService            Сервис для реализации бизнес логики связанной с животными
     * @param locationService          Сервис для реализации бизнес логики связанной с точками локации
     * @param mapper                   Класс для автоматического преобразования Entity в DTO и наоборот
     */
    public AnimalLocationServiceImpl(AnimalLocationRepository animalLocationRepository, AnimalService animalService, LocationService locationService, ModelMapper mapper) {
        this.animalLocationRepository = animalLocationRepository;
        this.animalService = animalService;
        this.locationService = locationService;
        this.mapper = mapper;
    }

    /**
     * Получение посещённых точек у животного
     *
     * @param animalId id животного
     * @param animalLocationSearchDTO условия поиска посещённых точек
     * @return коллекция посещённых точек
     */
    @Override
    public List<AnimalLocationResultDTO> getVisitedLocationsByAnimal(long animalId, AnimalLocationSearchDTO animalLocationSearchDTO) {
        if (animalId <= 0) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }
        Animal animal = animalService.findAnimalById(animalId);

        ChunkRequest chunkRequest = new ChunkRequest(animalLocationSearchDTO.getFrom(), animalLocationSearchDTO.getSize(),
                Sort.by(Sort.Direction.ASC, "id"));

        OffsetDateTime startDateTime = animalLocationSearchDTO.getStartDateTime() != null ?
                OffsetDateTime.parse(animalLocationSearchDTO.getStartDateTime()) : null;
        OffsetDateTime endDateTime = animalLocationSearchDTO.getEndDateTime() != null ?
                OffsetDateTime.parse(animalLocationSearchDTO.getEndDateTime()) : null;

        List<AnimalLocationResultDTO> resultDTOList = new ArrayList<>();
        List<AnimalLocation> animalLocations = animalLocationRepository.search(animal, startDateTime, endDateTime, chunkRequest);

        for (AnimalLocation animalLocation: animalLocations) {
            resultDTOList.add(mapper.map(animalLocation, AnimalLocationResultDTO.class));
        }

        return resultDTOList;
    }

    /**
     * Добавление новой точки локации для животного
     *
     * @param animalId id животного
     * @param locationId id точки локации
     * @return информация о добавленной к животному точки локации
     */
    @Override
    public AnimalLocation addVisitedLocationToAnimal(long animalId, long locationId) {
        Animal animal = animalService.findAnimalById(animalId);
        Location location = locationService.getLocationById(locationId);

        if (animal.getLifeStatus().equals(LifeStatus.DEAD)) {
            throw new AnimalDeadException(HttpStatus.BAD_REQUEST);
        }
        if (animal.getAnimalsLocations().isEmpty() && animal.getChippingLocationId().equals(location)) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }

        int lastLocation = animal.getAnimalsLocations().size();

        if (lastLocation > 0 && animal.getAnimalsLocations().get(lastLocation - 1).equals(location)) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }

        AnimalLocation animalLocation = new AnimalLocation();
        animalLocation.setAnimal(animal);
        animalLocation.setLocationPointId(location);
        animalLocation.setDateTimeOfVisitLocationPointByNow();

        animalLocationRepository.save(animalLocation);

        return animalLocationRepository.findFirstById(animalLocation.getId());
    }

    /**
     * Обновление посещённой животным точки локации с заданными параметрами
     *
     * @param animalId id животного
     * @param updateDTO параметры обновления точки локации
     * @return информация о посещённой точке локации у животного
     */
    @Override
    public AnimalLocation updateVisitedLocationToAnimal(long animalId, AnimalLocationUpdateDTO updateDTO) {
        Animal animal = animalService.findAnimalById(animalId);

        AnimalLocation visitedLocation = getAnimalLocationById(updateDTO.getVisitedLocationPointId());
        Location location = locationService.getLocationById(updateDTO.getLocationPointId());

        if (!visitedLocation.getAnimal().equals(animal)) {
            throw new AnimalNotFoundException(HttpStatus.NOT_FOUND);
        }
        if (visitedLocation.getLocationPointId().equals(location)) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }

        int visitedLocationIndex = animal.getVisitedLocations().indexOf(visitedLocation);

        if (animal.getChippingLocationId().equals(location) &&
                visitedLocationIndex == 0) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }
        if ((visitedLocationIndex < animal.getVisitedLocations().size() - 1 &&
                animal.getVisitedLocationByIndex(visitedLocationIndex + 1).equals(location)) ||
                (visitedLocationIndex > 0 && animal.getVisitedLocationByIndex(visitedLocationIndex - 1).equals(location))) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }

        visitedLocation.setLocationPointId(location);

        System.out.println(OffsetDateTime.from(visitedLocation.getDateTimeOfVisitLocationPoint()));

        animalLocationRepository.save(visitedLocation);

        return visitedLocation;
    }

    /**
     * Удаление посещённой животным точки локации
     *
     * @param animalId id животного
     * @param visitedLocationId id посещённой животным точки локации
     */
    @Override
    public void deleteAnimalLocation(long animalId, long visitedLocationId) {
        Animal animal = animalService.findAnimalById(animalId);

        AnimalLocation visitedLocation = getAnimalLocationById(visitedLocationId);

        if (!visitedLocation.getAnimal().equals(animal)) {
            throw new AnimalNotFoundException(HttpStatus.NOT_FOUND);
        }

        if (animal.getVisitedLocations().indexOf(visitedLocation) == 0 && animal.getVisitedLocations().size() > 1 &&
                animal.getVisitedLocationByIndex(1).equals(animal.getChippingLocationId())) {
            animalLocationRepository.delete(animal.getVisitedLocations().get(1));
        }

        animalLocationRepository.delete(visitedLocation);
    }

    /**
     * Получение посещённой животным точки локации по id
     *
     * @param id id посещённой
     * @return информация о посещённой животным точки локации
     */
    public AnimalLocation getAnimalLocationById(long id) {
        if (id <= 0) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }

        AnimalLocation animalLocation = animalLocationRepository.findFirstById(id);

        if (animalLocation == null) {
            throw new AnimalLocationNotFound(HttpStatus.NOT_FOUND);
        }

        return animalLocation;
    }
}
