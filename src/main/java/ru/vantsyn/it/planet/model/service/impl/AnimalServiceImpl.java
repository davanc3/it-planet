package ru.vantsyn.it.planet.model.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vantsyn.it.planet.model.dto.account.AccountResultDto;
import ru.vantsyn.it.planet.model.dto.animal.AnimalCreateDTO;
import ru.vantsyn.it.planet.model.dto.animal.AnimalResultDTO;
import ru.vantsyn.it.planet.model.dto.animal.AnimalSearchFilterDTO;
import ru.vantsyn.it.planet.model.dto.animal.AnimalUpdateDTO;
import ru.vantsyn.it.planet.model.dto.animalType.AnimalTypeIdsDTO;
import ru.vantsyn.it.planet.model.entity.Account;
import ru.vantsyn.it.planet.model.entity.Animal;
import ru.vantsyn.it.planet.model.entity.AnimalType;
import ru.vantsyn.it.planet.model.entity.Location;
import ru.vantsyn.it.planet.model.entity.enums.Gender;
import ru.vantsyn.it.planet.model.entity.enums.LifeStatus;
import ru.vantsyn.it.planet.model.exception.*;
import ru.vantsyn.it.planet.model.repository.AnimalRepository;
import ru.vantsyn.it.planet.model.service.AccountService;
import ru.vantsyn.it.planet.model.service.AnimalService;
import ru.vantsyn.it.planet.model.service.AnimalTypeService;
import ru.vantsyn.it.planet.model.service.LocationService;
import ru.vantsyn.it.planet.model.utils.ChunkRequest;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {
    /**
     * Репозиторий для взаимодействия с таблицей animal из БД
     */
    private final AnimalRepository animalRepository;

    /**
     * Сервис для работы с бизнес логикой аккаунтов
     */
    private final AccountService accountService;

    /**
     * Сервис для работы с бизнес логикой точек локации
     */
    private final LocationService locationService;

    /**
     * Сервис для работы с бизнес логикой типов животных
     */
    private final AnimalTypeService animalTypeService;

    /**
     * Класс для автоматического преобразования Entity в DTO и наоборот
     */
    private final ModelMapper mapper;

    /**
     * Конструктор с параметрами, для реализации DI
     *
     * @param animalRepository  Репозиторий для взаимодействия с таблицей animal из БД
     * @param accountService    Сервис для работы с бизнес логикой аккаунтов
     * @param locationService   Сервис для работы с бизнес логикой точек локации
     * @param animalTypeService Сервис для работы с бизнес логикой типов животных
     * @param mapper            Класс для автоматического преобразования Entity в DTO и наоборот
     */
    public AnimalServiceImpl(AnimalRepository animalRepository, AccountService accountService, LocationService locationService, AnimalTypeService animalTypeService, ModelMapper mapper) {
        this.animalRepository = animalRepository;
        this.accountService = accountService;
        this.locationService = locationService;
        this.animalTypeService = animalTypeService;
        this.mapper = mapper;
    }

    /**
     * Получение животного по id
     *
     * @param id id животного
     * @return животное
     */
    @Override
    public AnimalResultDTO getAnimalById(long id) {
        Animal animal = findAnimalById(id);

        return mapper.map(animal, AnimalResultDTO.class);
    }

    /**
     * Поиск животных по заданным фильтрам
     *
     * @param animalFilter заданные фильтры
     * @return животные
     */
    @Override
    public List<AnimalResultDTO> searchAnimals(AnimalSearchFilterDTO animalFilter) {
        ChunkRequest chunkRequest = new ChunkRequest(animalFilter.getFrom(), animalFilter.getSize(),
                Sort.by(Sort.Direction.ASC, "id"));
        Account account = new Account();
        if (animalFilter.getChipperId() != null) {
            AccountResultDto accountResultDto = accountService.getById(animalFilter.getChipperId());
            if (accountResultDto == null) {
                account.setId(animalFilter.getChipperId());
            } else {
                account.setId(accountResultDto.getId());
            }
        }

        Location location = null;

        if (animalFilter.getChippingLocationId() != null) {
            location = locationService.getLocationById(animalFilter.getChippingLocationId());
            if (location == null) {
                location = new Location();
                location.setId(animalFilter.getChippingLocationId());
            }
        }

        List<Animal> animals = animalRepository.search(
                animalFilter.getStartDateTime() != null ? OffsetDateTime.parse(animalFilter.getStartDateTime()) : null,
                animalFilter.getEndDateTime() != null ? OffsetDateTime.parse(animalFilter.getEndDateTime()) : null,
                account,
                location,
                LifeStatus.getInstance(animalFilter.getLifeStatus()),
                Gender.getInstance(animalFilter.getGender()),
                chunkRequest);

        List<AnimalResultDTO> animalResultDTOList = new ArrayList<>();

        for (Animal animal : animals) {
            animalResultDTOList.add(mapper.map(animal, AnimalResultDTO.class));
        }

        return animalResultDTOList;
    }

    /**
     * Создание нового животного
     *
     * @param animalCreateDTO данные животного
     * @return созданное животное
     */
    @Override
    public AnimalResultDTO createAnimal(AnimalCreateDTO animalCreateDTO) {
        Animal animal = new Animal();

        for (long animalTypeId : animalCreateDTO.getAnimalTypes()) {
            AnimalType animalType = animalTypeService.getAnimalTypeById(animalTypeId);

            if (animal.isAnimalTypeDuplicate(animalType)) {
                throw new AnimalTypeExistException(HttpStatus.CONFLICT);
            }

            animal.addAnimalType(animalType);
        }

        animal.setChipperId(accountService.convertAccountResultDtoToAccount(accountService.getById(animalCreateDTO.getChipperId())));
        animal.setChippingLocationId(locationService.getLocationById(animalCreateDTO.getChippingLocationId()));
        animal.setChippingDateTimeByNow();
        animal.setHeight(animalCreateDTO.getHeight());
        animal.setWeight(animalCreateDTO.getWeight());
        animal.setLength(animalCreateDTO.getLength());
        animal.setGender(Gender.getInstance(animalCreateDTO.getGender()));
        animal.setLifeStatus(LifeStatus.ALIVE);

        Animal createdAnimal = animalRepository.save(animal);

        return mapper.map(createdAnimal, AnimalResultDTO.class);
    }

    /**
     * Обновление данных животного
     *
     * @param id id животного
     * @param animalUpdateDTO данные по животному
     * @return животное с обновлённыеми данными
     */
    @Override
    public AnimalResultDTO updateAnimal(long id, AnimalUpdateDTO animalUpdateDTO) {
        Animal animal = findAnimalById(id);
        if (animalUpdateDTO.getLifeStatus() != null) {
            if (animal.getLifeStatus().equals(LifeStatus.DEAD) && animalUpdateDTO.getLifeStatus().equals("ALIVE")) {
                throw new IllegalDataException(HttpStatus.BAD_REQUEST);
            }
            if (animalUpdateDTO.getLifeStatus().equals("DEAD") && animal.getLifeStatus().toString().equals("ALIVE")) {
                animal.setLifeStatus(LifeStatus.DEAD);
                animal.setDeathDateTimeByNow();
            }
        }

        animal.setChipperId(accountService.convertAccountResultDtoToAccount(accountService.getById(animalUpdateDTO.getChipperId())));
        Location location = locationService.getLocationById(animalUpdateDTO.getChippingLocationId());
        if (location.equals(animal.getVisitedLocationByIndex(0))) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }
        if (animalUpdateDTO.getGender() != null) {
            animal.setGender(Gender.getInstance(animalUpdateDTO.getGender()));
        }

        animal.setChippingLocationId(location);
        animal.setHeight(animalUpdateDTO.getHeight());
        animal.setWeight(animalUpdateDTO.getWeight());
        animal.setLength(animalUpdateDTO.getLength());

        animalRepository.save(animal);

        return mapper.map(animal, AnimalResultDTO.class);
    }

    /**
     * Удаление животного
     *
     * @param id id животного
     */
    @Override
    public void deleteAnimal(long id) {
        Animal animal = findAnimalById(id);
        if (!animal.getAnimalsLocations().isEmpty()) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }
        animalRepository.delete(animal);
    }

    /**
     * Добавдение нового типа животного к животному
     *
     * @param animalId id животного
     * @param animalTypeId id типа животного
     * @return обновлённая информация о животном
     */
    @Override
    public AnimalResultDTO addAnimalTypeToAnimal(long animalId, long animalTypeId) {
        if (animalTypeId <= 0) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }

        Animal animal = findAnimalById(animalId);
        AnimalType animalType = animalTypeService.getAnimalTypeById(animalTypeId);
        if (animal.isAnimalTypeDuplicate(animalType)) {
            throw new AnimalTypeExistException(HttpStatus.CONFLICT);
        }
        animal.addAnimalType(animalType);
        animalRepository.save(animal);

        return mapper.map(animal, AnimalResultDTO.class);
    }

    /**
     * Обновление типа животных у животного
     *
     * @param animalId id животного
     * @param animalTypeIdsDTO информация по обновляемуму типу животных
     * @return информация о животном
     */
    @Override
    public AnimalResultDTO updateAnimalTypeToAnimal(long animalId, AnimalTypeIdsDTO animalTypeIdsDTO) {
        if (animalId <= 0) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }
        Animal animal = findAnimalById(animalId);

        AnimalType oldAnimalType = animalTypeService.getAnimalTypeById(animalTypeIdsDTO.getOldTypeId());
        AnimalType newAnimalType = animalTypeService.getAnimalTypeById(animalTypeIdsDTO.getNewTypeId());

        if (animal.isAnimalTypeDuplicate(newAnimalType)) {
            throw new AnimalTypeExistException(HttpStatus.CONFLICT);
        }

        if (!animal.isAnimalTypeDuplicate(oldAnimalType)) {
            throw new AnimalTypeNotFoundException(HttpStatus.NOT_FOUND);
        }
        animal.removeAnimalType(oldAnimalType);
        animal.addAnimalType(newAnimalType);

        animalRepository.save(animal);

        return mapper.map(animal, AnimalResultDTO.class);
    }

    /**
     * Удаление типа животных у животного
     *
     * @param animalId id животного
     * @param typeId id типа животных
     * @return информация о животном
     */
    @Override
    public AnimalResultDTO deleteAnimalTypeToAnimal(long animalId, long typeId) {
        if (animalId <= 0 || typeId <= 0) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }

        Animal animal = findAnimalById(animalId);
        AnimalType animalType = animalTypeService.getAnimalTypeById(typeId);

        if (!animal.isAnimalTypeDuplicate(animalType)) {
            throw new AnimalTypeNotFoundException(HttpStatus.NOT_FOUND);
        }

        if (animal.getAnimalTypes().size() == 1 && animal.isAnimalTypeDuplicate(animalType)) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }

        animal.removeAnimalType(animalType);

        animalRepository.save(animal);

        return mapper.map(animal, AnimalResultDTO.class);
    }

    /**
     * Поиск животного по id
     *
     * @param id id животного
     * @return животное
     */
    @Override
    public Animal findAnimalById(long id) {
        if (id <= 0) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }

        Animal animal = animalRepository.findFirstById(id);
        if (animal == null) {
            throw new AnimalNotFoundException(HttpStatus.NOT_FOUND);
        }

        return animal;
    }
}
