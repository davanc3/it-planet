package ru.vantsyn.it.planet.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vantsyn.it.planet.model.entity.AnimalType;

public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {
    /**
     * Получение типа животных по его названию
     *
     * @param type название
     * @return тип животных
     */
    AnimalType findFirstByType(String type);

    /**
     * Получение типа животных по id
     *
     * @param id id
     * @return тип животных
     */
    AnimalType findFirstById(long id);
}
