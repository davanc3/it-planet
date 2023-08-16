package ru.vantsyn.it.planet.model.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vantsyn.it.planet.model.entity.Account;
import ru.vantsyn.it.planet.model.entity.Animal;
import ru.vantsyn.it.planet.model.entity.Location;
import ru.vantsyn.it.planet.model.entity.enums.Gender;
import ru.vantsyn.it.planet.model.entity.enums.LifeStatus;

import java.time.OffsetDateTime;
import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    /**
     * Поиск животного по id
     *
     * @param id id животного
     * @return животное
     */
    Animal findFirstById(long id);

    /**
     * Поиск животных по параметрам
     *
     * @param startDateTime Дата и время, не раньше которых произошло чипирование животного в формате ISO-8601
     * @param endDateTime Дата и время, не позже которых произошло чипирование животного в формате ISO-8601
     * @param chipperId Идентификатор аккаунта пользователя, чипировавшего животное, если null, не участвует в фильтрации
     * @param chipperLocationId Идентификатор точки локации животных, если null, не участвует в фильтрации
     * @param lifeStatus Жизненный статус животного, если null, не участвует в фильтрации
     * @param gender Гендерная принадлежность животного, если null, не участвует в фильтрации
     * @param pageable пагинация + сортировка для выдачи результатов
     * @return список с животными
     */
    @Query("select a from Animal as a where (:startDateTime is null or a.chippingDateTime >= :startDateTime) " +
            "and (:endDateTime is null or a.chippingDateTime <= :endDateTime)" +
            " and (:chipperId is null or a.chipperId = :chipperId) and " +
            "(:chipperLocationId is null or a.chippingLocationId = :chipperLocationId)" +
            " and (:lifeStatus is null or a.lifeStatus = :lifeStatus) and " +
            "(:gender is null or a.gender = :gender)")
    List<Animal> search(@Param("startDateTime") OffsetDateTime startDateTime, @Param("endDateTime") OffsetDateTime endDateTime,
                        @Param("chipperId") Account chipperId, @Param("chipperLocationId") Location chipperLocationId,
                        @Param("lifeStatus") LifeStatus lifeStatus, @Param("gender") Gender gender, Pageable pageable);
}
