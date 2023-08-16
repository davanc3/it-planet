package ru.vantsyn.it.planet.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vantsyn.it.planet.model.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
    /**
     * Получение точки локации по широте и долготе
     *
     * @param latitude широта
     * @param longitude долгота
     * @return точка локации
     */
    Location findFirstByLatitudeAndLongitude(double latitude, double longitude);

    /**
     * Получение точки локации по id
     *
     * @param id id
     * @return точка локации
     */
    Location findFirstById(long id);
}
