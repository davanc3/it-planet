package ru.vantsyn.it.planet.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vantsyn.it.planet.model.dto.animalType.AnimalTypeDTO;
import ru.vantsyn.it.planet.model.entity.AnimalType;
import ru.vantsyn.it.planet.model.service.AccountService;
import ru.vantsyn.it.planet.model.service.AnimalTypeService;

@RestController
@RequestMapping("/animals/types")
public class AnimalTypeController {
    /**
     * Сервис для реализации бизнес логики при работе с типами животных
     */
    private final AnimalTypeService animalTypeService;
    /**
     * Сервис для реализации бизнес логики работы с аккаунтами
     */
    private final AccountService accountService;
    /**
     * Класс для автоматического преобразования Entity в DTO и наоборот
     */
    private final ModelMapper mapper;

    /**
     * Конструктор, для реализации DI
     *
     * @param animalTypeService экземпляр AnimalTypeService
     * @param accountService экземпляр AccountService
     * @param mapper экземпляр ModelMapper
     */
    public AnimalTypeController(AnimalTypeService animalTypeService, AccountService accountService, ModelMapper mapper) {
        this.animalTypeService = animalTypeService;
        this.accountService = accountService;
        this.mapper = mapper;
    }

    @PostMapping("")
    public ResponseEntity<AnimalType> createAnimalType(@Valid @RequestBody AnimalTypeDTO animalTypeDTO,
                                                       HttpServletRequest request) {
        accountService.checkIsAuthorize(request);

        return new ResponseEntity<>(animalTypeService.createAnimalType(mapper.map(animalTypeDTO, AnimalType.class)),
                HttpStatus.CREATED);
    }

    @GetMapping("/{animalId}")
    public AnimalType getAnimalTypeById(@PathVariable("animalId") long animalId) {
        return animalTypeService.getAnimalTypeById(animalId);
    }

    @PutMapping("/{animalId}")
    public AnimalType updateAnimalType(@PathVariable("animalId") long animalId,
                                       @Valid @RequestBody AnimalTypeDTO animalTypeDTO, HttpServletRequest request) {
        accountService.checkIsAuthorize(request);

        return animalTypeService.updateAnimalType(animalId, animalTypeDTO.getType());
    }

    @DeleteMapping("/{animalId}")
    public void deleteAnimalType(@PathVariable("animalId") long animalId, HttpServletRequest request) {
        accountService.checkIsAuthorize(request);
        animalTypeService.deleteAnimalType(animalId);
    }
}
