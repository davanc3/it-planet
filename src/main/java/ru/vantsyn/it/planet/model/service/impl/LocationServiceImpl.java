package ru.vantsyn.it.planet.model.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vantsyn.it.planet.model.entity.Location;
import ru.vantsyn.it.planet.model.exception.IllegalDataException;
import ru.vantsyn.it.planet.model.exception.LocationExistException;
import ru.vantsyn.it.planet.model.exception.LocationNotFoundException;
import ru.vantsyn.it.planet.model.repository.LocationRepository;
import ru.vantsyn.it.planet.model.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {
    /**
     * Репозиторий для взаимодействия с таблицей location из БД
     */
    private final LocationRepository locationRepository;

    /**
     * Конструктор с параметрами, для реализации DI
     *
     * @param locationRepository Репозиторий для взаимодействия с таблицей location из БД
     */
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Создание новой точки локации
     *
     * @param location точка локации
     * @return информация о созданной точке локации
     */
    @Override
    public Location createLocation(Location location) {
        Location checkLocation = locationRepository.findFirstByLatitudeAndLongitude(location.getLatitude(), location.getLongitude());
        if (checkLocation != null) {
            throw new LocationExistException(HttpStatus.CONFLICT);
        }

        return locationRepository.save(location);
    }

    /**
     * Получение точки локации по id
     *
     * @param id id точки локации
     * @return точка локации
     */
    @Override
    public Location getLocationById(long id) {
        if (id <= 0) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }

        Location location = locationRepository.findFirstById(id);

        if (location == null) {
            throw new LocationNotFoundException(HttpStatus.NOT_FOUND);
        }

        return location;
    }

    /**
     * Обновление записи о точке локации
     *
     * @param id id точки локации
     * @param location обновляемые данные точки локации
     * @return информация об обновлённой точке лакации
     */
    @Override
    public Location updateLocation(long id, Location location) {
        Location updateLocation = getLocationById(id);

        Location checkLocation = locationRepository.findFirstByLatitudeAndLongitude(location.getLatitude(), location.getLongitude());
        if (checkLocation != null) {
            throw new LocationExistException(HttpStatus.CONFLICT);
        }

        updateLocation.setLatitude(location.getLatitude());
        updateLocation.setLongitude(location.getLongitude());

        return locationRepository.save(updateLocation);
    }

    /**
     * Удаление точки локации
     *
     * @param id id точки локации
     */
    @Override
    public void deleteLocation(long id) {
        Location location = getLocationById(id);
        if (!location.getAnimals().isEmpty() || !location.getAnimalLocations().isEmpty()) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }
        locationRepository.delete(location);
    }
}
