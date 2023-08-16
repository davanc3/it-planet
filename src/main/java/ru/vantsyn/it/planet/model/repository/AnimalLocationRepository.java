package ru.vantsyn.it.planet.model.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vantsyn.it.planet.model.entity.Animal;
import ru.vantsyn.it.planet.model.entity.AnimalLocation;
import ru.vantsyn.it.planet.model.entity.AnimalType;
import ru.vantsyn.it.planet.model.entity.Location;

import java.time.OffsetDateTime;
import java.util.List;

public interface AnimalLocationRepository extends JpaRepository<AnimalLocation, Long> {
    AnimalLocation findFirstById(Long id);

    @Query("from AnimalLocation as a where a.animal = :animal and " +
            "(:startDateTime is null or a.dateTimeOfVisitLocationPoint >= :startDateTime) and " +
            "(:endDateTime is null or a.dateTimeOfVisitLocationPoint >= :endDateTime)")
    List<AnimalLocation> search(@Param("animal") Animal animal, @Param("startDateTime") OffsetDateTime startDateTime,
                                @Param("endDateTime") OffsetDateTime endDateTime, Pageable pageable);

    AnimalLocation findFirstByLocationPointId(Location location);
}
